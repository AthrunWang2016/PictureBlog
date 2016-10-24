package com.example.pictureblog.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.example.pictureblog.MainActivity;
import com.example.pictureblog.R;
import com.example.pictureblog.adapter.SetModePopAdapter;
import com.example.pictureblog.dailog.ModeTextDialog;
import com.example.pictureblog.utils.LogUtils;
import com.example.pictureblog.utils.MethUtils;
import com.example.pictureblog.utils.MyBitmapUtils;
import com.example.pictureblog.utils.MyHtmlUtils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public abstract class BaseModeFragment extends Fragment {

	View view;
	
	String picName;
	
	String TAG = "BaseModeFragment";
	
	String htmlString;
	
	String path;
	//private Bitmap bitmap;
	abstract void setViews();
	abstract void setLiseners();
	abstract void setFontBitmap(int requestCode);
	
	//public abstract void saveHtml(String path);
	public abstract void createHtml();
	
	public void setSavepath(String path){
		this.path = path;
	}
	
	public void saveHtml(String saveName) throws IOException{
		createHtml();
		if(htmlString == null || htmlString.equals("")){
			return;
		}
		MyHtmlUtils.saveHtml(path, saveName, htmlString);
	}
	
	void showPopupWindow(final int requestCode) {
        // 一个自定义的布局，作为显示的内容
        View contentView = View.inflate(getActivity(),R.layout.popwindow_modefragment, null);
        
        final PopupWindow popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        
        // 设置按钮的点击事件
        ListView listView = (ListView) contentView.findViewById(R.id.lv01);
        List<String> listData = new ArrayList<String>();
        String data[] = getActivity().getResources().getStringArray(R.array.popwin_array_basemodefragment);
        /*listData.add("拍照");
        listData.add("图库");
        listData.add("取消");*/
        for(int i=0;i<data.length;i++){
        	listData.add(data[i]);
    	}
        SetModePopAdapter arrayAdapter = new SetModePopAdapter(getActivity(), listData, R.layout.item_popupwindow_setmode);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position) {
					case 0:
						picName = path + "temp.jpg";
						LogUtils.log(TAG, picName);
						//从拍照获取图片
						//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						//若设置这句，则data返回Null，否则直接保存图片
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(picName)));
						getActivity().startActivityForResult(intent, requestCode*100);
						popupWindow.dismiss();
						break;
					case 1:
						//打开图库
						Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, requestCode*100+1);
						popupWindow.dismiss();
						break;
					case 2:
						setFontBitmap(requestCode);
						popupWindow.dismiss();
						/*new ModeTextDialog(getActivity(), getActivity().getResources().getString(R.string.text_textmodedialog_tip), new ModeTextDialog.MyButtonOnclick() {
							@Override
							public void sureOnclick(String etStr) {
								bitmap = MyBitmapUtils.getInstance().getFontImage(etStr);
							}
							@Override
							public void cancelOnclick(String etStr) {
							}
						});*/
						break;
					case 3:
						popupWindow.dismiss();
						break;
				}
			}
		});
        
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setContentView(contentView);
        // 设置好参数之后再show
        popupWindow.showAtLocation(getActivity().findViewById(R.id.ll02), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }
	
	Bitmap onCameraResult(int resultCode, Intent data,ImageView iv){
		if (resultCode == getActivity().RESULT_OK) {
			// 获取相机返回的数据，并转换为Bitmap图片格式
			//bitmap = data.getParcelableExtra("data");
			Bitmap bitmap = BitmapFactory.decodeFile(picName);
			if(bitmap == null){
				MethUtils.showToast(getActivity(), R.string.toast_filenotfound);
			}else{
				iv.setImageBitmap(bitmap);
			}
			return bitmap;
		}else{
			return null;
		}
	}
	
	Bitmap onPicResult(int resultCode, Intent data,ImageView iv){
		if ( resultCode == getActivity().RESULT_OK && null != data) {
			Uri selectedImage = data.getData();
			Bitmap bitmap = null;
			try {
				bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
				iv.setImageBitmap(bitmap);
			} catch (FileNotFoundException e) {
				MethUtils.showToast(getActivity(), R.string.toast_filenotfound);
				e.printStackTrace();
			} catch (IOException e) {
				MethUtils.showToast(getActivity(), R.string.toast_failtodo);
				e.printStackTrace();
			}
			return bitmap;
		}else{
			return null;
		}
	}
	
}
