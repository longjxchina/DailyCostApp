package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.database.DBHelper;
import com.app.models.Dict;

public class DictDao {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public DictDao(Context context){
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void add(Dict model){
		db.execSQL("insert into Dict(DictID,Code,DictName,Remark) values(?,?,?,?)", 
					new Object[]{ model.DictID, model.Code, model.DictName, model.Remark });
	}
	
	public void emptyData(){
		db.execSQL("delete from Dict");
	}
	
	public List<Dict> getList() {  
        ArrayList<Dict> arrModel = new ArrayList<Dict>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
        	Dict model = new Dict();  
            
        	model.DictID = c.getInt(c.getColumnIndex("DictId"));
        	model.Code = c.getString(c.getColumnIndex("Code"));
        	model.DictName = c.getString(c.getColumnIndex("DictName"));
        	model.Remark = c.getString(c.getColumnIndex("Remark"));
        	
        	arrModel.add(model);
        }  
        c.close();  
        return arrModel;  
    }
	
	/** 
     * query all Model, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM Dict", null);  
        return c;  
    }
}
