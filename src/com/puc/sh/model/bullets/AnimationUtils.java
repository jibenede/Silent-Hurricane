package com.puc.sh.model.bullets;

import android.content.Context;
import android.graphics.Bitmap;

public class AnimationUtils {
	public static Bitmap[] cropBitmapIntoRectMatrix(Context context, Bitmap bitmap, int columns,
			int rows, int width, int height) {
		Bitmap[] bitmaps = new Bitmap[columns * rows];

		for (int i = 0; i < columns; i++) {
			for (int j = 0; j < rows; j++) {
				Bitmap bmp = Bitmap.createBitmap(bitmap, j * (bitmap.getWidth() / columns), i
						* (bitmap.getHeight() / rows), bitmap.getWidth() / columns,
						bitmap.getHeight() / rows);
				bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
				bitmaps[i * columns + j] = bmp;
			}

		}
		return bitmaps;
	}

}
