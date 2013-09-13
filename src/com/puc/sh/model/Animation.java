package com.puc.sh.model;

import com.puc.soa.Globals;
import com.puc.soa.utils.Utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.TypedValue;

public class Animation 
{
	private Bitmap[] frames;
	
	private int currentFrame;
	private long timeOfFirstFrame;
	private boolean finished;
	
	public Animation(Bitmap bitmap, int x, int y, Context context)
	{
		frames = new Bitmap[x * y];
		for(int i = 0; i < x; i++)
		{
			for (int j = 0; j < y; j++)
			{
				Bitmap bmp = Bitmap.createBitmap(bitmap,  j * (bitmap.getWidth() / x), i * (bitmap.getHeight() / y), 
						bitmap.getWidth() / x, bitmap.getHeight() / y);
				int dim = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64, context.getResources().getDisplayMetrics());
				bmp = Bitmap.createScaledBitmap(bmp, dim, dim, false);
				frames[i*x + j] = bmp;
			}
			
		}
		
	}
	
	public void prepare()
	{
		currentFrame = 0;
		timeOfFirstFrame = System.currentTimeMillis();
		finished = false;
	}
	
	public Bitmap getFrame()
	{
		currentFrame = (int) ((System.currentTimeMillis() - timeOfFirstFrame)/50);

		if (currentFrame >= frames.length)
			return null;
		else
			return frames[currentFrame];
	}
	
	public boolean isFinished()
	{
		return finished;
	}
	
	

}
