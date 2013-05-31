package com.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/*
 * 类	  名:Common
 * 描          述：通用工具类
 * 作          者：long
 * 编码日期：2013-05-31
 */
public class Common {
	/*
	 * 检查设备是否连接上Wifi
	 */
	public static boolean isConnectedWifi(Context context){
		ConnectivityManager connManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		
		return mWifi.isConnected();
	}
	
	public static void showToastMsg(Context context, String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
