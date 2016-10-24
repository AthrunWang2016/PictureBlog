package com.example.pictureblog.db;



import java.util.ArrayList;
import java.util.List;

import com.example.pictureblog.entity.BlogEntity;
import com.example.pictureblog.utils.ConUtils;
import com.example.pictureblog.utils.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class BlogDAO extends MyBaseDAO {
	
	private static BlogDAO instance;

	private BlogDAO(Context context) {
		super(context);
	}

	public static BlogDAO getInstance(Context context){
		if(instance == null){
			instance = new BlogDAO(context);
		}
		return instance;
	}

	public List<BlogEntity> askBlogRecord(int page) {
		SQLiteDatabase db = dBHelper.getReadableDatabase();
		int size = 20;
		String sql = "select * from " + ConUtils.tableName + " order by _id DESC limit " + size  + " offset " + (size * page);
		Cursor c = db.rawQuery(sql, null);
		List<BlogEntity> lists = new ArrayList<BlogEntity>();
		while(c.moveToNext()){
			BlogEntity ce = new BlogEntity();
			LogUtils.log("askChatRoster", c.getString(1));
			ce.setDescription(c.getString(1));
			ce.setPictures(c.getInt(2));
			ce.setDate(c.getLong(3));
			lists.add(ce);
		}
		c.close();
		db.close();
		return lists;
	}
	
	public boolean saveBlogRecord(BlogEntity blogEntity) {
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		ContentValues values =new ContentValues();
		values.put("description", blogEntity.getDescription());
		values.put("_date", blogEntity.getDate());
		values.put("pictures", blogEntity.getPictures());
		long i = db.insert(ConUtils.tableName, null, values);
		db.close();
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean deleteBlogRecord(String date){
		SQLiteDatabase db = dBHelper.getWritableDatabase();
		int i = db.delete(ConUtils.tableName,"_date = ?",new String[]{date});
		db.close();
		if(i > 0){
			return true;
		}else{
			return false;
		}
	}
	
}
