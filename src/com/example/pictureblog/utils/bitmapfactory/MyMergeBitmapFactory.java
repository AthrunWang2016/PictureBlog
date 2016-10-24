package com.example.pictureblog.utils.bitmapfactory;

import com.example.pictureblog.mode.FirstModeBitmap;
import com.example.pictureblog.mode.SecondModeBitmap;
import com.example.pictureblog.mode.ThirdModeBitmap;

import android.graphics.Bitmap;

public abstract class MyMergeBitmapFactory {

	public abstract FirstModeBitmap firstModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap thirdBitmap);
	public abstract SecondModeBitmap secondModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap);
	public abstract ThirdModeBitmap thirdModeBitmap(Bitmap firstBitmap);
	
}
