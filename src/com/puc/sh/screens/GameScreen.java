package com.puc.sh.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Audio;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.sh.model.stages.Stage;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.CircularArray;

public class GameScreen extends Screen {
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;
	private Matrix mMatrix;
	private Stage mStage;
	private PointF mPoint;

	public GameScreen(Context context, AssetsHolder assets, GameState state, RenderView renderer,
			Stage stage) {
		super(context, assets, state, renderer);

		mPaint = new Paint();
		mMatrix = new Matrix();
		mStage = stage;
		mPoint = new PointF();
	}

	@Override
	public Bitmap getBitmap() {
		if (mBitmap == null) {
			mBitmap = Bitmap.createBitmap(mSize.x, mSize.y, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}

		drawCanvas();

		return mBitmap;
	}

	private void drawCanvas() {
		mCanvas.drawBitmap(mAssets.stageBackground, 0, 0, null);

		// mPaint.setColor(Color.BLACK);
		// mPaint.setTextSize(24);
		// mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		// mCanvas.drawText("BOSS", 20, 20, mPaint);
		//
		// mPaint.setColor(Color.rgb(255, 210, 0));
		// mPaint.setStrokeWidth(10);
		// float w = (mSize.x - 40) * mState.getBossHpRatio();
		// mCanvas.drawLine(20, 40, 20 + w, 40, mPaint);

		BulletArray bullets = mState.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = bullets.getBullet(i);
			if (b.mDisplay) {
				mCanvas.drawBitmap(b.mBitmap, b.mPosition.x, b.mPosition.y, null);
			}
		}

		if (mState.isPlayerContinuing() && mState.mShip.shouldDraw()) {
			mCanvas.drawBitmap(mState.mShip.mBitmap, mState.mShip.mShipPosition.x,
					mState.mShip.mShipPosition.y, null);
		}

		CircularArray animations = mState.mAnimations;
		for (int i = 0; i < animations.size(); i++) {
			Animation a = (Animation) animations.get(i);
			if (a.isOnScreen()) {
				mCanvas.drawBitmap(a.getFrame(), a.mPosition.x, a.mPosition.y, null);
			}
		}

		CircularArray enemies = mState.mEnemies;
		for (int i = 0; i < enemies.size(); i++) {
			Foe e = (Foe) enemies.get(i);
			if (e.isOnScreen()) {
				mMatrix.reset();
				mMatrix.setTranslate(e.getPosition().x, e.getPosition().y);
				float degrees = e.getAngle() * 360 / (2 * (float) Math.PI);
				mMatrix.postRotate(degrees, e.getPosition().x + e.getBitmap().getWidth() / 2,
						e.getPosition().y + e.getBitmap().getHeight() / 2);

				mCanvas.drawBitmap(e.getBitmap(), mMatrix, null);
			}
		}
	}

	@Override
	public void update(long interval) {
		mStage.update(interval);
		mState.update(interval);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			int shipX = (int) event.getX() - mAssets.ship.getWidth() / 2;
			int shipY = (int) event.getY() - mAssets.ship.getHeight() / 2;
			mState.setDestination(shipX, shipY);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int shipX = (int) event.getX() - mAssets.ship.getWidth() / 2;
			int shipY = (int) event.getY() - mAssets.ship.getHeight() / 2;
			mState.setDestination(shipX, shipY);
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			mState.setDestination(-1, -1);
		}
		return true;
	}

	@Override
	public Audio getAudio() {
		return mStage.getStageTheme();
	}

}
