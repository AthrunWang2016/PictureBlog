package com.example.pictureblog;

import java.io.Serializable;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		TApplication.lists.add(this);
	}
	
	public void setData(Serializable serializable){
		
	}
	
}
