package com.xingcloud.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 7/1/13
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pay {



    public final static String APP_SUCCESS = "APP_SUCCESS" ;
    public final static String APP_FAILED  = "APP_FAILED" ;
    public final static String APP_REFUNDED  = "APP_REFUNDED" ;

    public final static String GOOGLE_CHECKOUT = "googlecheckout" ;

    private Map<String, Object> payInfo = new HashMap<String, Object>();

    /**
     *
     * @param channelTransId
     * @param status   APP_SUCCESS APP_FAILED APP_REFUNDED
     * @param channel   GOOGLE_CHECKOUT
     * @param amount
     * @param currency
     * @param gross
     * @param fee
     * @param timestamp
     */
    public Pay(String channelTransId,
               String status,
               String channel,
               Long amount,
               String currency,
               Long gross,
               Long fee,
               Long timestamp) {

        payInfo.put("channelTransId", channelTransId);
        payInfo.put("status", status);
        payInfo.put("channel", channel);
        payInfo.put("amount", amount);
        payInfo.put("currency", currency);
        payInfo.put("gross", gross);
        payInfo.put("fee", fee);
        String ts = timestamp.toString() ;
        if(ts.length() >= 10)
            ts = ts.substring(0,10);
        payInfo.put("timestamp",ts);

    }

    public Map<String, Object> getPayInfo() {
        return payInfo;
    }
}
