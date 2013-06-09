package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.content.Context;

import com.app.dao.DailyDao;
import com.app.models.Daily;
import com.app.util.RemoteDataHelper;

public class DailyService {
	private DailyDao dao;
	//private final static String TAG = "DailyService";
	
	public DailyService(Context context){
		dao = new DailyDao(context);
	}
	
	public List<Daily> getList() {
		return dao.getList();
	}
	
	public void add(Daily model){
		dao.add(model);
	}
	
	public boolean updateLoadData(String syncUrl) throws Exception{
		List<Daily> lstData = dao.getList();
		ArrayList<NameValuePair> data;
		
		boolean result = true;
		
		for (Daily model : lstData){
			data = new ArrayList<NameValuePair>();
			
			data.add(new BasicNameValuePair("Id", Integer.toString(model.Id)));
			data.add(new BasicNameValuePair("Theme", model.Theme));
			data.add(new BasicNameValuePair("Cost", Double.toString(model.Cost)));
			data.add(new BasicNameValuePair("Remark", model.Remark));
			data.add(new BasicNameValuePair("AddTime", model.AddTime));
			data.add(new BasicNameValuePair("FinanceType", Integer.toString(model.FinanceType)));
			data.add(new BasicNameValuePair("CreateBy", model.CreateBy));
			data.add(new BasicNameValuePair("LastUpdateBy", model.LastUpdateBy));
			data.add(new BasicNameValuePair("LastUpdateDate", model.LastUpdateDate));
			data.add(new BasicNameValuePair("ForDate", model.ForDate));
			data.add(new BasicNameValuePair("ProjectId", Integer.toString(model.ProjectId)));
			
			String returnData = RemoteDataHelper.postRequest(syncUrl, data);
			
			if ("true".equals(returnData)){
				dao.delete(model.Id);
			}
		}
		
		return result;
	}

	public void delete(int id) {
		dao.delete(id);		
	}

	public void update(Daily model) {
		dao.update(model);		
	}
}
