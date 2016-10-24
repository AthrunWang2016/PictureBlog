package com.example.pictureblog.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter extends BaseAdapter{

	protected Context context;
	
	public MyBaseAdapter(Context context) {
		super();
		this.context = context;
	}

}
