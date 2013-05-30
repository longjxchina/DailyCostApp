package com.app.dailycostapp;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.dailycostapp.R;
import com.app.dao.ProjectDao;
import com.app.database.DBHelper;
import com.app.models.Project;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
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
		
		ProjectDao projDao = new ProjectDao(this);
		Project proj = new Project();
		proj.Name = "lxl";
		proj.Remark = "";
		projDao.add(proj);
		List<Project> lstProj = projDao.getList();
		for(int i = 0;i < lstProj.size(); i++){
			arrToDo.add(lstProj.get(i).Name);
		}
		arrAdpt.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
