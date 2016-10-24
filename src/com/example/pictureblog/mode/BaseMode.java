package com.example.pictureblog.mode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import com.example.pictureblog.utils.MyBitmapUtils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Base64;

public abstract class BaseMode implements Serializable{

	Bitmap resultBitmap;
	
	public abstract void recycleBitmap();
	protected abstract Bitmap mergeBitmap();
	
	public Bitmap getBitmap(){
		resultBitmap = mergeBitmap();
		return resultBitmap;
	}
	
	protected Bitmap smallBitmapSize(Bitmap bigBitmap, float sx) {
		  /*Matrix matrix = new Matrix(); 
		  matrix.postScale(sx, sx); //长和宽放大缩小的比例
		  Bitmap resizeBmp = Bitmap.createBitmap(bigBitmap, 0, 0, bigBitmap.getWidth(), bigBitmap.getHeight(),matrix,true);
		  return resizeBmp;*/
		return MyBitmapUtils.getInstance().smallBitmapSize(bigBitmap, sx);
	}
	
	public String imageBitmapToString(){
		/*resultBitmap = mergeBitmap();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		resultBitmap.compress(CompressFormat.JPEG, 60,outputStream);
		String body = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
		return body;*/
		return MyBitmapUtils.getInstance().imageBitmapToString(mergeBitmap());
	}
	
	public String saveBitmap(String path) {
		/*resultBitmap = mergeBitmap();
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
		return picName;*/
		return MyBitmapUtils.getInstance().saveBitmap(path, mergeBitmap());
	}
	
}
