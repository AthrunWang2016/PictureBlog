package com.example.pictureblog.utils;

import java.util.Iterator;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class MethUtils {

	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = { Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE };

	/**
	 * Checks if the app has permission to write to device storage
	 * 
	 * If the app does not has permission then the user will be prompted to
	 * grant permissions
	 * 
	 * @param activity
	 */
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		/*
		 * if (ContextCompat.checkSelfPermission(activity,
		 * Manifest.permission.READ_CONTACTS) !=
		 * PackageManager.PERMISSION_GRANTED) { }
		 */

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			LogUtils.log("MethUtils", "no permission");
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
		}else{
			LogUtils.log("MethUtils", "have permission");
		}
	}
	
	public static void setupView(int rl,Fragment mainFragment,FragmentActivity fragmentActivity) {
		FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(rl, mainFragment);
		transaction.show(mainFragment);
		//transaction.addToBackStack(null);
		transaction.commitAllowingStateLoss();
	}
	
	public static void replaceView(int rl,Fragment mainFragment,FragmentActivity fragmentActivity) {
		FragmentTransaction transaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
		transaction.replace(rl, mainFragment);
		transaction.show(mainFragment);
		//transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public static void showToast(Context context, int stringResouce){
		Toast.makeText(context, context.getResources().getString(stringResouce), 0).show();
	}
	
	/**
	 * uc浏览器"："com.uc.browser", "com.uc.browser.ActivityUpdate“
　　	 * opera："com.opera.mini.android", "com.opera.mini.android.Browser"
　　         * qq浏览器："com.tencent.mtt", "com.tencent.mtt.MainActivity"
	 */
	
	/** 
     * 检查系统中是否安装了某个应用 
     *  
     * @param context 
     * @param packageName 应用的包名 
     * @return true表示已安装，否则返回false 
     */  
	public static boolean isInstalled(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> installedList = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		Iterator<PackageInfo> iterator = installedList.iterator();

		PackageInfo info;
		String name;
		while (iterator.hasNext()) {
			info = iterator.next();
			name = info.packageName;
			if (name.equals(packageName)) {
				return true;
			}
		}
		return false;
		/*PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
            return false;
        }
        if(packageInfo ==null){
        	return false;
        }else{
        	return true;
        }*/
	}
	
	public static String getAppName(Activity activity){
		return activity.getApplicationInfo().loadLabel(activity.getPackageManager()).toString();
	}
	
	public static String getAppName(){
		return "com.example.pictureblog";
	}
	
}
