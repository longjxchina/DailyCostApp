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
    Context context;

    public  DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        // Store the context for later use
        this.context = context;
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		initDataBase(db, "create.sql");
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
