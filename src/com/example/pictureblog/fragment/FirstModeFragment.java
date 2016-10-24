package com.example.pictureblog.fragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.pictureblog.MainActivity;
import com.example.pictureblog.R;
import com.example.pictureblog.SelectModeActivity;
import com.example.pictureblog.SetModeActivity;
import com.example.pictureblog.adapter.SetModePopAdapter;
import com.example.pictureblog.dailog.ModeTextDialog;
import com.example.pictureblog.mode.FirstModeBitmap;
import com.example.pictureblog.utils.LogUtils;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyBitmapUtils;
import com.example.pictureblog.utils.MyHtmlUtils;
import com.example.pictureblog.utils.bitmapfactory.MySimpleMergeBitmapFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class FirstModeFragment extends BaseModeFragment {

	private ImageView iv01,iv02,iv03;
	
	private Bitmap bt01,bt02,bt03;
	
	private FirstModeBitmap firstModeBitmap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_firstmode,null);
		setViews();
		setLiseners();
		return view;
	}
	
	@Override
	void setViews() {
		iv01 = (ImageView) view.findViewById(R.id.iv01);
		iv02 = (ImageView) view.findViewById(R.id.iv02);
		iv03 = (ImageView) view.findViewById(R.id.iv03);
	}

	@Override
	void setLiseners() {
		iv01.setOnClickListener(new MyOnClickListener());
		iv02.setOnClickListener(new MyOnClickListener());
		iv03.setOnClickListener(new MyOnClickListener());
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case 1000:
				bt01 = onCameraResult(resultCode, data, iv01);
				break;
			case 1001:
				LogUtils.log(TAG, "iv01");
				bt01 = onPicResult(resultCode, data, iv01);
				break;
			case 2000:
				LogUtils.log(TAG, "iv02");
				bt02 = onCameraResult(resultCode, data, iv02);
				break;
			case 2001:
				LogUtils.log(TAG, "iv02");
				bt02 = onPicResult(resultCode, data, iv02);
				break;
			case 3000:
				LogUtils.log(TAG, "iv03");
				bt03 = onCameraResult(resultCode, data, iv03);
			case 3001:
				LogUtils.log(TAG, "iv03");
				bt03 = onPicResult(resultCode, data, iv03);
				break;
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void createHtml(){
		// createHtml(bt01, bt02, bt03);
		firstModeBitmap = new MySimpleMergeBitmapFactory().firstModeBitmap(bt01, bt02, bt03);
		// iv03.setImageBitmap(firstModeBitmap.getBitmap());
		htmlString = firstModeBitmap.saveBitmap(path);
	}
	
	@Override
	public void onDestroy() {
		if(firstModeBitmap != null){
			firstModeBitmap.recycleBitmap();
		}
		super.onDestroy();
	}
	
	/*public void createHtml(Bitmap bt01, Bitmap bt02, Bitmap bt03){
		
	}*/
	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.iv01:
					showPopupWindow(10);
					break;
				case R.id.iv02:
					showPopupWindow(20);
					break;
				case R.id.iv03:
					showPopupWindow(30);
					break;
			}
		}
	} 

	@Override
	void setFontBitmap(final int requestCode) {
		new ModeTextDialog(getActivity(), getActivity().getResources().getString(R.string.text_textmodedialog_tip), new ModeTextDialog.MyButtonOnclick() {
			@Override
			public void sureOnclick(String etStr) {
				if(TextUtils.isEmpty(etStr)){
					MethUtils.showToast(getActivity(), R.string.toast_content_nottobeempty);
					return;
				}
				if(etStr.length()>20){
					MethUtils.showToast(getActivity(), R.string.toast_content_mustbe20);
					return;
				}
				switch (requestCode) {
					case 10:
						bt01 = MyBitmapUtils.getInstance().getFontImage(640, 480, etStr);
						iv01.setImageBitmap(bt01);
						break;
					case 20:
						bt02 = MyBitmapUtils.getInstance().getFontImage(480, 640, etStr);
						iv02.setImageBitmap(bt02);
						break;
					case 30:
						bt03 = MyBitmapUtils.getInstance().getFontImage(480, 640, etStr);
						iv03.setImageBitmap(bt03);
						break;
				}
			}
			@Override
			public void cancelOnclick(String etStr) {
			}
		});
	}
	
}
