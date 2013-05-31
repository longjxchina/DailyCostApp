package com.app.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class RemoteDataHelper {
	/*
	 * 从远程获取json数据
	 */
	public static JSONObject doGet(String url) {
		try {
			String result = null;
			DefaultHttpClient httpClient = new DefaultHttpClient();

			HttpGet request = new HttpGet(url);
			request.addHeader("Accept", "application/json");
			HttpResponse response = httpClient.execute(request);
			result = EntityUtils.toString(response.getEntity());
			JSONObject object = new JSONObject(result);
			Log.i("HttpActivity", result);
			
			return object;
		} catch (Exception e) {
			Log.i("HttpActivity", "连接失败！详细错误：" + e.getMessage());
		}

		return null;
	}
}
