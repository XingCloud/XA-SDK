package com.xingcloud.http;

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

        String response = "" ;
        URL url = new URL(requestUrl) ;
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
             response += line ;
        }
        bufferedReader.close();
        return response ;
    }

    public static String doPost(String requestUrl,RequestParams requestParams,int timeout) throws IOException{

        String response = "" ;
        URL url = new URL(requestUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.getOutputStream().write(requestParams.toQueryString().getBytes());
        httpURLConnection.getOutputStream().flush();
        httpURLConnection.getOutputStream().close();
        httpURLConnection.setConnectTimeout(timeout);
        httpURLConnection.connect();


        int code = httpURLConnection.getResponseCode()   ;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())) ;
        String line = null ;
        while((line = bufferedReader.readLine()) != null){
            response += line ;
        }
        bufferedReader.close();
        return response ;
    }
}
