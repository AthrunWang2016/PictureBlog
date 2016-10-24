package com.example.pictureblog;

import java.io.File;
import java.util.List;

import com.example.pictureblog.adapter.BlogListAdapter;
import com.example.pictureblog.dailog.PopupWindowDialog;
import com.example.pictureblog.dailog.WarmingDialog;
import com.example.pictureblog.db.BlogDAO;
import com.example.pictureblog.entity.BlogEntity;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyFileUtils;
import com.example.pictureblog.widget.MyRefreshListView;
import com.example.pictureblog.widget.MyRefreshListView.IReflashListener;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	private RelativeLayout rl02;
	private TextView tv01;
	private MyRefreshListView myRefreshListView;
	
	private int page = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		setView();
		setData();
		
	}

	private void setView() {
		rl02 = (RelativeLayout) findViewById(R.id.rl02);
		tv01 = (TextView) findViewById(R.id.tv01);
		tv01.setOnClickListener(new MyOnClickListener());
	}

	private void setData() {
		View view = null;
		List<BlogEntity> blogEntities = BlogDAO.getInstance(MainActivity.this).askBlogRecord(page);
		if(blogEntities!=null&&blogEntities.size()!=0){
			view = View.inflate(MainActivity.this, R.layout.activity_main_haveblog, null);
			setHaveBlogView(view, blogEntities);
		}else{
			view = View.inflate(MainActivity.this, R.layout.activity_main_noblog, null);
			setNoBlogView(view);
		}
		rl02.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT); 
	}
	
	private void setHaveBlogView(View view, final List<BlogEntity> blogEntities) {
		myRefreshListView = (MyRefreshListView) view.findViewById(R.id.mrv01);
	    final BlogListAdapter blogListAdapter = new BlogListAdapter(MainActivity.this, blogEntities);
		myRefreshListView.setAdapter(blogListAdapter);
		myRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MainActivity.this, ShowHtmlActivity.class);
				intent.putExtra("blogEntity", (BlogEntity)parent.getItemAtPosition(position));
				startActivity(intent);
			}
		});
		myRefreshListView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				String[] data = getResources().getStringArray(R.array.popwin_array_activity_main);
				final BlogEntity blogEntity = (BlogEntity) parent.getItemAtPosition(position);
				PopupWindowDialog popupWindowDialog = new PopupWindowDialog(MainActivity.this, data, new PopupWindowDialog.MyListViewItemOnClick() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
						switch (position) {
							case 0:
								new WarmingDialog(MainActivity.this, getResources().getString(R.string.text_warmingdialog_delete), new WarmingDialog.MyButtonOnclick() {
									@Override
									public void sureOnclick() {
										boolean isDel = BlogDAO.getInstance(MainActivity.this).deleteBlogRecord(blogEntity.getDate()+"");
										new Thread(){
											public void run() {
												String path = blogEntity.getDescription();
												path = path.substring(0, path.lastIndexOf("/"));
												File file = new File(path);
												MyFileUtils.deleteFile(file);
											};
										}.start();
										if(isDel){
											blogListAdapter.removeData(blogEntity);
											MethUtils.showToast(MainActivity.this, R.string.toast_succsstodelete);
										}else{
											MethUtils.showToast(MainActivity.this, R.string.toast_failtodelete);
										}
									}
									@Override
									public void cancelOnclick() {
									}
								});
								break;
							case 1:
								Intent intent = new Intent();
								intent.setAction("android.intent.action.VIEW");
								Uri content_url = Uri.parse("file://"+blogEntity.getDescription());
								intent.setData(content_url);
								/*if(MethUtils.isInstalled(MainActivity.this, "com.uc.browser")){
									intent.setClassName("com.uc.browser", "com.uc.browser.ActivityUpdate");
									startActivity(intent);
								}*/
								if(MethUtils.isInstalled(MainActivity.this, "com.UCMobile")){
									intent.setClassName("com.UCMobile", "com.UCMobile.main.UCMobile");
									startActivity(intent);
								}
								if(MethUtils.isInstalled(MainActivity.this, "com.opera.mini.android")){
									intent.setClassName("com.opera.mini.android","com.opera.mini.android.Browser");
									startActivity(intent);
								}
								if(MethUtils.isInstalled(MainActivity.this, "com.tencent.mtt")){
									intent.setClassName("com.tencent.mtt","com.tencent.mtt.MainActivity");
									startActivity(intent);
								}
								if(MethUtils.isInstalled(MainActivity.this, "com.android.browser")){
									intent.setClassName("com.android.browser","com.android.browser.BrowserActivity");
									startActivity(intent);
								}
								
								break;
							case 2:
								break;
						}
					}
				});
				popupWindowDialog.showShareWindow();
				popupWindowDialog.showAsDropDown(view);
				/*int[] location = new int[2];
				view.getLocationOnScreen(location);
				popupWindowDialog.showAtLocation(view, Gravity.RIGHT, 0, 0);*/
				return true;
			}
		});
		myRefreshListView.setIReflashListener(new IReflashListener() {
			@Override
			public void onReflash() {
				
			}
			@Override
			public void onLoadmore() {
				page = page + 1;
				List<BlogEntity> lists = BlogDAO.getInstance(MainActivity.this).askBlogRecord(page);
				if(lists != null && lists.size() != 0){
					blogListAdapter.addData(lists);
				}
				myRefreshListView.finishLoadmore();
			}
		});
	}
	
	private void setNoBlogView(View view) {
		view.findViewById(R.id.btn01).setOnClickListener(new MyOnClickListener());
	}
	
	class MyListViewItemOnClick implements PopupWindowDialog.MyListViewItemOnClick{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.tv01:
				case R.id.btn01:
					startActivity(new Intent(MainActivity.this, SelectModeActivity.class));
					break;
			}
		}
	}
	
}
