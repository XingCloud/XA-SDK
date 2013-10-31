package com.xingcloud;

import android.content.Context;
import com.xingcloud.model.Action;
import com.xingcloud.model.Operation;
import com.xingcloud.model.Update;
import com.xingcloud.utils.Cutil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class XA implements IXA {

  static final int BATCH_SIZE = 20;

  public static final int DEFAULT_TIMEOUT = 20000;
  public static final String ANDROID_PLATFORM = "android";
  private XAService xaService;
  public static XA xa = null;

  private BlockingQueue<Sending> queue = new LinkedBlockingQueue<Sending>();

  private Sender sender = null;

  public static XA init(String appid) {
     xa=new XA(appid);
     return xa;
  }

  public static XA init(String appid, String uid) {
     xa=new XA(appid,uid);
     return xa;
  }

  public static XA init(String appid, String uid, Context c) {
     xa=new XA(appid,uid,c);
     return xa;
  }

  private XA(String appid) {
    this(appid, null);
  }

  private XA(String appid, String uid) {
    this.xaService = new XAService(appid, uid);
    startSender();
    //new LogOnCreate().execute(uid);

  }

  private XA(String appid, String uid, Context c) {
    this(appid, uid);
    logOncreate(c);
    visit();
  }

  private void startSender() {
    if (sender == null) {
      sender = new Sender();
      sender.start();
    }
  }

  @Override
  public void setUid(String uid) {
    this.xaService.setUid(uid);
  }

  @Override
  public void action(Action action) {
    checkAvailability();
    queue.add(new Sending(action, xaService.getUid()));
  }

  @Override
  public void action(Action action, long timestamp) {
    checkAvailability();
    action.setTimestamp(timestamp);
    queue.add(new Sending(action, xaService.getUid()));
  }

  public void action(String event, long value, long timestamp) {
    checkAvailability();
    Action action = new Action(event);
    if (value != 0) action.setValue(value);
    if (timestamp != -1) action.setTimestamp(timestamp);
    queue.add(new Sending(action, xaService.getUid()));
  }

  public void action(String event, long value) {
    action(event, value, -1);
  }

  public void action(String event) {
    action(event, 0, -1);
  }

  public void visit() {
    Action act = new Action("visit");
    action(act);
  }

  public void visit(long timestamp){
    Action act = new Action("visit",timestamp);
    action(act);
  }

  public void pay() {
    Action pay = new Action("pay");
    action(pay);
  }

  public void pay(long value) {
    Action pay = new Action("pay", value);
    action(pay);
  }

  public void pay(long value,long timestamp){
    Action pay = new Action("pay",value,timestamp);
    action(pay);
  }

  @Override
  public void update(Update update) {
    checkAvailability();
    queue.add(new Sending(update, xaService.getUid()));
  }

  public void update(String property, Object value) {
    checkAvailability();
    queue.add(new Sending(new Update(property, value), xaService.getUid()));
  }

  public void updateLan() {
    Update lanUpdate = new Update("language", Locale.getDefault().getLanguage());
    update(lanUpdate);
  }

  public void updateCountry() {
    Update countryUpdate = new Update("country", Locale.getDefault().getCountry());
    update(countryUpdate);
  }

  public void updatePlatform() {
    Update countryUpdate = new Update("country", ANDROID_PLATFORM);
    update(countryUpdate);
  }

  public void updateRef(Context c) {
    if (!Cutil.checkNull(c)) {
      String ref = XaTracker.getReferrer(c);
      if (!Cutil.checkNull(ref)) {
        Update refUpdate = new Update("reference", ref);
        update(refUpdate);
      }
    }
  }

  public void updateRef(String reference) {
    Update refUpdate = new Update("reference", reference);
    update(refUpdate);
  }

  @Override
  public void close() {
    sender.close();
  }

  private void checkAvailability() {
    if (xaService.getAppid() == null || xaService.getUid() == null) {
      throw new NullPointerException("appid or uid null! appid:" + xaService.getAppid() + ",uid:" + xaService.getUid());
    }
  }

  private void logOncreate(Context c) {
    updateLan();
    updateCountry();
    updatePlatform();
    visit();
    updateRef(c);
  }


  class Sender extends Thread {
    private boolean closed = false;
    private List<Operation> lastFailed = null;
    private String lastUid = null;

    Sender() {
      super("XA-Sender");
    }

    @Override
    public void run() {
      while (!closed) {
        List<Operation> selected = new ArrayList<Operation>();
        String currentUid = null;
        if (lastUid != null) {
          currentUid = lastUid;
          selected = lastFailed;
          clearFailed();
        } else {
          for (int i = 0; i < BATCH_SIZE && !queue.isEmpty(); i++) {
            if (currentUid != null && !currentUid.equals(queue.peek().uid)) {
              //deal with actions with same uid only
              break;
            }
            Sending next = queue.poll();
            if (currentUid == null) {
              currentUid = next.uid;
            }
            selected.add(next.operation);
          }
        }
        if (selected.size() != 0) {
          try {
            xaService.batch(currentUid, selected, DEFAULT_TIMEOUT);
          } catch (IOException e) {
            e.printStackTrace();
            //send error. resend later.
            System.err.println("xaService. failed, will retry later.");
            stallFailed(selected, currentUid);
          }
        }
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();  //e:
        }
      }
    }

    private void clearFailed() {
      lastFailed = null;
      lastUid = null;
    }

    private void stallFailed(List<Operation> selected, String currentUid) {
      lastFailed = selected;
      lastUid = currentUid;
    }

    public void close() {
      closed = true;
    }
  }

  static class Sending {
    Operation operation;
    String uid;

    Sending(Operation operation, String uid) {
      this.operation = operation;
      this.uid = uid;
    }

  }

  public static void main(String[] args) throws InterruptedException {
    XA xa = new XA("xaa", "immars");
    xa.action(new Action("visit"));
    xa.action(new Action("heartbeat"));
    xa.update(new Update("ref0", "g"));
    Thread.sleep(10000);
  }

}


