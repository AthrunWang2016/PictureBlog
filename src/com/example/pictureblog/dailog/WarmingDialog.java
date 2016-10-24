package com.example.pictureblog.dailog;


import com.example.pictureblog.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
/**
 * 提示框（有按钮）
 *
 */
public class WarmingDialog  {
	
	private Context context;
	private String message;
	
	private AlertDialog ad;
    
	private TextView messageView;
	private Button bt01;
	private Button bt02;
	private Window window;
    
	private MyButtonOnclick myButtonOnclick;
    
    public WarmingDialog(Context context,String message,MyButtonOnclick myButtonOnclick) {
        this.context = context;
        this.message = message;
        this.myButtonOnclick = myButtonOnclick;
        
        ad = new AlertDialog.Builder(context).create();
        ad.show();
        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        window = ad.getWindow();
        window.setContentView(R.layout.dialog_warming);
        
        LayoutParams params = new LayoutParams();
		params.width = LayoutParams.WRAP_CONTENT;//宽度
		params.height = LayoutParams.WRAP_CONTENT;//高度
		//params.alpha = 0.5f;//透明度
		//window.setAttributes(params);
		ad.setCanceledOnTouchOutside(true);
		setViews();
        
    }
    
    private void setViews(){
    	messageView = (TextView)window.findViewById(R.id.tv2);
    	bt01 = (Button)window.findViewById(R.id.bt01);
    	bt02 = (Button)window.findViewById(R.id.bt02);
    	
    	OnClickListener onClickListener = new MyOnClickListener();
    	bt01.setOnClickListener(onClickListener);
    	bt02.setOnClickListener(onClickListener);
        messageView.setText(message);
    }
    
    class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.bt01:
					ad.dismiss();
					if(myButtonOnclick == null){
						break;
					}
					myButtonOnclick.cancelOnclick();
					break;
				case R.id.bt02:
					ad.dismiss();
					if(myButtonOnclick == null){
						break;
					}
					myButtonOnclick.sureOnclick();
					break;
			}
		}
    }
    
    public interface MyButtonOnclick {
    	public void sureOnclick();
    	public void cancelOnclick();
    }
    
}
