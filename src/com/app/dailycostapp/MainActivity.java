package com.app.dailycostapp;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
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
import com.app.dailycostapp.R;
import com.app.models.Daily;
import com.app.models.DictItems;
import com.app.models.Project;
import com.app.service.DailyService;
import com.app.service.DictEnum;
import com.app.service.DictItemsService;
import com.app.service.DictService;
import com.app.service.GlobalConst;
import com.app.service.ProjectService;
import com.app.util.Common;
import com.app.util.CommonListAdapter;

public class MainActivity extends Activity implements OnClickListener  {
	ListView lvDaily;
	CommonListAdapter<Daily> arrAdpt;
	DailyService dailySvc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		dailySvc = new DailyService(this);
		lvDaily = (ListView)findViewById(R.id.lvDaily);
		
		// bindDaily();		
		bindEvent();
		
		// TODO 列表的删除，数据修改
		// buildTestData();
	}

	private void buildTestData() {
		ProjectService projSvc = new ProjectService(this);
		DictItemsService diService = new DictItemsService(this);
		Project proj = new Project();
		proj.Id = 2;
		proj.Name="项目2";
		
		DictItems itemCommon = new DictItems();
		DictItems itemFinance = new DictItems();
		
		itemCommon.DictItemId = 3;
		itemCommon.DictCode = DictEnum.CommonTheme.toString();
		itemCommon.ItemCode = "3";
		itemCommon.ItemName = "常用2";
		itemCommon.ItemValue = "3";
		
		itemFinance.DictItemId = 4;
		itemFinance.DictCode = DictEnum.FinanceType.toString();
		itemFinance.ItemCode = "4";
		itemFinance.ItemName = "支出2";
		itemFinance.ItemValue = "4";
		projSvc.add(proj);
		diService.add(itemCommon);
		diService.add(itemFinance);
	}

	/*
	 * 绑定事件处理程序
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
	 * 绑定列表数据
	 */
	private void bindDaily() {
		DailyService dailySvc = new DailyService(this);
		List<Daily> lstData = dailySvc.getList();
		
		arrAdpt = new CommonListAdapter<Daily>(this,
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
            	  // TODO 查看
                  return true; /* true means: "we handled the event". */       
              case GlobalConst.CONTEXTMENU_MODIFY:
            	  editDaily(GlobalConst.OP_TYPE_MODIFY, dailyModel);
            	  // TODO 修改
                  return true; /* true means: "we handled the event". */
              case GlobalConst.CONTEXTMENU_DELETE:
            	  //ArrayList<Music> musics = biz.getMusics();
            	  //adapter.changeDataSet(musics);
            	  dailySvc.delete(dailyModel.Id);
            	  Common.showToastMsg(this, getString(R.string.delete_success));
            	  bindDaily();
            	  // TODO 删除
                  return true; /* true means: "we handled the event". */
         }
         
         return false;       
    }       
	
	/*
	 * 绑定点击事件
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
	 * 上传Daily到服务器
	 */
	private void UploadData() {
		final String syncUrl = this.getString(R.string.sync_daily);
		final DailyService dailySvc = new DailyService(this);
		
		if (!noticeWifiStatus()){
			return;
		}
		
		Runnable access = new Runnable() {			
			public void run() {
				dailySvc.UpdateLoadData(syncUrl);
			}
		};
		
		new Thread(access).start();
	}

	/*
	 * 点击新增
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

	/*
	 * 同步基础数据
	 */
	private void syncBaseData(){
		final String dictUrl = this.getString(R.string.sync_dict_url);
		final String dictItemsUrl = this.getString(R.string.sync_dict_items_url);
		final String projectUrl = this.getString(R.string.sync_project_url);
		final ProjectService projSvc = new ProjectService(this);
		final DictService dictSvc = new DictService(this);
		final DictItemsService dictItemsSvc = new DictItemsService(this);
		//final Context ctx = this;
		
		if (!noticeWifiStatus()){
			return;
		}
		
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
	
	private boolean noticeWifiStatus() {
		if (!Common.isConnectedWifi(this)){
			Common.showToastMsg(this, "wifi未连接！");
			return false;
		}
		else{
			Common.showToastMsg(this, "wifi已连接！");	
			return true;
		}
	}
}
