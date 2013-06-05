package com.app.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.app.database.DBHelper;
import com.app.models.Daily;

public class DailyDao {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	
	public DailyDao(Context context){
		dbHelper = new DBHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	
	public void add(Daily model){
		db.execSQL("INSERT INTO Daily (Theme,Cost,AddTime,Remark,FinanceType,CreateBy,LastUpdateBy,LastUpdateDate,ForDate,ProjectId) VALUES (?,?,?,?,?,?,?,?,?,?)", 
					new Object[]{model.Theme.replace("'", "''"),
								 model.Cost,
								 model.AddTime,
								 model.Remark.replace("'", "''"),
								 model.FinanceType,
								 model.CreateBy,
								 model.LastUpdateBy,
								 model.LastUpdateDate,
								 model.ForDate,
								 model.ProjectId});
	}
	
	public void delete(int id){
		db.execSQL("delete from daily where id=?", new Object[] { id } );
	}
	
	public List<Daily> getList() {  
        ArrayList<Daily> arrModels = new ArrayList<Daily>();  
        Cursor c;
        
        c = queryTheCursor();
        
        while (c.moveToNext()) {  
        	Daily model = new Daily();  
            
        	model.Id = c.getInt(c.getColumnIndex("Id"));
        	model.Theme = c.getString(c.getColumnIndex("Theme"));
        	model.Cost = c.getDouble(c.getColumnIndex("Cost"));
        	model.Remark = c.getString(c.getColumnIndex("Remark"));
        	model.AddTime = c.getString(c.getColumnIndex("AddTime"));
        	model.FinanceType = c.getInt(c.getColumnIndex("FinanceType"));
			model.CreateBy = c.getString(c.getColumnIndex("CreateBy"));
			model.LastUpdateBy = c.getString(c.getColumnIndex("LastUpdateBy"));
			model.LastUpdateDate = c.getString(c.getColumnIndex("LastUpdateDate"));
			model.ForDate = c.getString(c.getColumnIndex("ForDate"));
			model.ProjectId = c.getInt(c.getColumnIndex("ProjectId"));
        	
			arrModels.add(model);
        }  
        c.close();  
        return arrModels;  
    }
	
	public Cursor queryTheCursor() {  
        Cursor c = db.rawQuery("SELECT * FROM Daily", null);  
        return c;  
    }

	public void update(Daily model) {
		db.execSQL("update Daily set Theme=?,Cost=?,Remark=?,FinanceType=?,LastUpdateBy=?,LastUpdateDate=?,ForDate=?,ProjectId=? where id=?", 
				new Object[]{model.Theme.replace("'", "''"),
							 model.Cost,
							 model.Remark.replace("'", "''"),
							 model.FinanceType,
							 model.CreateBy,
							 model.LastUpdateBy,
							 model.ForDate,
							 model.ProjectId,
							 model.Id});
	}
}
