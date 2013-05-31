package com.app.dailycostapp;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.app.dailycostapp.R;
import com.app.service.ProjectService;
import com.app.util.Common;

public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		noticeWifiStatus();
		
		ListView lvToDo = (ListView)findViewById(R.id.lvToDo);
		final ArrayList<String> arrToDo = new ArrayList<String>();
		final ArrayAdapter<String> arrAdpt = new ArrayAdapter<String>(this,
																   R.layout.todo_list,
																   arrToDo);
		
		lvToDo.setAdapter(arrAdpt);
		
		arrToDo.add("a");
		arrToDo.add("b");
		arrToDo.add("c");
		arrAdpt.notifyDataSetChanged();
		
		ImageButton imgSyncBaseData = (ImageButton)findViewById(R.id.imgSyncBaseData);
		
		imgSyncBaseData.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View v) {
		if(R.id.imgSyncBaseData == v.getId()){
			syncBaseData();
		}
	}
	
	private void syncBaseData(){
		String dictUrl = this.getString(R.string.sync_dict_url);
		String dictItemUrl = this.getString(R.string.sync_dict_items_url);
		String projectUrl = this.getString(R.string.sync_project_url);		
		ProjectService projSvc = new ProjectService();
		
		projSvc.SyncProject(projectUrl);
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
