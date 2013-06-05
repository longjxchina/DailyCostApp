package com.app.models;

import java.io.Serializable;

import android.content.Context;

import com.app.service.DictEnum;
import com.app.service.DictItemsService;
import com.app.util.ListDataBinder;

public class Daily implements ListDataBinder, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public String getText(Context context) {
		DictItemsService diSvc = new DictItemsService(context);
		DictItems diFinanceType = diSvc.getEntity(DictEnum.FinanceType.toString(), 
												  Integer.toString(this.FinanceType));
		
		
		// TODO 测试  列表显示格式：日期 - 主题  - 财务类型 - 金额
		return String.format("%s - %s - %s - %s",
							 this.ForDate,
							 this.Theme,
							 diFinanceType == null ? "" : diFinanceType.ItemName,
							 this.Cost);
	}
	@Override
	public String getValue(Context context) {
		return Integer.toString(this.Id);
	}
}
