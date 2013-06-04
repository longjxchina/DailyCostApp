package com.app.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.app.dao.DictItemsDao;
import com.app.models.DictItems;
import com.app.util.RemoteDataHelper;

public class DictItemsService {
	private DictItemsDao dao;
	private final static String TAG = "DictItemService";
	
	public DictItemsService(Context context){
		dao = new DictItemsDao(context);
	}
	
	public JSONArray SyncDictItems(String url){
		JSONArray arrJson = null; 
		
		try{
			arrJson = RemoteDataHelper.getJSONArray(url); 
			
			dao.emptyData();
			
			for(int i = 0; i < arrJson.length(); i++){
				JSONObject json = arrJson.getJSONObject(i);
				DictItems model = new DictItems();
				
				model.DictItemId = json.getInt("DictItemID");
				model.DictCode = json.getString("DictCode");
				model.ItemCode = json.getString("ItemCode");
				model.ItemName = json.getString("ItemName");
				model.ItemValue = json.getString("ItemValue");
				model.Remark = json.getString("Remark");
				
				dao.add(model);				
			}
		}
		catch(Exception ex){
			Log.e(TAG, "同步DictItems出错！详细信息：" + ex.getMessage());
		}
		
		return arrJson;
	}
	
	public List<DictItems> getList() {
		return dao.getList(null);
	}
	
	public List<DictItems> getList(String dictCode) {
		return dao.getList(dictCode);
	}
}
