package com.puc.soa;

import android.graphics.Bitmap;

public class Globals {
	public static final int MAX_SPEED = 1000;

	public static final int BULLET_LIMIT = 3000;
	public static final int ENEMY_LIMIT = 100;
	public static final int ANIMATIONS_LIMIT = 100;

	public static final int DEFAULT_LIVES = 10;
	public static final int DEFAULT_BOMBS = 3;

	public static final int ACCELEROMETER_THRESHOLD = 12;

	public static final int CANVAS_WIDTH = 720;
	public static final int CANVAS_HEIGHT = 1280;

	public static Bitmap ScreenBitmap;

	static {
		ScreenBitmap = Bitmap.createBitmap(Globals.CANVAS_WIDTH, Globals.CANVAS_HEIGHT,
				Bitmap.Config.ARGB_8888);
	}

}
