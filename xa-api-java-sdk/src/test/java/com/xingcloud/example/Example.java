package com.xingcloud.example;

import com.xingcloud.XAService;
import com.xingcloud.model.Action;
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

    public static void main(String args[]) {
        String appid = "xa_demo";
        String uid = "123a" ;
        XAService xaService = new XAService(appid,uid);

        /*  init event just with a propertyName
         *  it's value will be set to zero
         *  it's timestamp will be set to when  the xa server receive it
         */
        Action visit = new Action("visit");
        Action buyFruit = new Action("buy.fruit", 1);
        Action pay = new Action("pay", 100l, System.currentTimeMillis() / 1000);
        Action heartbeat = new Action("heartbeat.*.*", 1l, System.currentTimeMillis());


        Update updateNation = new Update("nation", "USA");
        Update updateLevel = new Update("level", "1");

        String response = null;
        // action
        try {
            int timeout = 10000 ;
            // action
            response = xaService.action(visit, timeout);
            responseHandle(response);

            // update
            response = xaService.update(updateNation, timeout);
            responseHandle(response);

            // batch action
            response = xaService.batch(Arrays.asList(pay, heartbeat), timeout);
            responseHandle(response);

            // batch update
            response = xaService.batch(Arrays.asList(updateNation, updateLevel), timeout);
            responseHandle(response);

            // batch action and update
            response = xaService.batch(Arrays.asList(pay, heartbeat,updateNation), timeout);
            responseHandle(response);

            // batch  with current known timestamp  on the client side
            // all actions' accurate happen time will be corrected  on the server side
            // by the current time on the client side and time recorded in actions
            // often used in condition  the the clock is not accurate on the client side
            response = xaService.batch(Arrays.asList(pay), System.currentTimeMillis(), timeout);
            responseHandle(response);

            // batch  with absolute timestamp
            // all timestamp recorded in actions will be ignored and  replaced by the absoluteTs
            // rarely used
            response = xaService.batch(System.currentTimeMillis() - 86400, Arrays.asList(pay, heartbeat), timeout);
            responseHandle(response);

        } catch (IOException e) {
            e.printStackTrace();
            // TODO exception handle
        }
    }

    private static void responseHandle(String jsonResponse) {
        // TODO
        System.out.println(jsonResponse);

        /*
        *   {"stats":"ok","time":"0.80 ms","message":"store 2 action and 0 update "}
        */
    }

}
