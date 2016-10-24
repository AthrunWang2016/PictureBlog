package com.example.pictureblog.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.pictureblog.R;
import com.example.pictureblog.entity.BlogEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BlogListAdapter extends MyBaseAdapter{
	
	private List<BlogEntity> lists;
	
	public BlogListAdapter(Context context, List<BlogEntity> lists) {
		super(context);
		this.lists = lists;
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

	public void addData(List<BlogEntity> BlogEntities){
		lists.addAll(BlogEntities);
		notifyDataSetChanged();
	}
	
	public void removeData(BlogEntity BlogEntity){
		lists.remove(BlogEntity);
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.item_list_blog, null);
			viewHolder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			viewHolder.tv_pic = (TextView) convertView.findViewById(R.id.tv_pic);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date(lists.get(position).getDate()));
		viewHolder.tv_date.setText(date);
		viewHolder.tv_pic.setText(lists.get(position).getPictures()+"");
		
		return convertView;
	}

	class ViewHolder{
		TextView tv_date;
		TextView tv_pic;
	}
	
}
