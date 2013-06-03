package com.app.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.os.AsyncTask;
import android.util.Log;

public class HttpTask extends AsyncTask<HttpUriRequest, Integer, ArrayList<JSONObject>> {

	@Override
	protected ArrayList<JSONObject> doInBackground(HttpUriRequest... params) {
		ArrayList<JSONObject> arrJson = new ArrayList<JSONObject>();
		
		for(int i = 0; i < params.length; i++){
			HttpUriRequest request = params[i];
			HttpClient client = new DefaultHttpClient();
			
			try {
	        	// The UI Thread shouldn't be blocked long enough to do the reading in of the stream.
	        	HttpResponse response =  client.execute(request);	        	
	        	BufferedReader reader = new BufferedReader(
		        								new InputStreamReader(response.getEntity().getContent(), 
		        								"GB2312"));
	        	StringBuilder builder = new StringBuilder();
	        	
	        	for (String line = null; (line = reader.readLine()) != null ; ) {
	        		builder.append(line).append("\n");
	        	}
	        	
	        	JSONTokener tokener = new JSONTokener(builder.toString());
	        	JSONObject json = new JSONObject(tokener);
	        	
	        	arrJson.add(json);
			}
			catch(Exception ex){
				Log.e("SyncError", "Í¬²½" + request.getURI() + "³ö´í£¡");
			}
		}
    	
		return arrJson;
	}

}
