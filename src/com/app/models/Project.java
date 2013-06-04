package com.app.models;

import com.app.util.ListDataBinder;

public class Project implements ListDataBinder {
	public int Id;
	public String Name;
	public String Remark;
	
	@Override
	public String getText() {
		return this.Name;
	}
	
	@Override
	public String getValue() {
		return Integer.toString(this.Id);
	}	
}
