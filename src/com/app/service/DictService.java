package com.app.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.app.dao.DictDao;
import com.app.models.Dict;
import com.app.util.RemoteDataHelper;

public class DictService {
	private DictDao dao;
	private final static String TAG = "DictService";
	
	public DictService(Context context){
		dao = new DictDao(context);
	}
	
	public JSONArray SyncDict(String url){
		JSONArray arrJson = null; 
		
		try{
			arrJson = RemoteDataHelper.getJSONArray(url); 
			
			dao.emptyData();
			
			for(int i = 0; i < arrJson.length(); i++){
				JSONObject json = arrJson.getJSONObject(i);
				Dict model = new Dict();
				
				model.DictID = json.getInt("DictID");
				model.Code = json.getString("Code");
				model.DictName = json.getString("DictName");				
				model.Remark = json.getString("Remark");
				
				dao.add(model);				
			}
		}
		catch(Exception ex){
			Log.e(TAG, "同步Dict出错！详细信息：" + ex.getMessage());
		}
		
		return arrJson;
	}
	
	public List<Dict> getList() {
		return dao.getList();
	}
}
