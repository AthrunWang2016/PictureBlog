package com.example.pictureblog;

import java.io.File;
import java.io.IOException;

import com.example.pictureblog.db.BlogDAO;
import com.example.pictureblog.entity.BlogEntity;
import com.example.pictureblog.fragment.BaseModeFragment;
import com.example.pictureblog.fragment.FirstModeFragment;
import com.example.pictureblog.fragment.SecondModeFragment;
import com.example.pictureblog.fragment.ThirdModeFragment;
import com.example.pictureblog.utils.LogUtils;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyFileUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SetModeActivity extends BaseActivity {

	private String TAG = "SetModeActivity";
	
	private TextView tv01;
	private Button btn01;
	private Button btn02;
	
	private BaseModeFragment mainFragment = null;
	
	private int picNum = 1;
	private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + MethUtils.getAppName() + "/" + System.currentTimeMillis() + "/";
	private String htmlName = System.currentTimeMillis()+".html";
	
	private boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_mode);
		
		setViews();
		int i = getIntent().getIntExtra("mode", 0);
		setDatas(i);
	}

	private void setViews() {
		btn01 = (Button) findViewById(R.id.btn01);
		btn02 = (Button) findViewById(R.id.btn02);
		tv01 = (TextView) findViewById(R.id.tv01);
		
		btn01.setOnClickListener(new MyOnClickListener());
		btn02.setOnClickListener(new MyOnClickListener());
		tv01.setOnClickListener(new MyOnClickListener());
	}
	
	private void setDatas(int i) {
		switch (i) {
			case R.layout.fragment_firstmode:
				mainFragment = new FirstModeFragment();
				break;
			case R.layout.fragment_secondmode:
				mainFragment = new SecondModeFragment();
				break;
			case R.layout.fragment_thirdmode:
				mainFragment = new ThirdModeFragment();
				break;
			default:
				break;
		}
		if(isFirst){
			setupView(mainFragment);
		}else{
			MethUtils.setupView(R.id.fragment_container, mainFragment, SetModeActivity.this);
		}
		isFirst = false;
		mainFragment.setSavepath(path);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 9000:
				if (resultCode == RESULT_OK) {
					LogUtils.log(TAG, "isNotFirst");
					int i = data.getIntExtra("mode", 0);
					setDatas(i);
				}else{
					//have to make sure
					new Thread(){
						public void run() {
							path = path.substring(0, path.lastIndexOf("/"));
							File file = new File(path);
							MyFileUtils.deleteFile(file);
						};
					}.start();
				}
				/*int i = getIntent().getIntExtra("mode", 0);
				switch (i) {
					case R.layout.fragment_firstmode:
						mainFragment = new FirstModeFragment();
						break;
					case R.layout.fragment_secondmode:
						mainFragment = new SecondModeFragment();
						break;
					case R.layout.fragment_thirdmode:
						mainFragment = new ThirdModeFragment();
						break;
					default:
						break;
				}
				MethUtils.setupView(R.id.fragment_container, mainFragment, SetModeActivity.this);*/
				break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void setupView(BaseModeFragment mainFragment) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.add(R.id.fragment_container, mainFragment);
		transaction.show(mainFragment);
		transaction.commit();
	}
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.btn01:
					//继续选择模板
					//MethUtils.verifyStoragePermissions(SetModeActivity.this);
					picNum += 1;
					//mainFragment.createHtml();
					try {
						mainFragment.saveHtml(htmlName);
						Intent intent = new Intent(SetModeActivity.this, SelectModeActivity.class);
						intent.putExtra("isFirst", false);
						startActivityForResult(intent, 9000);
					} catch (IOException e) {
						MethUtils.showToast(SetModeActivity.this, R.string.toast_failtodo);
						e.printStackTrace();
					}
					break;
				case R.id.btn02:
					//生成日志
					//MethUtils.verifyStoragePermissions(SetModeActivity.this);
					try {
						mainFragment.saveHtml(htmlName);
						BlogEntity blogEntity = new BlogEntity(System.currentTimeMillis(), picNum, path + htmlName);
						BlogDAO.getInstance(SetModeActivity.this).saveBlogRecord(blogEntity);
						Intent intent = new Intent(SetModeActivity.this, ShowHtmlActivity.class);
						intent.putExtra("blogEntity", blogEntity);
						startActivity(intent);
					} catch (IOException e) {
						MethUtils.showToast(SetModeActivity.this, R.string.toast_failtodo);
						e.printStackTrace();
					}
					break;
				case R.id.tv01:
					finish();
					break;
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		LogUtils.log(TAG, "onDestroy");
		super.onDestroy();
	}
	
}
