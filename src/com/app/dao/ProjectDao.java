package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.database.DBHelper;
import com.app.models.Project;

public class ProjectDao {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public ProjectDao(Context context){
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void add(Project proj){
		db.execSQL("insert into project(id, name,remark) values(?,?,?)", 
					new Object[]{proj.Id, proj.Name, proj.Remark });
	}
	
	public void emptyData(){
		db.execSQL("delete from project");
	}
	
	public List<Project> getList(String dictCode) {  
        ArrayList<Project> arrProjs = new ArrayList<Project>();  
        Cursor c;
        
        if (dictCode == null || dictCode.length() == 0){
        	c = queryTheCursor();
        }else{
        	c = queryTheCursor(dictCode);
        }
        
        while (c.moveToNext()) {  
        	Project proj = new Project();  
            
        	proj.Id = c.getInt(c.getColumnIndex("Id"));
        	proj.Name = c.getString(c.getColumnIndex("Name"));
        	proj.Remark = c.getString(c.getColumnIndex("Remark"));
        	
        	arrProjs.add(proj);
        }  
        c.close();  
        return arrProjs;  
    }
	
	public List<Project> getList() {  
		return getList(null);
	}
	
	/** 
     * query all Project, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM Project", null);  
        return c;  
    }  
    
    /** 
     * query all Project, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor(String dictCode) {  
        Cursor c = db.rawQuery(String.format("SELECT * FROM Project where DictCode='%s'", dictCode), null);  
        return c;  
    }
}
