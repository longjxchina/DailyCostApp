package com.app.dailycostapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import com.app.dailycostapp.R;
import com.app.models.Daily;
import com.app.service.DailyService;
import com.app.service.DictItemsService;
import com.app.service.DictService;
import com.app.service.ProjectService;
import com.app.util.Common;
import com.app.util.CommonListAdapter;

public class MainActivity extends Activity implements OnClickListener  {
	ListView lvToDo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		lvToDo = (ListView)findViewById(R.id.lvToDo);
		
		noticeWifiStatus();
		bindDaily();		
		
		ImageButton imgSyncBaseData = (ImageButton)findViewById(R.id.imgSyncBaseData);
		ImageButton imgBtnNew = (ImageButton)findViewById(R.id.imgBtnAddDaily);
		ImageButton imgBtnUpload = (ImageButton)findViewById(R.id.imgBtnUpload);
		
		imgSyncBaseData.setOnClickListener(this);
		imgBtnNew.setOnClickListener(this);
		imgBtnUpload.setOnClickListener(this);
		
		// TODO 列表的删除，数据修改
	}

	private void bindDaily() {
		DailyService dailySvc = new DailyService(this);
		List<Daily> lstData = dailySvc.getList();
		CommonListAdapter<Daily> arrAdpt = new CommonListAdapter<Daily>(this,
											lstData);
		
		lvToDo.setAdapter(arrAdpt);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
		
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.imgSyncBaseData:
				syncBaseData();
				break;
			case R.id.imgBtnAddDaily:
				AddDaily();
				break;
			case R.id.imgBtnUpload:
				UploadData();
				break;
		}
	}
	
	private void UploadData() {
		final String syncUrl = this.getString(R.string.sync_daily);
		final DailyService dailySvc = new DailyService(this);
		
		Runnable access = new Runnable() {			
			public void run() {
				dailySvc.UpdateLoadData(syncUrl);
			}
		};
		
		new Thread(access).start();
	}

	private void AddDaily() {
		Intent intent = new Intent(this, DailyAdd.class);
		startActivity(intent);
	}

	private void syncBaseData(){
		final String dictUrl = this.getString(R.string.sync_dict_url);
		final String dictItemsUrl = this.getString(R.string.sync_dict_items_url);
		final String projectUrl = this.getString(R.string.sync_project_url);
		final ProjectService projSvc = new ProjectService(this);
		final DictService dictSvc = new DictService(this);
		final DictItemsService dictItemsSvc = new DictItemsService(this);
		//final Context ctx = this;
		
		Runnable access = new Runnable() {			
			public void run() {
				try {	
					projSvc.SyncProject(projectUrl);
					dictSvc.SyncDict(dictUrl);
					dictItemsSvc.SyncDictItems(dictItemsUrl);
					
					//Common.showToastMsg(ctx, ctx.getString(R.string.sync_success));
				}
				catch(Exception ex){
					//Common.showToastMsg(ctx, ctx.getString(R.string.sync_error));
				}
			}
		};
		
		new Thread(access).start();		
	}
	
	private void noticeWifiStatus() {
		if (!Common.isConnectedWifi(this)){			
			Common.showToastMsg(this, "wifi未连接！");
		}
		else{
			Common.showToastMsg(this, "wifi已连接！");			
		}
	}
}
