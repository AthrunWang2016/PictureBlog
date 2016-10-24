package com.example.pictureblog.mode;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.ImageView;

public class ThirdModeBitmap extends BaseMode  {

	private ImageView iv01;
	
	private Bitmap firstBitmap;
	
	public ThirdModeBitmap(Bitmap firstBitmap) {
		int width = 480;
		int height = 640;
		if(firstBitmap == null){
			firstBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		}
		this.firstBitmap = firstBitmap;
	}
	
	@Override
	protected Bitmap mergeBitmap() {
		Bitmap bitmap = Bitmap.createBitmap(firstBitmap.getWidth()+2, firstBitmap.getHeight()+2, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
	    canvas.drawColor(Color.WHITE);
	    canvas.drawBitmap(firstBitmap, 1, 1, null);
	    return bitmap;
	}
	
	@Override
	public void recycleBitmap() {
		firstBitmap.recycle();
	}
	
}
