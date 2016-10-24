package com.example.pictureblog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.pictureblog.dailog.WarmingDialog;
import com.example.pictureblog.db.BlogDAO;
import com.example.pictureblog.entity.BlogEntity;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyFileUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class ShowHtmlActivity extends Activity {

	private TextView tv01;
	private TextView tv02;
	private TextView tv03;
	private WebView webView1;
	
	private BlogEntity blogEntity;
	
	//private String webHead = "content://com.android.htmlfileprovider";
	private String webHead = "file://";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_html);
		
		setViews();
		setDatas();
		
	}

	private void setViews() {
		tv01 = (TextView) findViewById(R.id.tv01);
		tv02 = (TextView) findViewById(R.id.tv02);
		tv03 = (TextView) findViewById(R.id.tv03);
		webView1 = (WebView) findViewById(R.id.webView1);
		
		tv01.setOnClickListener(new MyOnClickListener());
		tv03.setOnClickListener(new MyOnClickListener());
	}

	private void setDatas() {
		blogEntity = (BlogEntity) getIntent().getSerializableExtra("blogEntity");
		if(blogEntity!=null){
			String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(blogEntity.getDate());
			tv02.setText(date);
		}
		webView1.getSettings().setJavaScriptEnabled(true);
		webView1.loadUrl(webHead + blogEntity.getDescription());
		webView1.setWebViewClient(new WebViewClient());
	}
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			final Intent intent = new Intent(ShowHtmlActivity.this, MainActivity.class);
			switch (v.getId()) {
				case R.id.tv01:
					finish();
					startActivity(intent);
					break;
				case R.id.tv03:
					//提示是否删除
					new WarmingDialog(ShowHtmlActivity.this, getResources().getString(R.string.text_warmingdialog_delete), new WarmingDialog.MyButtonOnclick() {
						@Override
						public void sureOnclick() {
							boolean isDel = BlogDAO.getInstance(ShowHtmlActivity.this).deleteBlogRecord(blogEntity.getDate()+"");
							new Thread(){
								public void run() {
									String path = blogEntity.getDescription();
									path = path.substring(0, path.lastIndexOf("/"));
									File file = new File(path);
									MyFileUtils.deleteFile(file);
								};
							}.start();
							if(isDel){
								MethUtils.showToast(ShowHtmlActivity.this, R.string.toast_succsstodelete);
							}else{
								MethUtils.showToast(ShowHtmlActivity.this, R.string.toast_failtodelete);
							}
							finish();
							startActivity(intent);
						}
						@Override
						public void cancelOnclick() {
						}
					});
					break;
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		startActivity(new Intent(ShowHtmlActivity.this, MainActivity.class));
		super.onBackPressed();
	}
	
	@Override
	public void onDestroy() {
		webView1.clearCache(true);
		super.onDestroy();
	}

}
