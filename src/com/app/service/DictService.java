package com.app.service;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import com.app.dao.DictDao;
import com.app.models.Dict;
import com.app.util.RemoteDataHelper;

public class DictService {
	private DictDao dao;

	public DictService(Context context) {
		dao = new DictDao(context);
	}

	public JSONArray SyncDict(String url) throws JSONException, Exception {
		JSONArray arrJson = null;

		arrJson = RemoteDataHelper.getJSONArray(url);

		dao.emptyData();

		for (int i = 0; i < arrJson.length(); i++) {
			JSONObject json = arrJson.getJSONObject(i);
			Dict model = new Dict();

			model.DictID = json.getInt("DictID");
			model.Code = json.getString("Code");
			model.DictName = json.getString("DictName");
			model.Remark = json.getString("Remark");

			dao.add(model);
		}

		return arrJson;
	}

	public List<Dict> getList() {
		return dao.getList();
	}
}
