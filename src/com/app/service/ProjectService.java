package com.app.service;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.app.util.*;

public class ProjectService {
	public ProjectService(){
		
	}
	
	public void SyncProject(String url){
		JSONObject json = RemoteDataHelper.doGet(url);
		JSONArray arrJson; 
		
		try{
			arrJson = json.getJSONArray("data");
		}
		catch(Exception ex){
			Log.e("Error", "¶ÁÈ¡Project Json³ö´í£¡");
		}
	}
}
