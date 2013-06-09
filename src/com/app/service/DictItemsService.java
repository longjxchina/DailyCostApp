package com.app.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.app.dao.DictItemsDao;
import com.app.models.DictItems;
import com.app.util.RemoteDataHelper;

public class DictItemsService {
	private DictItemsDao dao;

	public DictItemsService(Context context) {
		dao = new DictItemsDao(context);
	}

	/*
	 * 根据字典和值获取实体
	 */
	public DictItems getEntity(String dictCode, String itemValue) {
		return dao.getEntity(dictCode, itemValue);
	}

	public JSONArray SyncDictItems(String url) throws JSONException, Exception {
		JSONArray arrJson = null;

		arrJson = RemoteDataHelper.getJSONArray(url);

		dao.emptyData();

		for (int i = 0; i < arrJson.length(); i++) {
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

		return arrJson;
	}

	public List<DictItems> getList() {
		return dao.getList(null);
	}

	public List<DictItems> getList(String dictCode) {
		return dao.getList(dictCode);
	}

	public void add(DictItems model) {
		dao.add(model);
	}
}
