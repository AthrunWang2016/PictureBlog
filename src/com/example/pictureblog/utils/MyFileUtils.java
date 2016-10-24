package com.example.pictureblog.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyFileUtils {

	public static boolean deleteFile(File file){
		if (file.exists()) { //指定文件是否存在  
            if (file.isFile()) { //该路径名表示的文件是否是一个标准文件  
                file.delete(); //删除该文件  
            } else if (file.isDirectory()) { //该路径名表示的文件是否是一个目录（文件夹）  
                File[] files = file.listFiles(); //列出当前文件夹下的所有文件  
                for (File f : files) {  
                	deleteFile(f); //递归删除  
                }  
            }  
            file.delete(); //删除文件夹（song,art,lyric）  
        }
		return true;   
	}
	
	public static String readFile(String path) throws IOException{
    	File file = new File(path);
    	if(!file.exists()){
    		return "";
    	}
	    FileInputStream inputStream = new FileInputStream(file);
	    byte[] b = new byte[inputStream.available()];
	    LogUtils.log("MyFileUtils", "readFile:"+b.toString());
	    inputStream.read(b);
    	return new String(b);
    }
	
}
