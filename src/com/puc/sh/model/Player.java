package com.puc.sh.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.TypedValue;
import android.view.WindowManager;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.Globals;
import com.puc.soa.utils.BulletArray;

public class Player {
	public enum PlayerState {
		ALIVE, DEAD, REVIVING, DEFEATED
	}

	public static final int BULLET_INTERVAL = 200;
	public static final int TIME_FOR_REVIVAL = 2000;
	public static final int TIME_OF_INVULNERABILITY = 3000;

	private Context mContext;
	private int mStartX;
	private int mStartY;

	public PlayerState mStatus;

	public PointF mShipPosition;
	private PointF shipDestination;

	private int mLives;
	private int timeSinceLastBullet;
	public Bitmap mBitmap;

	private BulletArray bulletArray;
	private Bullet b;

	private AssetsHolder mAssets;
	private GameState mState;

	private long mTimeOfLastRebirth;
	private long mTimeOfLastDeath;

	public Player(Context context, GameState state, AssetsHolder assets) {
		mContext = context;
		mState = state;
		mAssets = assets;

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		mBitmap = assets.ship;

		mStartX = (width - mBitmap.getWidth()) / 2;
		mStartY = height - mBitmap.getHeight() - 50;
		mShipPosition = new PointF(mStartX, mStartY);
		shipDestination = new PointF(-1, -1);

		mLives = Globals.DEFAULT_LIVES;

		this.timeSinceLastBullet = 0;
		this.bulletArray = mState.getBullets();
		this.b = new Bullet();

		mStatus = PlayerState.ALIVE;
	}

	public boolean shouldDraw() {
		if (mStatus == PlayerState.ALIVE) {
			return true;
		} else if (mStatus == PlayerState.REVIVING) {
			// Flicker effect every 250 ms when reviving.
			if (((System.currentTimeMillis() - mTimeOfLastRebirth) / 100) % 2 == 0) {
				return true;
			}
		}
		return false;
	}

	public void update(long interval) {
		if (mStatus == PlayerState.ALIVE || mStatus == PlayerState.REVIVING) {
			this.updateShipPosition(interval);

			timeSinceLastBullet += interval;
			if (timeSinceLastBullet > BULLET_INTERVAL) {
				timeSinceLastBullet = 0;
				this.fireBullets();
			}

			if (mStatus == PlayerState.ALIVE && this.hitTest()) {
				this.die();
			}

			if (mStatus == PlayerState.REVIVING
					&& System.currentTimeMillis() - mTimeOfLastRebirth > TIME_OF_INVULNERABILITY) {
				mStatus = PlayerState.ALIVE;
			}

		} else if (mStatus == PlayerState.DEAD) {
			if (System.currentTimeMillis() - mTimeOfLastDeath > TIME_FOR_REVIVAL) {
				mShipPosition.x = mStartX;
				mShipPosition.y = mStartY;

				mStatus = PlayerState.REVIVING;
				mTimeOfLastRebirth = System.currentTimeMillis();
			}
		}
	}

	private void die() {
		mStatus = PlayerState.DEAD;
		mLives--;
		if (mLives <= 0) {
			mStatus = PlayerState.DEFEATED;
		}

		Animation a = new Animation(mContext, mAssets.death, 50, mShipPosition.x
				+ mBitmap.getWidth() / 2 - mAssets.death[0].getWidth() / 2, mShipPosition.y
				+ mBitmap.getHeight() / 2 - mAssets.death[0].getHeight() / 2);
		a.prepare();
		mState.mAnimations.add(a);

		mTimeOfLastDeath = System.currentTimeMillis();
	}

	private void fireBullets() {
		float x = mShipPosition.x + mBitmap.getWidth() / 2 - 16;

		b.initializeLinearBullet(mAssets.plasma, true, -50, -1000, x, mShipPosition.y, 2000, 32, 1);
		bulletArray.addBullet(b);

		b.initializeLinearBullet(mAssets.plasma, true, 0, -1000, x, mShipPosition.y, 2000, 32, 1);
		bulletArray.addBullet(b);

		b.initializeLinearBullet(mAssets.plasma, true, 50, -1000, x, mShipPosition.y, 2000, 32, 1);
		bulletArray.addBullet(b);
	}

	public void setDestination(int x, int y) {
		int deltaY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, mContext
				.getResources().getDisplayMetrics());
		if (x != -1 && y != -1)
			shipDestination.set(x - mBitmap.getWidth() / 4, y - deltaY);
		else
			shipDestination.set(x, y);
	}

	private void updateShipPosition(long interval) {
		if (shipDestination.x != -1 && shipDestination.y != -1) {
			float xDelta = shipDestination.x - mShipPosition.x;
			float yDelta = shipDestination.y - mShipPosition.y;

			float delta = FloatMath.sqrt(xDelta * xDelta + yDelta * yDelta);

			float xSpeed = delta < 1 ? 0 : xDelta / delta * Globals.MAX_SPEED * interval / 1000;
			float ySpeed = delta < 1 ? 0 : yDelta / delta * Globals.MAX_SPEED * interval / 1000;

			if (xDelta >= 0)
				mShipPosition.x += Math.min(xSpeed, xDelta);
			else
				mShipPosition.x += Math.max(xSpeed, xDelta);

			if (yDelta >= 0)
				mShipPosition.y += Math.min(ySpeed, yDelta);
			else
				mShipPosition.y += Math.max(ySpeed, yDelta);
		}
	}

	private boolean hitTest() {
		Bullet b;
		for (int i = 0; i < this.bulletArray.size(); i++) {
			b = this.bulletArray.getBullet(i);

			if (!b.mBenign && CollisionUtils.playerBulletCollide(this, b)) {
				return true;
			}
		}
		return false;
	}

}
