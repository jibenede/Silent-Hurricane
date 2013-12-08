package com.puc.sh.model;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.TypedValue;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.BulletEmitter;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.AuroraContext;
import com.puc.soa.GameState;
import com.puc.soa.Globals;

public class Player {
	public enum PlayerState {
		ALIVE, DEAD, REVIVING, DEFEATED
	}

	public enum WeaponType {
		WideSpread, Heavy, Support, None
	}

	public static final int BULLET_INTERVAL = 80;
	public static final int TIME_FOR_REVIVAL = 1000;
	public static final int TIME_OF_INVULNERABILITY = 2000;
	public static final int BULLET_SPEED = 1500;

	private AuroraContext mContext;
	private int mStartX;
	private int mStartY;

	public PlayerState mStatus;

	public PointF mShipPosition;
	private PointF shipDestination;

	public int mLives;
	public int mBombs;

	private int timeSinceLastBullet;
	public Bitmap mBitmap;

	private Bullet b;

	private long mTimeOfLastRebirth;
	private long mPowerupDuration;
	private long mTimeOfLastDeath;
	private long mTicks;

	private WeaponType mPowerup;

	private AssetsHolder mAssets;
	private GameState mState;

	public Player(AuroraContext context) {
		mContext = context;

		mBitmap = context.getAssets().ship;
		this.b = new Bullet(context);

		mAssets = context.getAssets();
		mState = context.getState();

		reset();
	}

	public void reset() {
		mStartX = (Globals.CANVAS_WIDTH - mBitmap.getWidth()) / 2;
		mStartY = Globals.CANVAS_HEIGHT - mBitmap.getHeight() - 50;
		mShipPosition = new PointF(mStartX, mStartY);
		shipDestination = new PointF(-1, -1);
		mPowerupDuration = 0;

		mLives = mContext.getPersistanceManager().getLives();
		mBombs = Globals.DEFAULT_BOMBS;

		this.timeSinceLastBullet = 0;

		mStatus = PlayerState.ALIVE;
		mPowerup = WeaponType.None;
	}

	public boolean shouldDraw() {
		if (mStatus == PlayerState.ALIVE) {
			return true;
		} else if (mStatus == PlayerState.REVIVING) {
			// Flicker effect every 250 ms when reviving.
			if (((mTicks - mTimeOfLastRebirth) / 100) % 2 == 0) {
				return true;
			}
		}
		return false;
	}

	public void update(long interval) {
		mTicks += interval;

		if (mStatus == PlayerState.ALIVE || mStatus == PlayerState.REVIVING) {
			this.updateShipPosition(interval);

			if (mPowerupDuration > 0) {
				mPowerupDuration -= interval;
				if (mPowerupDuration <= 0) {
					mPowerup = WeaponType.None;
				}
			}

			testPowerup();

			timeSinceLastBullet += interval;
			if (timeSinceLastBullet > BULLET_INTERVAL) {
				timeSinceLastBullet = 0;
				this.fireBullets();
			}

			if (mStatus == PlayerState.ALIVE && this.hitTest()) {
				this.die();
			}

			if (mStatus == PlayerState.REVIVING
					&& mTicks - mTimeOfLastRebirth > TIME_OF_INVULNERABILITY) {
				mStatus = PlayerState.ALIVE;
			}

		} else if (mStatus == PlayerState.DEAD) {
			if (mTicks - mTimeOfLastDeath > TIME_FOR_REVIVAL) {
				mShipPosition.x = mStartX;
				mShipPosition.y = mStartY;

				mStatus = PlayerState.REVIVING;
				mTimeOfLastRebirth = mTicks;
			}
		}
	}

	private void die() {
		mPowerup = WeaponType.None;
		mPowerupDuration = 0;

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

		mTimeOfLastDeath = mTicks;
	}

	private void fireBullets() {
		float x = mShipPosition.x + mBitmap.getWidth() / 2 - 16;

		if (mPowerup == WeaponType.None) {
			for (int i = -50; i <= 50; i += 50) {
				b.initializeLinearBullet(mAssets.plasma, true, i, -BULLET_SPEED, x,
						mShipPosition.y, 2000, 32, 1);
				mState.mPlayerBullets.addBullet(b);
			}
		} else if (mPowerup == WeaponType.WideSpread) {
			for (int i = -400; i <= 400; i += 100) {
				b.initializeLinearBullet(mAssets.plasma, true, i, -BULLET_SPEED, x,
						mShipPosition.y, 2000, 24, 1);
				mState.mPlayerBullets.addBullet(b);
			}
		} else if (mPowerup == WeaponType.Heavy) {
			for (int i = -60; i <= 60; i += 30) {
				b.initializeLinearBullet(mAssets.heavy, true, 0, -BULLET_SPEED, mShipPosition.x
						+ mBitmap.getWidth() / 2 - 12 + i, mShipPosition.y, 2000, 24, 3);
				mState.mPlayerBullets.addBullet(b);
			}
		}
	}

	public void setDestination(int x, int y) {
		int deltaY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, mContext
				.getContext().getResources().getDisplayMetrics());
		if (x != -1 && y != -1)
			shipDestination.set(x - mBitmap.getWidth() / 4, y - deltaY);
		else
			shipDestination.set(x, y);
	}

	private void updateShipPosition(long interval) {
		if (shipDestination.x != -1 && shipDestination.y != -1) {
			float xDelta = shipDestination.x - mShipPosition.x;
			float yDelta = shipDestination.y - mShipPosition.y;

			float delta = (float) (Math.sqrt(xDelta * xDelta + yDelta * yDelta));

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

			if (mShipPosition.y < 0) {
				mShipPosition.y = 0;
			}
		}
	}

	private void testPowerup() {
		for (int i = 0; i < mState.mPowerups.size(); i++) {
			Powerup p = (Powerup) mState.mPowerups.get(i);
			if (p.isOnScreen()
					&& CollisionUtils.rectCollide(mShipPosition.y,
							mShipPosition.x + mBitmap.getWidth(),
							mShipPosition.y + mBitmap.getHeight(), mShipPosition.x, p.mPosition.y,
							p.mPosition.x + p.mBitmap.getWidth(),
							p.mPosition.y + p.mBitmap.getHeight(), p.mPosition.x)) {
				p.consume();

				if (p.mType == Powerup.PowerupType.WideSpread) {
					mPowerup = WeaponType.WideSpread;
				} else if (p.mType == Powerup.PowerupType.Heavy) {
					mPowerup = WeaponType.Heavy;
				}
				mPowerupDuration = 5000;
			}
		}
	}

	public long getTimeSinceLastDeath() {
		return mTicks - mTimeOfLastDeath;
	}

	private boolean hitTest() {
		Bullet b;
		for (int i = 0; i < mState.mEnemyBullets.size(); i++) {
			b = mState.mEnemyBullets.getBullet(i);

			if (b.mDisplay && CollisionUtils.playerBulletCollide(this, b)) {
				return true;
			}
		}

		for (int i = 0; i < mState.mSpecialBullets.size(); i++) {
			BulletEmitter s = (BulletEmitter) mState.mSpecialBullets.get(i);

			if (s.isOnScreen() && CollisionUtils.playerBulletCollide(this, s)) {
				return true;
			}
		}
		return false;
	}

}
