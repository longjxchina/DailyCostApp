package com.app.dailycostapp;

import java.util.List;

import com.app.models.*;
import com.app.service.*;
import com.app.util.Common;
import com.app.util.CommonListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class DailyAdd extends Activity implements OnClickListener {
	protected Spinner spProject;
	protected Spinner spTheme;
	protected DatePicker dpAddDate;
	protected Spinner spFinanceType;
	protected EditText etRemark;
	protected Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.daily_add);
		spProject = (Spinner)findViewById(R.id.spProject);
		spTheme = (Spinner)findViewById(R.id.spTheme);
		dpAddDate = (DatePicker)findViewById(R.id.dpAddDate);
		spFinanceType = (Spinner)findViewById(R.id.spFinanceType);
		etRemark = (EditText)findViewById(R.id.etRemark);
		btnSave = (Button)findViewById(R.id.btnSave);
	
		initUI();
	}

	/*
	 * 初始化
	 */
	private void initUI() {
		bindProject();
		bindTheme();
		bindFinanceType();
		
		btnSave.setOnClickListener(this);
	}
	
	/*
	 * 绑定财务类型
	 */
	private void bindFinanceType() {
		List<DictItems> lstData = new DictItemsService(this).getList();
		CommonListAdapter<DictItems> arrAdpt = new CommonListAdapter<DictItems>(this, lstData);		
		spFinanceType.setAdapter(arrAdpt);
	}
	
	/*
	 * 绑定常用花费名称
	 */
	private void bindTheme() {
		List<DictItems> lstData = new DictItemsService(this).getList(Integer.toString(DictEnum.CommonTheme.ordinal()));
		CommonListAdapter<DictItems> arrAdpt = new CommonListAdapter<DictItems>(this, lstData);		
		spTheme.setAdapter(arrAdpt);
	}

	/*
	 * 绑定项目
	 */
	private void bindProject() {
		List<Project> lstProject = new ProjectService(this).getList();
		CommonListAdapter<Project> arrAdpt = new CommonListAdapter<Project>(this, lstProject);		
		spProject.setAdapter(arrAdpt);
	}

	/*
	 * 按钮点击事件
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View view){
		switch(view.getId())
		{
			case R.id.btnSave:
				saveDaily();
				break;
			case R.id.btnCancel:
				break;		
		}
	}

	/*
	 * 保存
	 */
	private void saveDaily() {
		Daily model = new Daily();
		
		model.ProjectId = ((Project)spProject.getSelectedItem()).Id;
	}
}
