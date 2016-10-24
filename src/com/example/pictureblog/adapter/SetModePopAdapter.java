package com.example.pictureblog.adapter;

import java.util.List;

import com.example.pictureblog.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SetModePopAdapter extends MyBaseAdapter{
	
	private List<String> lists;
	private int resouce;
	
	public SetModePopAdapter(Context context, List<String> lists, int resouce) {
		super(context);
		this.lists = lists;
		this.resouce = resouce;
	}

	@Override
	public int getCount() {
		if(lists == null){
			return 0;
		}
		return lists.size();
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void addData(String BlogEntities){
		lists.add(BlogEntities);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, resouce, null);
			viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.tv_date.setText(lists.get(position));
		
		return convertView;
	}

	class ViewHolder{
		TextView tv_date;
	}
	
}
