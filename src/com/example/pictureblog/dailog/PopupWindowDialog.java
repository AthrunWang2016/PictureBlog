package com.example.pictureblog.dailog;

import java.util.ArrayList;
import java.util.List;

import com.example.pictureblog.R;
import com.example.pictureblog.adapter.SetModePopAdapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

public class PopupWindowDialog extends PopupWindow{

	private Context context;
	private String data[];
	List<String> dataList;
	private MyListViewItemOnClick myListViewItemOnClick;
	
	public PopupWindowDialog(Context context, String[] data, MyListViewItemOnClick myListViewItemOnClick) {
		super();
		this.context = context;
		this.data = data;
		this.myListViewItemOnClick = myListViewItemOnClick;
	}

	public void showShareWindow() {
        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_menu_warp,null);

        setViews(view);
        
        // 设置SelectPicPopupWindow的View
        setContentView(view);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        //this.setBackgroundDrawable(dw);

    }

	private void setViews(View view){
		setDatas();
		ListView lv01 = (ListView) view.findViewById(R.id.lv01);
		lv01.setAdapter(new SetModePopAdapter(context, dataList, R.layout.item_popupwindow_setmode));
		lv01.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(myListViewItemOnClick == null){
					return;
				}
				myListViewItemOnClick.onItemClick(parent, view, position, id);
				dismiss();
			}
		});
	}
	
    private void setDatas(){
    	dataList = new ArrayList<String>();
    	for(int i=0;i<data.length;i++){
    		dataList.add(data[i]);
    	}
    }
    
	public interface MyListViewItemOnClick{
		public void onItemClick(AdapterView<?> parent, View view, int position, long id);
	}
	
}
