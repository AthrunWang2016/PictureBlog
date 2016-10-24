package com.example.pictureblog.mode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class SecondModeBitmap extends BaseMode  {

	Bitmap firstBitmap, secondBitmap;
	
	public SecondModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
		super();
		int width = 480;
		int height = 640;
		if(firstBitmap == null){
			firstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		if(secondBitmap == null){
			secondBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		this.firstBitmap = firstBitmap;
		this.secondBitmap = secondBitmap;
	}
	
	@Override
	protected Bitmap mergeBitmap() {
	
		  if(secondBitmap.getWidth() - firstBitmap.getWidth() > 0){
			  secondBitmap = smallBitmapSize(secondBitmap, firstBitmap.getWidth()*1.0f/secondBitmap.getWidth());
		  }else{
			  firstBitmap = smallBitmapSize(firstBitmap, secondBitmap.getWidth()*1.0f/firstBitmap.getWidth());
		  }
		  
		  Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth()+2, firstBitmap.getHeight()+secondBitmap.getHeight()+4, Bitmap.Config.ARGB_8888);
		  Canvas canvas = new Canvas(bitmap);
		  
		  canvas.drawColor(Color.WHITE);
		  canvas.drawBitmap(firstBitmap, firstBitmap.getWidth()>=secondBitmap.getWidth()?1:(secondBitmap.getWidth() - firstBitmap.getWidth())/2+1, 1, null);
		  canvas.drawBitmap(secondBitmap, secondBitmap.getWidth()>=firstBitmap.getWidth()?1:(secondBitmap.getWidth() - firstBitmap.getWidth())/2+1, firstBitmap.getHeight()+3, null);
		  return bitmap;
	}
	
	@Override
	public void recycleBitmap() {
		firstBitmap.recycle();
		secondBitmap.recycle();
	}

}
