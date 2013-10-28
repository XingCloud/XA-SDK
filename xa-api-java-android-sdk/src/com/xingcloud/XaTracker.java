package com.xingcloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xingcloud.utils.Cutil;
import com.xingcloud.utils.Futil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created with IntelliJ IDEA.
 * User: yb
 * Date: 10/23/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class XaTracker extends BroadcastReceiver{
  private final String INSTALL_ACTION = "com.android.vending.INSTALL_REFERRER";
  private static final String ELEXXATRACKERFILE = "ELEXXATRACKERFILE";
  @Override
  public void onReceive(Context c, Intent i) {
    if(i.getAction().equals(INSTALL_ACTION)){
      String referrer = i.getStringExtra("referrer");
      if(Cutil.checkNull(referrer)){
        return;
      }
      Log.d("XA","referrer:" + referrer);
      try {
        referrer = URLDecoder.decode(referrer, "utf-8");

        if(!Cutil.checkNull(referrer)){
          if(!Cutil.checkNull(XA.xa))
            XA.xa.updateRef(referrer);
          File f = new File(c.getFilesDir(),ELEXXATRACKERFILE);
          if(!f.exists()){
            Futil.writeFile(f, referrer);
          }
        }
      } catch (UnsupportedEncodingException e) {
        Log.e("XA","referrer error");
        return;
      } catch (IOException e) {
        Log.e("XA","referrer file error");
        e.printStackTrace();
      }
    }
  }

  public static String getReferrer(Context c){
    File f = new File(c.getFilesDir(),ELEXXATRACKERFILE);
    if(f.exists()){
      try {
        return Futil.readFile(f);
      } catch (IOException e) {
        Log.e("XA","Error read referrer");
      }
    }
    return null;
  }

}
