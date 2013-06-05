package com.app.models;

import android.content.Context;

import com.app.util.ListDataBinder;

public class Project implements ListDataBinder {
	public int Id;
	public String Name;
	public String Remark;
	
	@Override
	public String getText(Context context) {
		return this.Name;
	}
	
	@Override
	public String getValue(Context context) {
		return Integer.toString(this.Id);
	}	
}
