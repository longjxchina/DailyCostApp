package com.app.dailycostapp;

import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.app.dailycostapp.R;
import com.app.models.Daily;
import com.app.service.DailyService;
import com.app.service.DictItemsService;
import com.app.service.DictService;
import com.app.service.GlobalConst;
import com.app.service.ProjectService;
import com.app.util.Common;
import com.app.util.CommonListAdapter;

public class MainActivity extends Activity implements OnClickListener  {
	static ListView lvDaily;
	static CommonListAdapter<Daily> arrAdpt;
	static Context ctx;
	DailyService dailySvc;
	
	static final Handler syncDataHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			String result = msg.obj.toString();

			if (Integer.parseInt(result) == GlobalConst.MESSAGE_SUCCESS) {
				bindDaily();
			}
		}
	}; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		ctx = this;
		dailySvc = new DailyService(this);
		lvDaily = (ListView)findViewById(R.id.lvDaily);
				
		bindEvent();
		
		// buildTestData();
	}

	/*
	 * ���¼��������
	 */
	public void bindEvent() {
		ImageButton imgSyncBaseData = (ImageButton)findViewById(R.id.imgSyncBaseData);
		ImageButton imgBtnNew = (ImageButton)findViewById(R.id.imgBtnAddDaily);
		ImageButton imgBtnUpload = (ImageButton)findViewById(R.id.imgBtnUpload);
		
		imgSyncBaseData.setOnClickListener(this);
		imgBtnNew.setOnClickListener(this);
		imgBtnUpload.setOnClickListener(this);
		
		/* Add Context-Menu listener to the ListView. */       
        lvDaily.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			@Override
			public void onCreateContextMenu(ContextMenu ctxMenu, View view, ContextMenuInfo info) {
				ctxMenu.setHeaderTitle(R.string.operation);
				ctxMenu.add(0, GlobalConst.CONTEXTMENU_LOOKUP, 1, R.string.lookup);
				ctxMenu.add(0, GlobalConst.CONTEXTMENU_MODIFY, 2, R.string.modify);
				ctxMenu.add(0, GlobalConst.CONTEXTMENU_DELETE, 3, R.string.delete);
			}
        }); 
	}

	/*
	 * ���б�����
	 */
	private static void bindDaily() {
		DailyService dailySvc = new DailyService(ctx);
		List<Daily> lstData = dailySvc.getList();
		
		arrAdpt = new CommonListAdapter<Daily>(ctx,
											lstData);		
		lvDaily.setAdapter(arrAdpt);
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		bindDaily();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	
	@Override       
    public boolean onContextItemSelected(MenuItem item) {       
		AdapterContextMenuInfo info = (AdapterContextMenuInfo)item.getMenuInfo();
		Daily dailyModel = (Daily)arrAdpt.getItem(info.position);
      
         /* Switch on the ID of the item, to get what the user selected. */       
         switch (item.getItemId()) {       
              case GlobalConst.CONTEXTMENU_LOOKUP:
            	  editDaily(GlobalConst.OP_TYPE_SHOW, dailyModel);
                  return true; /* true means: "we handled the event". */       
              case GlobalConst.CONTEXTMENU_MODIFY:
            	  editDaily(GlobalConst.OP_TYPE_MODIFY, dailyModel);
                  return true; /* true means: "we handled the event". */
              case GlobalConst.CONTEXTMENU_DELETE:
            	  dailySvc.delete(dailyModel.Id);
            	  Common.showToastMsg(this, R.string.delete_success, Toast.LENGTH_SHORT);
            	  bindDaily();
                  return true; /* true means: "we handled the event". */
         }
         
         return false;       
    }       
	
	/*
	 * �󶨵���¼�
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.imgSyncBaseData:
				syncBaseData();
				break;
			case R.id.imgBtnAddDaily:
				editDaily(GlobalConst.OP_TYPE_ADD, null);
				break;
			case R.id.imgBtnUpload:
				UploadData();
				break;
		}
	}
	
	/*
	 * �ϴ�Daily��������
	 */
	private void UploadData() {
		final String syncUrl = this.getString(R.string.sync_daily);
		final DailyService dailySvc = new DailyService(this);
		
		if (!noticeWifiStatus()){
			return;
		}
		
		Runnable access = new Runnable() {			
			public void run() {
				Message msg = syncDataHandler.obtainMessage();
				try{
					dailySvc.UpdateLoadData(syncUrl);
					msg.obj = GlobalConst.MESSAGE_SUCCESS;
					Common.showNonUIToastMsg(getApplicationContext(), R.string.sync_success, Toast.LENGTH_SHORT);
				}
				catch(Exception ex){
					msg.obj = GlobalConst.MESSAGE_ERROR;
					Common.showNonUIToastMsg(getApplicationContext(), R.string.sync_error, Toast.LENGTH_SHORT);
				}
				finally{
					syncDataHandler.sendMessage(msg);
				}
			}
		};
		
		Common.showToastMsg(this, R.string.sync_start);
		new Thread(access).start();
	}
	
	/*
	 * ͬ����������
	 */
	private void syncBaseData(){
		final String dictUrl = this.getString(R.string.sync_dict_url);
		final String dictItemsUrl = this.getString(R.string.sync_dict_items_url);
		final String projectUrl = this.getString(R.string.sync_project_url);
		final ProjectService projSvc = new ProjectService(this);
		final DictService dictSvc = new DictService(this);
		final DictItemsService dictItemsSvc = new DictItemsService(this);
		
		if (!noticeWifiStatus()){
			return;
		}
		
		Runnable access = new Runnable() {			
			public void run() {
				Message msg = syncDataHandler.obtainMessage();
				
				try {	
					projSvc.SyncProject(projectUrl);
					dictSvc.SyncDict(dictUrl);
					dictItemsSvc.SyncDictItems(dictItemsUrl);
					
					msg.obj = GlobalConst.MESSAGE_SUCCESS;
					Common.showNonUIToastMsg(getApplicationContext(), R.string.sync_success, Toast.LENGTH_SHORT);
				}
				catch(Exception ex){
					msg.obj = GlobalConst.MESSAGE_ERROR;
					Common.showNonUIToastMsg(getApplicationContext(), R.string.sync_error, Toast.LENGTH_SHORT);
				}
				finally{
					syncDataHandler.sendMessage(msg);
				}
			}
		};
		
		Common.showToastMsg(this, getString(R.string.sync_start), Toast.LENGTH_SHORT);
		new Thread(access).start();		
	}
	

	/*
	 * �������
	 */
	private void editDaily(int opType, Daily model) {
		Intent intent = new Intent(this, DailyEdit.class);
		
		intent.putExtra(GlobalConst.OP_TYPE, opType);
		
		if (opType == GlobalConst.OP_TYPE_MODIFY 
			|| opType == GlobalConst.OP_TYPE_SHOW){
			intent.putExtra(GlobalConst.DAILY, model);
		}
		
		startActivity(intent);
	}

	private boolean noticeWifiStatus() {
		if (!Common.isConnectedWifi(this)){
			Common.showToastMsg(this, "wifiδ���ӣ�");
			return false;
		}
		
		return true;
	}
}