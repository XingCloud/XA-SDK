package com.xingcloud.example;

import com.xingcloud.Pay337;
import com.xingcloud.XAService;
import com.xingcloud.model.Action;
import com.xingcloud.model.Pay;
import com.xingcloud.model.Update;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class Example {

    public static void main(String args[]) throws IOException {
		exampleHelloWorld();
		exampleActions();
		exampleUserProperties();
		exampleResponseHandling();
		exampleBatchSending();
        examplePay337();
    }

	/**
	 * example sending a simple visit event.
	 * @throws IOException
	 */
	private static void exampleHelloWorld() throws IOException {
		String appid = "myproject"; // your registered project id at http://p.xingcloud.com
  		String uid = "user123a" ; //current user's uid
		XAService xaService = new XAService(appid,uid);
		xaService.action(new Action("visit"), 10000); //timeout 10 seconds
	}

	/**
	 * example sending actions in different ways.
	 */
	private static void exampleActions() throws IOException {
		XAService xaService = new XAService("myproject", "user1234a");
		int timeoutMillisec = 10000;		
		xaService.action(new Action("visit"), timeoutMillisec); //timeout 10 seconds.		
		xaService.action(new Action("pay", 100l), timeoutMillisec); //action with values.
		long timestamp = System.currentTimeMillis();
		xaService.action(new Action("pay", 100l, timestamp), timeoutMillisec); //action with values and timestamp.
		/*
		 * timestamps are useful when you need to re-write an already-sent action.
		 * if the same user id sends the same action with the same timestamp,
		 * the later action would overwrite the earlier ones.
		 * For example, the following action will rewrite last pay action's value 100 to 50,
		 * instead of recording a new pay action.
		 */
		xaService.action(new Action("pay", 50l, timestamp), timeoutMillisec); //action with values and timestamp.		
	}

	/**
	 * example updating user's properties.
	 * user's properties must be pre-defined in your project at http://a.xingcloud.com before being updated.
	 * @throws IOException
	 */
	private static void exampleUserProperties() throws IOException {
		XAService xaService = new XAService("myproject", "user1234a");
		xaService.update(new Update("nation","us"), 10000);// set the value of user "user1234a"'s property 'nation' to 'us'.
		xaService.update(new Update("platform", "facebook"), 10000);// set the value of user "user1234a"'s property 'platform' to 'facebook'.
	}

	/**
	 * example handling responses from api calls.
	 * @throws IOException
	 */
	private static void exampleResponseHandling() throws IOException {
		XAService xaService = new XAService("myproject", "user1234a");
		String response = xaService.action(new Action("visit"), 10000);
		responseHandle(response);//json string returned to diagnostic problems.
	}

	/**
	 * example sending multiple data in one request to reduce I/O.
	 */
	private static void exampleBatchSending() throws IOException {
		// once a XAService object is initiated, it can be used throughout the rest of this app's lifecycle,
		// as long as the current user id does not change.
		XAService xaService = new XAService("myproject", "user1234a");		

		int timeoutMillisec = 10000;
		Action visit = new Action("visit");
	  	Action buyFruit = new Action("buy.fruit", 1);
		
		//sends 2 actions together
		xaService.batch(Arrays.asList(visit, buyFruit), timeoutMillisec);
		
  		Action pay = new Action("pay", 100l, System.currentTimeMillis() / 1000);
  		Action heartbeat = new Action("heartbeat", 1l, System.currentTimeMillis());
  		Update updateNation = new Update("nation", "USA");
  		Update updateLevel = new Update("level", "1");
		
		//sends 2 actions and 2 updates together
		xaService.batch(Arrays.asList(pay, heartbeat, updateNation, updateLevel), timeoutMillisec);		
		
		//batch  with timestamp.
		xaService.batch(System.currentTimeMillis(), Arrays.asList(pay, heartbeat, updateNation, updateLevel), timeoutMillisec);		
		
	}

	/**
	 * example sending purchasing transactions to pay337.
	 */
    private static void examplePay337(){
        String appid = "337test" ;
        String uid = "123" ;

        Pay337 pay337 = new Pay337(appid,uid);

        String channelTransId = "123" ;
        String status = Pay.APP_SUCCESS  ;
        String channel = "googlecheckout";
        long amount = 100l ;
        String currency = "USD" ;
        long gross = 10l ;
        long fee = 4l ;
        long timestamp = System.currentTimeMillis() / 1000;

         Pay pay = new Pay( channelTransId,
                 status,
                 channel,
                 amount,
                 currency,
                 gross,
                 fee,
                 timestamp)  ;
        try{

            String response = pay337.pay(pay,10);
            responseHandle(response);
        }catch (IOException e){
             e.printStackTrace();
        }
    }

    private static void responseHandle(String jsonResponse) {
        System.out.println(jsonResponse);

        /*
        *   {"stats":"ok","time":"0.80 ms","message":"store 2 action and 0 update "}
        */
    }

}
