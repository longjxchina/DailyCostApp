package com.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;
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
		showToastMsg(context, msg, Toast.LENGTH_LONG);
	}
	
	public static void showToastMsg(Context context, String msg, int remainTime){
		Toast.makeText(context, msg, remainTime).show();
	}
	
	public static void showToastMsg(Context context, int resId){
		showToastMsg(context, resId, Toast.LENGTH_LONG);
	}
	
	public static void showToastMsg(Context context, int resId, int remainTime){		
		Toast.makeText(context, resId, remainTime).show();
	}
	
	public static void showNonUIToastMsg(Context context, int resId){
		showNonUIToastMsg(context, resId, Toast.LENGTH_LONG);
	}
	
	public static void showNonUIToastMsg(Context context, int resId, int remainTime){
		Looper.prepare();
		Toast.makeText(context, resId, remainTime).show();
		Looper.loop();
	}
}
