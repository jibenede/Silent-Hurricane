package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class TutorialScreen extends Screen {

	private Bitmap mBitmap;
	private Canvas mCanvas;

	private Widget mButtonHome;
	private Widget mButtonNext;

	private int currentScreen;

	private Rect rect1;
	private Rect rect2;

	public TutorialScreen(AuroraContext context, RenderView renderer) {
		super(context, renderer);
		currentScreen = 1;

		mButtonNext = new Widget(mContext.getAssets().buttonResume, Globals.CANVAS_WIDTH - 320,
				Globals.CANVAS_HEIGHT - 280);
		mButtonNext.setListener(new OnTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				currentScreen++;
			}
		});

		mButtonHome = new Widget(mContext.getAssets().buttonExit, 20, Globals.CANVAS_HEIGHT - 280);
		mButtonHome.setListener(new OnTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				MainScreen screen = new MainScreen(mContext, mRenderer);
				mRenderer.transitionTo(screen);
			}
		});

		rect1 = new Rect(20, Globals.CANVAS_HEIGHT - 200, 200, Globals.CANVAS_HEIGHT - 40);
		rect2 = new Rect(Globals.CANVAS_WIDTH - 200, Globals.CANVAS_HEIGHT - 200,
				Globals.CANVAS_WIDTH - 20, Globals.CANVAS_HEIGHT - 40);
	}

	@Override
	public Bitmap getBitmap() {
		if (mBitmap == null) {
			mBitmap = Globals.ScreenBitmap;
			mCanvas = new Canvas(mBitmap);
		}

		drawCanvas();

		return mBitmap;
	}

	private void drawCanvas() {
		if (currentScreen == 1) {
			mCanvas.drawBitmap(mContext.getAssets().tutorial1, 0, 0, null);
		} else if (currentScreen == 2) {
			mCanvas.drawBitmap(mContext.getAssets().tutorial2, 0, 0, null);
		} else {
			mCanvas.drawBitmap(mContext.getAssets().tutorial3, 0, 0, null);
		}

		mCanvas.drawBitmap(mButtonHome.mBitmap, null, rect1, null);
		if (currentScreen < 3) {
			mCanvas.drawBitmap(mButtonNext.mBitmap, null, rect2, null);
		}
	}

	@Override
	public void update(long interval) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mButtonHome.hitTest(event);
			if (currentScreen < 3) {
				mButtonNext.hitTest(event);
			}
		}

		return true;
	}

	@Override
	public boolean onBackPressed() {
		MainScreen screen = new MainScreen(mContext, mRenderer);
		mRenderer.transitionTo(screen);
		return true;
	}

}
