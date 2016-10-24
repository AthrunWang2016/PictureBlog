package com.example.pictureblog;

import java.util.ArrayList;
import java.util.List;

import com.example.pictureblog.utils.LogUtils;

import android.app.Activity;
import android.app.Application;

public class TApplication extends Application{

	public static List<Activity> lists = new ArrayList<Activity>();
	
	@Override
	public void onCreate() {
		super.onCreate();
		LogUtils.isLog = true;
	}
	
	/*public void addActivityList(Activity activity){
		lists.add(activity);
	}*/
	
	public void exitApp(){
		for(Activity a:lists){
			if(a != null){
				a.finish();
			}
		}
	}
	
}
