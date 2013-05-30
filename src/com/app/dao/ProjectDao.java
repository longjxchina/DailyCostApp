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
		db.execSQL("insert into project(projectname,remark) values(?,?)", 
					new Object[]{ proj.ProjectName, proj.Remark });
	}
	
	public List<Project> getList() {  
        ArrayList<Project> arrProjs = new ArrayList<Project>();  
        Cursor c = queryTheCursor();  
        while (c.moveToNext()) {  
        	Project proj = new Project();  
            
        	proj.Id = c.getInt(c.getColumnIndex("Id"));
        	proj.ProjectName = c.getString(c.getColumnIndex("ProjectName"));
        	proj.Remark = c.getString(c.getColumnIndex("Remark"));
        	
        	arrProjs.add(proj);
        }  
        c.close();  
        return arrProjs;  
    }  
	
	/** 
     * query all Project, return cursor 
     * @return  Cursor 
     */  
    public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM Project", null);  
        return c;  
    }  
}
