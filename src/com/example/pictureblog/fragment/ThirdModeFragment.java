package com.example.pictureblog.fragment;

import com.example.pictureblog.MainActivity;
import com.example.pictureblog.R;
import com.example.pictureblog.SelectModeActivity;
import com.example.pictureblog.dailog.ModeTextDialog;
import com.example.pictureblog.fragment.FirstModeFragment.MyOnClickListener;
import com.example.pictureblog.mode.ThirdModeBitmap;
import com.example.pictureblog.utils.LogUtils;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyBitmapUtils;
import com.example.pictureblog.utils.MyHtmlUtils;
import com.example.pictureblog.utils.bitmapfactory.MySimpleMergeBitmapFactory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ThirdModeFragment extends BaseModeFragment {

	private ImageView iv01;
	
	private Bitmap bt01;
	
	private ThirdModeBitmap thirdModeBitmap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_thirdmode,null);
		setViews();
		setLiseners();
		return view;
	}
	
	@Override
	void setViews() {
		iv01 = (ImageView) view.findViewById(R.id.iv01);
	}

	@Override
	void setLiseners() {
		iv01.setOnClickListener(new MyOnClickListener());
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
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void createHtml(){
		thirdModeBitmap = new ThirdModeBitmap(bt01);
		htmlString = thirdModeBitmap.saveBitmap(path);
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
						bt01 = MyBitmapUtils.getInstance().getFontImage(480, 640, etStr);
						iv01.setImageBitmap(bt01);
						break;
				}
			}
			@Override
			public void cancelOnclick(String etStr) {
			}
		});
	}

	
	class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.iv01:
					showPopupWindow(10);
					break;
			}
		}
	}
	
	
	
}
