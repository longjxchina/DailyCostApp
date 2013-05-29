package com.app.dailycostapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.dailycostapp.R;

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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
