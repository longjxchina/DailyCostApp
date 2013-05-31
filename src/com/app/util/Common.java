package com.app.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/*
 * ��	  ��:Common
 * ��          ����ͨ�ù�����
 * ��          �ߣ�long
 * �������ڣ�2013-05-31
 */
public class Common {
	/*
	 * ����豸�Ƿ�������Wifi
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
