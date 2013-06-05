package com.app.models;

import android.content.Context;

import com.app.util.ListDataBinder;

public class DictItems  implements ListDataBinder{
	public int DictItemId;
	public String DictCode;
	public String ItemCode;
	public String ItemName;
	public String ItemValue;
	public String Remark;
	@Override
	public String getText(Context context) {
		return this.ItemName;
	}
	@Override
	public String getValue(Context context) {
		return this.ItemValue;
	}
}
