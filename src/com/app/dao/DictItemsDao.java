package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.database.DBHelper;
import com.app.models.DictItems;

public class DictItemsDao {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public DictItemsDao(Context context){
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void add(DictItems model){
		db.execSQL("insert into DictItems(DictItemId,DictCode,ItemCode,ItemName,ItemValue,Remark) values(?,?,?,?,?,?)", 
					new Object[]{ model.DictItemId, model.DictCode, model.ItemCode, model.ItemName, model.ItemValue, model.Remark});
	}
	
	public void emptyData(){
		db.execSQL("delete from DictItems");
	}
	
	public List<DictItems> getList() {  
        return getList(null);
    }

	public List<DictItems> getList(String dictCode) {  
        ArrayList<DictItems> arrModel = new ArrayList<DictItems>();  
        Cursor c; 
        		
        if (dictCode == null || dictCode.length() == 0){
        	c = queryTheCursor();
        }
        else {
        	c = queryTheCursor(dictCode);
        }
                		  
        while (c.moveToNext()) {  
        	DictItems model = new DictItems();  
            
        	model.DictItemId = c.getInt(c.getColumnIndex("DictItemID"));
        	model.DictCode = c.getString(c.getColumnIndex("DictCode"));
        	model.ItemCode = c.getString(c.getColumnIndex("ItemCode"));
        	model.ItemName = c.getString(c.getColumnIndex("ItemName"));
        	model.ItemValue = c.getString(c.getColumnIndex("ItemValue"));
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
    public Cursor queryTheCursor(String dictCode) {
    	Cursor c;
    	
    	if (dictCode == null){
    		c = db.rawQuery("SELECT * FROM DictItems", null);    		
    	}
    	else{    	
    		c = db.rawQuery(String.format("SELECT * FROM DictItems where DictCode='%s'", dictCode), null);
    	}
    	
        return c;  
    }
    
    public Cursor queryTheCursor() {
    	return queryTheCursor(null);
    }
}
