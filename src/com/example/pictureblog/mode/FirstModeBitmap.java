package com.example.pictureblog.mode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class FirstModeBitmap extends BaseMode  {

	Bitmap firstBitmap, secondBitmap, thirdBitmap;
	
	public FirstModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap thirdBitmap) {
		super();
		int width = 480;
		int height = 640;
		if(firstBitmap == null){
			firstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		if(secondBitmap == null){
			secondBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		if(thirdBitmap == null){
			thirdBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		this.firstBitmap = firstBitmap;
		this.secondBitmap = secondBitmap;
		this.thirdBitmap = thirdBitmap;
	}

	@Override
	protected Bitmap mergeBitmap() {
	
		if (secondBitmap.getHeight() - thirdBitmap.getHeight() > 0) {
			secondBitmap = smallBitmapSize(secondBitmap, thirdBitmap.getHeight() * 1.0f / secondBitmap.getHeight());
		} else {
			thirdBitmap = smallBitmapSize(thirdBitmap, secondBitmap.getHeight() * 1.0f / thirdBitmap.getHeight());
		}

		if (secondBitmap.getWidth() - thirdBitmap.getWidth() > 0) {
			secondBitmap = smallBitmapSize(secondBitmap, thirdBitmap.getWidth() * 1.0f / secondBitmap.getWidth());
		} else {
			thirdBitmap = smallBitmapSize(thirdBitmap, secondBitmap.getWidth() * 1.0f / thirdBitmap.getWidth());
		}

		int stWidth = secondBitmap.getWidth() + thirdBitmap.getWidth();

		if (firstBitmap.getWidth() > stWidth) {
			firstBitmap = smallBitmapSize(firstBitmap, (stWidth) * 1.0f / firstBitmap.getWidth());
		}

		Bitmap bitmap = Bitmap.createBitmap(stWidth + 4, firstBitmap.getHeight() + secondBitmap.getHeight() + 4,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(firstBitmap, (stWidth - firstBitmap.getWidth()) / 2, 1, null);
		canvas.drawBitmap(secondBitmap, 1, firstBitmap.getHeight() + 3, null);
		canvas.drawBitmap(thirdBitmap, secondBitmap.getWidth() + 3, firstBitmap.getHeight() + 3, null);
		return bitmap;
	}
	
	@Override
	public void recycleBitmap() {
		firstBitmap.recycle();
		secondBitmap.recycle();
		thirdBitmap.recycle();
	}

}
