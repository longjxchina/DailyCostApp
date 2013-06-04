package com.app.dailycostapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.app.models.*;
import com.app.service.*;
import com.app.util.Common;
import com.app.util.CommonListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

@SuppressLint("SimpleDateFormat")
public class DailyAdd extends Activity implements OnClickListener {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	
	protected Spinner spProject;
	protected Spinner spTheme;
	protected EditText etForDate;
	protected Spinner spFinanceType;
	protected EditText etMoney;
	protected EditText etRemark;
	protected Button btnSave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.daily_add);
		spProject = (Spinner)findViewById(R.id.spProject);
		spTheme = (Spinner)findViewById(R.id.spTheme);
		etForDate = (EditText)findViewById(R.id.etForDate);
		spFinanceType = (Spinner)findViewById(R.id.spFinanceType);
		etMoney = (EditText)findViewById(R.id.etMoney);
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
		
		etForDate.setText(sdf.format(cal.getTime()));		
		btnSave.setOnClickListener(this);
	}
	
	/*
	 * 绑定财务类型
	 */
	private void bindFinanceType() {
		List<DictItems> lstData = new DictItemsService(this).getList(DictEnum.FinanceType.toString());
		CommonListAdapter<DictItems> arrAdpt = new CommonListAdapter<DictItems>(this, lstData);		
		spFinanceType.setAdapter(arrAdpt);
	}
	
	/*
	 * 绑定常用花费名称
	 */
	private void bindTheme() {
		List<DictItems> lstData = new DictItemsService(this).getList(DictEnum.CommonTheme.toString());
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
		DailyService dailySvc = new DailyService(this);
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		model.Theme = ((DictItems)spTheme.getSelectedItem()).ItemName;
		model.Cost = Double.parseDouble(etMoney.getText().toString());
		model.ForDate = etForDate.getText().toString();
		model.FinanceType = Integer.parseInt(((DictItems)spFinanceType.getSelectedItem()).ItemValue);
		model.ProjectId = ((Project)spProject.getSelectedItem()).Id;
		model.Remark = etRemark.getText().toString();
		model.CreateBy = "lxl";
		model.LastUpdateBy = "lxl";
		model.AddTime = dateFmt.format(new Date());
		model.LastUpdateDate = dateFmt.format(new Date());
		
		dailySvc.add(model);
		Common.showToastMsg(this, getString(R.string.save_success));
	}
}
