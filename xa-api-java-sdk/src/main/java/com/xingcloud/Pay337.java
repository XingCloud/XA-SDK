package com.xingcloud;

import com.sun.org.apache.xml.internal.security.algorithms.implementations.SignatureBaseRSA;
import com.xingcloud.http.HttpToolkit;
import com.xingcloud.http.RequestParams;
import com.xingcloud.model.Pay;


import java.io.IOException;
import java.security.MessageDigest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 7/1/13
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class Pay337 {


    private static final String url = "http://pay.337.com/payelex/api/mobile/mobile_transaction.php";
    private static final String apiKey = "e6WyOz,PnAuvDd7JYjO," ;

    private String appid;
    private String uid;

    public Pay337(String appid) {
        this.appid = appid;
    }

    public Pay337(String appid, String uid) {
        this.appid = appid;
        this.uid = uid;
    }

    public String pay(String uid, Pay pay, int timeout) throws IOException {
        Map<String, Object> params = pay.getPayInfo();
        params.put("uid", uid);
        params.put("appId", appid);


        List<String> sortedKyes = new ArrayList<String>();
        sortedKyes.addAll(params.keySet());
        Collections.sort(sortedKyes, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        String token = apiKey;
        for (String key : sortedKyes) {
            if (!"token".equals(key)) {
                token += key + params.get(key);
            }
        }

        // md5
        params.put("token", MD5(token).toUpperCase());


        return HttpToolkit.doPost(url, new RequestParams(params), 10);
    }

    public String pay(Pay pay, int timeout) throws IOException {
        return pay(uid, pay, timeout);
    }


    private String MD5(String input) {
        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.reset();

            messageDigest.update(input.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

}
