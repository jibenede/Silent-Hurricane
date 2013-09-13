package com.puc.sh.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.sh.model.stages.Stage1;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;

public class MainScreen extends Screen {
	private Bitmap mBitmap;
	private Canvas mCanvas;

	private double mAlpha;

	private boolean mFading;
	private long mFadeDuration;

	private Widget mPlayButton;

	public MainScreen(Context context, AssetsHolder assets, GameState state, RenderView renderer) {
		super(context, assets, state, renderer);

		mPlayButton = new Widget(assets.buttonPlay, mSize.x / 2 - assets.buttonPlay.getWidth() / 2,
				mSize.y / 2 - assets.buttonPlay.getHeight() / 2);
		mPlayButton.setListener(new OnTouchListener() {
			public void onTouchEvent(MotionEvent event) {
				fade(1000);
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
			mBitmap = Bitmap.createBitmap(mSize.x, mSize.y, Bitmap.Config.ARGB_8888);
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

		mCanvas.drawBitmap(mAssets.mainBackground, 0, 0, paint);
		paint.setColor(Color.YELLOW);
		paint.setAlpha(alpha);
		mCanvas.drawBitmap(mPlayButton.mBitmap, mPlayButton.X, mPlayButton.Y, paint);
	}

	@Override
	public void update(long interval) {
		if (mFading) {
			mAlpha -= (255.0 * interval / mFadeDuration);
			Log.i("Game", "Alpha: " + mAlpha);
			if (mAlpha <= 0) {
				mAlpha = 0;
				mFading = false;
				onFadeFinished();
			}
		}
	}

	private void onFadeFinished() {
		Stage1 stage = new Stage1(mContext, mState, mAssets, mRenderer);
		GameScreen screen = new GameScreen(mContext, mAssets, mState, mRenderer, stage);
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
		mPlayButton.hitTest(event);
		return false;
	}

}
