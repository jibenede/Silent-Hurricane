package com.puc.sh.model;

import android.graphics.Bitmap;
import android.view.MotionEvent;

public class Widget {
	public int X;
	public int Y;
	public Bitmap mBitmap;
	private OnTouchListener mListener;

	private int mWidth;
	private int mHeight;

	public Widget(Bitmap bitmap, int x, int y) {
		mBitmap = bitmap;
		X = x;
		Y = y;

		mWidth = bitmap.getWidth();
		mHeight = bitmap.getHeight();
	}

	public Widget(Bitmap bitmap, int x, int y, int width, int height) {
		this(bitmap, x, y);
		mWidth = width;
		mHeight = height;
	}

	public void setListener(OnTouchListener listener) {
		mListener = listener;
	}

	public void hitTest(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();

		if (x > X && x < X + mWidth && y > Y && y < Y + mHeight) {
			if (mListener != null) {
				mListener.onTouchEvent(event);
			}
		}

	}

	public interface OnTouchListener {
		public void onTouchEvent(MotionEvent event);
	}

}
