package com.app.util;


import java.util.List;

import com.app.dailycostapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommonListAdapter<T extends ListDataBinder> extends BaseAdapter {
	List<T> lstDataSource;
	Context context;
	
	public CommonListAdapter(Context ctx, List<T> lstDataSource){
		this.lstDataSource = lstDataSource;
		this.context = ctx;
	}
	
	@Override
	public int getCount() {
		return lstDataSource.size();
	}

	@Override
	public Object getItem(int position) {
		return lstDataSource.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public int getPosition(String key){
		for(T t : lstDataSource){
			if (key.equals(t.getValue(context))){
				return lstDataSource.indexOf(t);
			}
		}
		
		return -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater _LayoutInflater=LayoutInflater.from(context);
		convertView=_LayoutInflater.inflate(R.layout.list_item, null);
		
		if(convertView!=null)
		{
			TextView tvText = (TextView)convertView.findViewById(R.id.tvText);
			TextView tvValue =(TextView)convertView.findViewById(R.id.tvValue);
			tvText.setText(lstDataSource.get(position).getText(context));
			tvValue.setText(lstDataSource.get(position).getValue(context));
		}
		return convertView;
	}

}
