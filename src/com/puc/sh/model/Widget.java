package com.puc.sh.model;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Widget {
	public int X;
	public int Y;
	public Bitmap mBitmap;
	private OnTouchListener mListener;

	public Widget(Bitmap bitmap, int x, int y) {
		mBitmap = bitmap;
		X = x;
		Y = y;
	}

	public void setListener(OnTouchListener listener) {
		mListener = listener;
	}

	public void hitTest(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		if (x > X && x < X + mBitmap.getWidth() && y > Y && y < Y + mBitmap.getHeight()) {
			if (mListener != null) {
				mListener.onTouchEvent(event);
			}
		}
	}

	public interface OnTouchListener {
		public void onTouchEvent(MotionEvent event);
	}

}