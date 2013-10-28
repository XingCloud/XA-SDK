package com.xingcloud.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Cutil {
	/**
	 * 检查参数中是否有空值
	 * @param names
	 * @return
	 */
	public static boolean checkNull(String... names){
		for(int i=0;i<names.length;i++){
			if(names[i] == null || names[i].trim().equals("")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查多个对象是否为null
	 * @param objects
	 * @return
	 */
	public static boolean checkNull(Object...objects){
		for(int i=0;i<objects.length;i++){
			if(objects[i] == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查网络是否畅通
	 * @param c
	 * @return
	 */
	public static boolean isConnected(Context c){
		try{
			 ConnectivityManager connectivity = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE); 
			 if (connectivity != null) { 
				 NetworkInfo info = connectivity.getActiveNetworkInfo();
				 if (info != null&& info.isConnected()) { 
					 if (info.getState() == NetworkInfo.State.CONNECTED) { 
						 return true;
					 }
				 }
			 }
		 }catch(Exception e){
			 
		 }
		 return false;
	}
}
