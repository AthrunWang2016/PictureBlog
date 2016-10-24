package com.example.pictureblog.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyHtmlUtils {

	private static final String mHtmlHead = "<!DOCTYPE html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>我的日志</title></head><body style='width:100%'>" ; 
    private static final String mHtmlImgHead = "<div style='align:center'><img width='100%' align='center' src='"; 
    private static final String mHtmlImgEnd = "'/></div>"; 
    private static final String mHtmlEnd = "</body></html>";
	
    private static String createHtml(String cotent){
    	cotent = mHtmlImgHead + cotent + mHtmlImgEnd;
    	String result = mHtmlHead + mHtmlEnd;
    	String mid = new String(result);  
        mid = mid.replace(mHtmlEnd, cotent);
        result = mid + mHtmlEnd;
    	return result;
	}
    
    public static String readHtml(String path) throws IOException{
    	/*File file = new File(path);
    	if(!file.exists()){
    		return null;
    	}
	    FileInputStream inputStream = new FileInputStream(file);
	    byte[] b = new byte[inputStream.available()];
	    inputStream.read(b);
    	return new String(b);*/
    	return MyFileUtils.readFile(path);
    }
    
    public static void saveHtml(String path, String name, String result) throws IOException{
    	File file = new File(path);
    	String cotent = "";
    		LogUtils.log("MyHtmlUtils", "saveHtml:readHtml");
    		cotent = readHtml(path + name);
    	if(cotent==null||cotent.equals("")){
    		//File file = new File(path);
            FileOutputStream fos = new FileOutputStream(path + name);
            result = createHtml(result);
            // 把长宽写入头部  
            fos.write(result.getBytes());  
            fos.flush();  
            fos.close();  
    	}else{
    		String mid = new String(cotent);  
            mid = mid.replace(mHtmlEnd, mHtmlImgHead + result);
            cotent = mid + mHtmlImgEnd + mHtmlEnd;
            //File file = new File(path);
            FileOutputStream fos = new FileOutputStream(path + name);
            // 把长宽写入头部  
            fos.write(cotent.getBytes());  
            fos.flush();  
            fos.close();  
    	}
		
	}
	
}
