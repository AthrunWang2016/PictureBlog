package com.example.pictureblog.db;


import com.example.pictureblog.utils.ConUtils;
import com.example.pictureblog.utils.LogUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 保存日志记录
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	private static DBHelper instance;
	
	//private String tableName = "myBlogRecordTable";
	//private static String dbName = "MyPictureBlog.db";
	
	
	private String myBlogRecordTable = "create table if not exists " + ConUtils.tableName + " ("
			+ "_id integer primary key autoincrement,"
			+ "description text,"
			+ "pictures integer,"
			+ "_date long );";
	
	public static DBHelper getInstance(Context context){
		if(instance == null){
			instance = new DBHelper(context);
		}
		return instance;
	}
	
	private DBHelper(Context context){
		super(context, "MyPictureBlog.db", null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(myBlogRecordTable);
		LogUtils.log("SQLiteDatabase", "SQLiteDatabase");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
