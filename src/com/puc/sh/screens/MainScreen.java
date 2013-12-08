package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class MainScreen extends Screen {
	private Bitmap mBitmap;
	private Canvas mCanvas;

	private double mAlpha;

	private boolean mFading;
	private long mFadeDuration;

	private Widget mButtonPlay;
	private Widget mButtonTutorial;

	public MainScreen(AuroraContext context, RenderView renderer) {
		super(context, renderer);

		mButtonPlay = new Widget(mContext.getAssets().buttonPlay, Globals.CANVAS_WIDTH / 2
				- mContext.getAssets().buttonPlay.getWidth() / 2, Globals.CANVAS_HEIGHT / 2
				- mContext.getAssets().buttonPlay.getHeight() / 2);
		mButtonPlay.setListener(new OnTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				StageSelectionScreen screen = new StageSelectionScreen(mContext, mRenderer);
				mRenderer.transitionTo(screen);
			}
		});

		mButtonTutorial = new Widget(mContext.getAssets().buttonTutorial, Globals.CANVAS_WIDTH / 2
				- mContext.getAssets().buttonTutorial.getWidth() / 2, Globals.CANVAS_HEIGHT / 2
				+ mContext.getAssets().buttonTutorial.getHeight() / 2 + 50);
		mButtonTutorial.setListener(new OnTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				TutorialScreen screen = new TutorialScreen(mContext, mRenderer);
				mRenderer.transitionTo(screen);
			}
		});

		mAlpha = 255;
	}

	public void reset() {
		mAlpha = 255;
		mFading = false;
		mFadeDuration = 0;
	}

	@Override
	public Bitmap getBitmap() {
		if (mBitmap == null) {
			mBitmap = Globals.ScreenBitmap;
			mCanvas = new Canvas(mBitmap);
			drawCanvas();
		}

		if (mFading) {
			drawCanvas();
		}

		return mBitmap;
	}

	private void drawCanvas() {
		int alpha = (int) Math.max(0, mAlpha);

		mCanvas.drawRGB(0, 0, 0);

		Paint paint = new Paint();
		paint.setAlpha(alpha);

		mCanvas.drawBitmap(mContext.getAssets().mainBackground, 0, 0, paint);
		mCanvas.drawBitmap(mButtonPlay.mBitmap, mButtonPlay.X, mButtonPlay.Y, paint);
		mCanvas.drawBitmap(mButtonTutorial.mBitmap, mButtonTutorial.X, mButtonTutorial.Y, paint);
	}

	@Override
	public void update(long interval) {
		if (mFading) {
			mAlpha -= (255.0 * interval / mFadeDuration);
			if (mAlpha <= 0) {
				mAlpha = 0;
				mFading = false;
				onFadeFinished();
			}
		}
	}

	private void onFadeFinished() {
		StageSelectionScreen screen = new StageSelectionScreen(mContext, mRenderer);
		mRenderer.transitionTo(screen);
	}

	/**
	 * Starts a fading effect on this screen. All subsequent drawing will be
	 * applied an alpha.
	 * 
	 * @param duration
	 *            The duration in milliseconds this fade effect should take.
	 */
	public void fade(long duration) {
		mFading = true;
		mFadeDuration = duration;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mButtonPlay.hitTest(event);
		mButtonTutorial.hitTest(event);
		return false;
	}

}
