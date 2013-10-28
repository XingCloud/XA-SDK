package com.xingcloud.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: witwolf
 * Date: 6/20/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class HttpToolkit {

    public static String doGet(String requestUrl,int timeout) throws IOException {

        //System.out.println("Request:" + requestUrl);

        String respone = "" ;
        URL url = new URL(requestUrl) ;
        Log.d("XA",requestUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection() ;
        httpURLConnection.setConnectTimeout(timeout);
        httpURLConnection.setReadTimeout(timeout);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.connect();
        int code = httpURLConnection.getResponseCode()   ;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())) ;
        String line = null ;
        while((line = bufferedReader.readLine()) != null){
             respone += line ;
        }
        Log.d(String.valueOf(code),respone);
        bufferedReader.close();
        return respone ;
    }
}
