package com.app.database;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 13-5-30.
 */
public class DBHelper extends SQLiteOpenHelper {
    final static int DB_VERSION = 1;
    final static String DB_NAME = "dailycost";
    final static String CREATE_TABLE_DAILY = "CREATE TABLE Daily(Id integer PRIMARY KEY AUTOINCREMENT,Theme varchar(200) NULL,Cost float NOT NULL,AddTime timestamp NULL,Remark varchar(250)  NULL,FinanceType integer NULL,CreateBy varchar(50)  NULL,LastUpdateBy varchar(50)  NULL,LastUpdateDate timestamp NULL CONSTRAINT DF_tbDaily_LastUpdateDate  DEFAULT (getdate()),ForDate date NULL,ProjectId integer NOT NULL)";
    final static String CREATE_TABLE_DICT = "CREATE TABLE Dict(DictID integer PRIMARY KEY,Code varchar(50)  NULL,DictName nvarchar(100)  NULL,Remark nvarchar(500)  NULL,IsEnable boolean NULL,CreateBy varchar(50)  NULL,CreateDate timestamp NULL,CreatorName nvarchar(100)  NULL,LastUpdateBy varchar(50)  NULL,LastUpdateByName nvarchar(100)  NULL,LastUpdateDate timestamp NULL)";
    final static String CREATE_TABLE_DICT_ITEM = "CREATE TABLE DictItems(DictItemID integer PRIMARY KEY,DictCode varchar(50)  NULL,ItemCode varchar(50)  NULL,ItemName nvarchar(100)  NULL,ItemValue nvarchar(200)  NULL,Remark nvarchar(500)  NULL,IsEnable boolean NULL,CreateBy varchar(50)  NULL,CreatorName nvarchar(100)  NULL,CreateDate timestamp NULL,LastUpdateBy varchar(50)  NULL,LastUpdateByName nvarchar(100)  NULL,LastUpdateDate timestamp NULL)";
    final static String CREATE_TABLE_PROJECT = "CREATE TABLE Project(Id integer PRIMARY KEY,Name nvarchar(100)  NULL,Remark nvarchar(1000)  NULL)";
    Context context;

    public  DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        // Store the context for later use
        this.context = context;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		createTable(db);
	}

	private void createTable(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_DAILY);
		db.execSQL(CREATE_TABLE_DICT);
		db.execSQL(CREATE_TABLE_DICT_ITEM);
		db.execSQL(CREATE_TABLE_PROJECT);
	}

	private void initDataBase(SQLiteDatabase db, String sqlFile) {
		// TODO Auto-generated method stub
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte buf[] = new byte[1024];
	    int len;
	    AssetManager assetManager = context.getAssets();
	    InputStream inputStream = null;
	    
	    try{
	        inputStream = assetManager.open(sqlFile);
	        while ((len = inputStream.read(buf)) != -1) {
	            outputStream.write(buf, 0, len);
	        }
	        outputStream.close();
	        inputStream.close();
	             
	        String[] createScript = outputStream.toString().split(";");
	        for (int i = 0; i < createScript.length; i++) {
	                String sqlStatement = createScript[i].trim();
	            // TODO You may want to parse out comments here
	            if (sqlStatement.length() > 0) {
	                db.execSQL(sqlStatement + ";");
	            }
	        }
	    } catch (IOException e){
	        // TODO Handle Script Failed to Load
	    } catch (SQLException e) {
	        // TODO Handle Script Failed to Execute
	    }
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
