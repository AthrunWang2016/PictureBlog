package com.example.pictureblog.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;

public class MyBitmapUtils {

private static MyBitmapUtils fileToStringUtils = new MyBitmapUtils();
	
	private MyBitmapUtils() {
		
	}

	public static MyBitmapUtils getInstance(){
		return fileToStringUtils;
	}
	
	/*public Bitmap mergeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap thridBitmap) {
		  Bitmap bitmap = Bitmap.createBitmap(secondBitmap.getWidth()+thridBitmap.getWidth(), firstBitmap.getHeight()+secondBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		  Canvas canvas = new Canvas(bitmap);
		  canvas.drawColor(Color.WHITE);
		  canvas.drawBitmap(firstBitmap, (secondBitmap.getWidth()+thridBitmap.getWidth()-firstBitmap.getWidth())/2, 0, null);
		  canvas.drawBitmap(secondBitmap, 0, firstBitmap.getHeight(), null);
		  canvas.drawBitmap(thridBitmap, secondBitmap.getWidth(), firstBitmap.getHeight(), null);
		  return bitmap;
	}*/
	
	public Bitmap getFontImage(int width, int height, String mString) {  
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);   
        Canvas canvasTemp = new Canvas(bmp);   
        canvasTemp.drawColor(Color.WHITE);   
        Paint p = new Paint();   
        p.setColor(Color.BLACK);  
        p.setTypeface(Typeface.DEFAULT);  
        p.setAntiAlias(true);//去除锯齿  
        p.setFilterBitmap(true);//对位图进行滤波处理  
        p.setTextSize((width-2)/20);  
        canvasTemp.drawText(mString, (width-(width-2)/20*mString.length())/2, (height-width/20)/2, p);
        return bmp;  
    }  
	
	public Bitmap smallBitmapSize(Bitmap bigBitmap, float sx) {
		  Matrix matrix = new Matrix(); 
		  matrix.postScale(sx, sx); //长和宽放大缩小的比例
		  Bitmap resizeBmp = Bitmap.createBitmap(bigBitmap, 0, 0, bigBitmap.getWidth(), bigBitmap.getHeight(),matrix,true);
		  return resizeBmp;
  }
	
	public String imageBitmapToString(Bitmap bitmap){
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 60,outputStream);
		String body = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
		return body;
	}
	
	public String saveBitmap(String path, Bitmap resultBitmap) {
		String picName = System.currentTimeMillis() + ".jpg";
		File f = new File(path);
		try {
			if (f.mkdirs()) {
				f.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(path + picName);
			resultBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return picName;
	}
	
}
