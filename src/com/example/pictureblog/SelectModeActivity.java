package com.example.pictureblog;

import com.example.pictureblog.utils.LogUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectModeActivity extends BaseActivity {

	private String TAG = "SelectModeActivity";
	
	private ViewPager bPager;
	private LinearLayout indicatorLayout;
	private int bImages[]={
			 R.layout.fragment_firstmode,  R.layout.fragment_secondmode,  R.layout.fragment_thirdmode
	};
	private TextView tv01;
	private int prePos;
	
	boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_mode);
		
		setViews();
		setBannerIndicator();
		setBannerPager();
	}

	private void setViews() {
		
		isFirst = getIntent().getBooleanExtra("isFirst", true);
		
		bPager = (ViewPager) findViewById(R.id.mode_vp01);
		indicatorLayout = (LinearLayout) findViewById(R.id.bannerIndicatorId);
		tv01 = (TextView) findViewById(R.id.tv01);
		tv01.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	private void setBannerIndicator(){
		for(int i = 0;i < bImages.length;i ++){
			View v=new View(SelectModeActivity.this);
			v.setEnabled(true);
			v.setBackgroundResource(R.drawable.banner_indicator_selector);
			LayoutParams params = new LayoutParams(20, 20);
			params.rightMargin=15;
			v.setLayoutParams(params);
			indicatorLayout.addView(v);
		}
		indicatorLayout.getChildAt(0).setEnabled(false);	
	}
	
	private void setBannerPager(){
		bPager.setAdapter(new BannerAdapter());
		bPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int postion) {
				// TODO Auto-generated method stub
				indicatorLayout.getChildAt(prePos%bImages.length).setEnabled(true);
				indicatorLayout.getChildAt(postion%bImages.length).setEnabled(false);
				prePos=postion;
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	class BannerAdapter extends PagerAdapter{
		@Override
		public int getCount() {
			return bImages.length;
		}
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
		@Override
		public Object instantiateItem(ViewGroup container, final int position) {
			View v = View.inflate(SelectModeActivity.this, bImages[position], null);
			container.addView(v);
			v.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(SelectModeActivity.this, SetModeActivity.class);
					intent.putExtra("mode", bImages[position]);
					if(isFirst){
						LogUtils.log(TAG, "isFirst");
						startActivity(intent);
					}else{
						LogUtils.log(TAG, "isNotFirst");
						setResult(RESULT_OK, intent);
						finish();
					}
				}
			});
			return v;
		}
	}
	
}
