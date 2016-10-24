package com.example.pictureblog.utils.bitmapfactory;

import com.example.pictureblog.mode.FirstModeBitmap;
import com.example.pictureblog.mode.SecondModeBitmap;
import com.example.pictureblog.mode.ThirdModeBitmap;

import android.graphics.Bitmap;

public class MySimpleMergeBitmapFactory extends MyMergeBitmapFactory{

	@Override
	public FirstModeBitmap firstModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap, Bitmap thirdBitmap) {
		return new FirstModeBitmap(firstBitmap, secondBitmap, thirdBitmap);
	}

	@Override
	public SecondModeBitmap secondModeBitmap(Bitmap firstBitmap, Bitmap secondBitmap) {
		return new SecondModeBitmap(firstBitmap, secondBitmap);
	}

	@Override
	public ThirdModeBitmap thirdModeBitmap(Bitmap firstBitmap) {
		return new ThirdModeBitmap(firstBitmap);
	}

}
