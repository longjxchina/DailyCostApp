package com.app.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.app.models.Project;
import com.app.util.*;
import com.app.dao.*;

public class ProjectService {
	private ProjectDao dao;
	private final static String TAG = "ProjectService";
	
	public ProjectService(Context context){
		dao = new ProjectDao(context);
	}
	
	public JSONArray SyncProject(String url){
		JSONArray arrJson = null; 
		
		try{
			arrJson = RemoteDataHelper.getJSONArray(url); 
			
			dao.emptyData();
			
			for(int i = 0; i < arrJson.length(); i++){
				JSONObject json = arrJson.getJSONObject(i);
				Project proj = new Project();
				
				proj.Id = json.getInt("Id");
				proj.Name = json.getString("Name");
				proj.Remark = json.getString("Remark");
				
				dao.add(proj);				
			}
		}
		catch(Exception ex){
			Log.e(TAG, "同步Project出错！详细信息：" + ex.getMessage());
		}
		
		return arrJson;
	}
	
	public List<Project> getList() {
		return dao.getList();
	}
}
