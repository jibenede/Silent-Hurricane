package com.puc.sh.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.puc.sh.model.Audio;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;

public abstract class Screen {
	protected Context mContext;
	protected Point mSize;
	protected AssetsHolder mAssets;
	protected GameState mState;
	protected RenderView mRenderer;

	public Screen(Context context, AssetsHolder assets, GameState state, RenderView renderer) {
		mContext = context;
		mAssets = assets;
		mState = state;
		mRenderer = renderer;

		WindowManager wManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wManager.getDefaultDisplay();
		mSize = new Point();
		display.getSize(mSize);
	}

	public abstract Bitmap getBitmap();

	public abstract void update(long interval);

	public Audio getAudio() {
		return null;
	}

	public boolean onTouchEvent(MotionEvent event) {
		return false;
	}
}
