package com.app.util;

import java.util.List;

import com.app.dailycostapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DictItemsListAdapter<T extends ListDataBinder> extends CommonListAdapter<T> {

	public DictItemsListAdapter(Context ctx, List<T> lstDataSource) {
		super(ctx, lstDataSource);
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
			tvValue.setText(lstDataSource.get(position).getText(context));
		}
		return convertView;
	}

	@Override
	public int getPosition(String key){
		for(T t : lstDataSource){
			if (key.equals(t.getText(context))){
				return lstDataSource.indexOf(t);
			}
		}
		
		return -1;
	}
}
