package com.app.models;

import com.app.util.ListDataBinder;

public class Daily implements ListDataBinder{
	public int Id;
	public String Theme;
	public double Cost;
	public String ForDate;	
	public int FinanceType;
	public int ProjectId;
	public String Remark;	
	public String CreateBy;
	public String LastUpdateBy;
	public String AddTime;
	public String LastUpdateDate;
	
	@Override
	public String getText() {
		// TODO �б���ʾ��ʽ������ - ����  - �������� - ���
		return this.ForDate + " - " + this.Theme;
	}
	@Override
	public String getValue() {
		return Integer.toString(this.Id);
	}
}
