package com.app.models;

import com.app.util.ListDataBinder;

public class DictItems  implements ListDataBinder{
	public int DictItemId;
	public String DictCode;
	public String ItemCode;
	public String ItemName;
	public String ItemValue;
	public String Remark;
	@Override
	public String getText() {
		return this.ItemName;
	}
	@Override
	public String getValue() {
		return this.ItemValue;
	}
}
