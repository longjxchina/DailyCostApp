package com.app.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RemoteDataHelper {
	private static final String TAG = "RemoteDataHelper";
		
	/**
	 * ��ȡjson����
	 * @param  url
	 * @return JSONObject
	 * @throws JSONException 
	 * @throws ConnectionException 
	 */
	public static JSONObject getJSON(String url) throws JSONException, Exception {		
		return new JSONObject(getRequest(url));
	}
	
	/**
	 * ��ȡjson����
	 * @param  url
	 * @return JSONObject
	 * @throws JSONException 
	 * @throws ConnectionException 
	 */
	public static JSONArray getJSONArray(String url) throws JSONException, Exception {		
		return new JSONArray(getRequest(url));
	}
	
	/*
	 * ����POST����
	 */
	public static JSONObject postReturnJSON(String url, List<NameValuePair> data) throws JSONException, Exception {		
		return new JSONObject(postRequest(url, data));
	}
	
	/**
	 * ��api����get���󣬷��شӺ�̨ȡ�õ���Ϣ��
	 * 
	 * @param url
	 * @return String
	 */
	protected static String getRequest(String url) throws Exception {
		return getRequest(url, new DefaultHttpClient(new BasicHttpParams()));
	}
	
	/**
	 * ��api����get���󣬷��شӺ�̨ȡ�õ���Ϣ��
	 * 
	 * @param url
	 * @param client
	 * @return String
	 */
	protected static String getRequest(String url, DefaultHttpClient client) throws Exception {
		String result = null;
		int statusCode = 0;
		HttpGet getMethod = new HttpGet(url);
		Log.d(TAG, "do the getRequest,url="+url+"");
		
		try {
			getMethod.setHeader("Accept", "application/json");
			HttpResponse httpResponse = client.execute(getMethod);
			//statusCode == 200 ����
			statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statuscode = "+statusCode);
			//�����ص�httpResponse��Ϣ
			result = retrieveInputStream(httpResponse.getEntity());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			throw new Exception(e);
		} finally {
			getMethod.abort();
		}
		return result;
	}
	
	public static String postRequest(String url,List<NameValuePair> parameters) throws Exception {
		return postRequest(url, parameters, new DefaultHttpClient());
	}
	
	/*
	 * ��api����post���󣬷��شӺ�̨ȡ�õ���Ϣ��
	 */
	protected static String postRequest(String url,List<NameValuePair> parameters, DefaultHttpClient client) throws Exception {
		String result = null;
		int statusCode = 0;
		HttpPost postMethod = new HttpPost(url);
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, "utf-8");
		
		Log.d(TAG, "do the postRequest,url="+url+"");
		
		try {
			postMethod.setHeader("Accept", "application/json");
			
			// ��ʵ���������õ��������
			postMethod.setEntity(entity);
			
			HttpResponse httpResponse = client.execute(postMethod);
			
			//statusCode == 200 ����
			statusCode = httpResponse.getStatusLine().getStatusCode();
			Log.d(TAG, "statuscode = "+statusCode);
			//�����ص�httpResponse��Ϣ
			result = retrieveInputStream(httpResponse.getEntity());
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			throw new Exception(e);
		} finally {
			postMethod.abort();
		}
		return result;
	}
	
	/**
	 * ����httpResponse��Ϣ,����String
	 * 
	 * @param httpEntity
	 * @return String
	 */
	protected static String retrieveInputStream(HttpEntity httpEntity) {				
		int length = (int) httpEntity.getContentLength();
		
		//the number of bytes of the content, or a negative number if unknown. If the content length is known but exceeds Long.MAX_VALUE, a negative number is returned.
		//length==-1��������䱨��println needs a message
		if (length < 0) {
			length = 10000;
		}
		
		StringBuffer stringBuffer = new StringBuffer(length);
		
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent(), HTTP.UTF_8);
			char buffer[] = new char[length];
			int count;
			while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
				stringBuffer.append(buffer, 0, count);
			}
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IllegalStateException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		return stringBuffer.toString();
	}
}
