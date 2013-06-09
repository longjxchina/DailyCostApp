package com.app.dailycostapp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.app.models.*;
import com.app.service.*;
import com.app.util.Common;
import com.app.util.CommonListAdapter;
import com.app.util.DictItemsListAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

@SuppressLint("SimpleDateFormat")
public class DailyEdit extends Activity implements OnClickListener {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	
	private int opType;
	private Daily modifyDailyEntity;
	private CommonListAdapter<Project> projAdpt;
	private CommonListAdapter<DictItems> themeAdpt;
	private CommonListAdapter<DictItems> financeAdpt;
	
	protected Spinner spProject;
	protected Spinner spTheme;
	protected EditText etForDate;
	protected Spinner spFinanceType;
	protected EditText etMoney;
	protected EditText etRemark;
	protected Button btnSave;
	protected Button btnCancel;
	protected LinearLayout lnlOperate;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		opType = getIntent().getIntExtra(GlobalConst.OP_TYPE, GlobalConst.OP_TYPE_ADD);
		setTitleAndActionBar();
		setContentView(R.layout.daily_edit);
		spProject = (Spinner)findViewById(R.id.spProject);
		spTheme = (Spinner)findViewById(R.id.spTheme);
		etForDate = (EditText)findViewById(R.id.etForDate);
		spFinanceType = (Spinner)findViewById(R.id.spFinanceType);
		etMoney = (EditText)findViewById(R.id.etMoney);
		etRemark = (EditText)findViewById(R.id.etRemark);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnCancel = (Button)findViewById(R.id.btnCancel);
		lnlOperate = (LinearLayout)findViewById(R.id.lnlOperate);		
		
		initUI();		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void setTitleAndActionBar() {
		if (opType == GlobalConst.OP_TYPE_ADD || opType == GlobalConst.OP_TYPE_MODIFY){
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			// ActionBar actionBar =getActionBar();
			// actionBar.hide();
		}
		else{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
	            // Show the Up button in the action bar.
	            getActionBar().setDisplayHomeAsUpEnabled(true);
	        }
		}
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
		btnCancel.setOnClickListener(this);
		
		setView();
	}
	
	private void setView() {
		switch(opType){
			case GlobalConst.OP_TYPE_ADD:
				break;
			case GlobalConst.OP_TYPE_MODIFY:				
				loadEntity();
				break;
			case GlobalConst.OP_TYPE_SHOW:
				loadEntity();
				disabledView();
				break;
		}
		
	}

	private void disabledView() {		
		spProject.setEnabled(false);
		spTheme.setEnabled(false);
		etForDate.setEnabled(false);
		spFinanceType.setEnabled(false);
		etForDate.setEnabled(false);
		etMoney.setEnabled(false);
		etRemark.setEnabled(false);
		lnlOperate.setVisibility(View.GONE);
	}

	private void loadEntity() {
		modifyDailyEntity = (Daily)getIntent().getSerializableExtra(GlobalConst.DAILY);
		
		spProject.setSelection(projAdpt.getPosition(Integer.toString(modifyDailyEntity.ProjectId)));
		spTheme.setSelection(themeAdpt.getPosition(modifyDailyEntity.Theme));
		spFinanceType.setSelection(financeAdpt.getPosition(Integer.toString(modifyDailyEntity.FinanceType)));
		etForDate.setText(modifyDailyEntity.ForDate);
		etMoney.setText(Double.toString(modifyDailyEntity.Cost));
		etRemark.setText(modifyDailyEntity.Remark);
	}

	/*
	 * 绑定财务类型
	 */
	private void bindFinanceType() {
		List<DictItems> lstData = new DictItemsService(this).getList(DictEnum.FinanceType.toString());
		financeAdpt = new CommonListAdapter<DictItems>(this, lstData);		
		spFinanceType.setAdapter(financeAdpt);
	}
	
	/*
	 * 绑定常用花费名称
	 */
	private void bindTheme() {
		List<DictItems> lstData = new DictItemsService(this).getList(DictEnum.CommonTheme.toString());
		themeAdpt = new DictItemsListAdapter<DictItems>(this, lstData);		
		spTheme.setAdapter(themeAdpt);
	}

	/*
	 * 绑定项目
	 */
	private void bindProject() {
		List<Project> lstProject = new ProjectService(this).getList();
		projAdpt = new CommonListAdapter<Project>(this, lstProject);		
		spProject.setAdapter(projAdpt);
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
				finish();
				break;		
		}
	}

	/*
	 * 保存
	 */
	private void saveDaily() {
		Daily model;
		DailyService dailySvc = new DailyService(this);
		SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String money = etMoney.getText().toString();
		
		if (money.length() == 0){
			Common.showToastMsg(this, getString(R.string.money) + getString(R.string.empty_msg));
			etMoney.requestFocus();
			return;
		}
		
		if (!money.matches("(\\d+\\.)?\\d+")){
			Common.showToastMsg(this, getString(R.string.money) + getString(R.string.error_input_msg));
			etMoney.requestFocus();
			return;
		}
		
		if (opType == GlobalConst.OP_TYPE_MODIFY){
			model = modifyDailyEntity;
		}
		else{
			model = new Daily();
		}
		
		model.Theme = ((DictItems)spTheme.getSelectedItem()).ItemName;
		model.Cost = Double.parseDouble(money);
		model.ForDate = etForDate.getText().toString();
		model.FinanceType = Integer.parseInt(((DictItems)spFinanceType.getSelectedItem()).ItemValue);
		model.ProjectId = ((Project)spProject.getSelectedItem()).Id;
		model.Remark = etRemark.getText().toString();
		model.CreateBy = "lxl";
		model.LastUpdateBy = "lxl";
		model.AddTime = dateFmt.format(new Date());
		model.LastUpdateDate = dateFmt.format(new Date());
		
		if (opType == GlobalConst.OP_TYPE_MODIFY){
			dailySvc.update(model);
		}
		else{
			dailySvc.add(model);
		}
		
		Common.showToastMsg(this, getString(R.string.save_success));
		finish();
	}
}
