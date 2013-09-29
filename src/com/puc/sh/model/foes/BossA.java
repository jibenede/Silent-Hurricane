package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;

public class BossA extends Foe {
	private enum State {
		ARRIVING, HOLDING, GOING_LEFT, GOING_RIGHT
	}

	private PointF mPosition;
	private float mShipRadius;
	private Bitmap mBitmap;
	private State mPhase;

	private final int SPEED = 100;

	private final int FIRING_INTERVAL = 500;
	private final int BULLET_SPEED = 500;
	private long mTimeUntilNextShot;

	public BossA(Context context, GameState state, AssetsHolder assets, int hp) {
		super(context, state, assets, hp);

		mBitmap = assets.boss;
		mShipRadius = mBitmap.getWidth() / 2;

		mPosition = new PointF(mSize.x / 2 - mShipRadius, -mBitmap.getHeight());
		mPhase = State.ARRIVING;

		mTimeUntilNextShot = 500;
	}

	@Override
	public PointF getPosition() {
		return mPosition;
	}

	@Override
	public Bitmap getBitmap() {
		return mBitmap;
	}

	@Override
	public float getAngle() {
		return 0;
	}

	@Override
	public boolean isOnScreen() {
		return mHp > 0;
	}

	@Override
	protected void updatePosition(long interval) {
		if (mPhase == State.ARRIVING) {
			mPosition.y += (interval / 1000.0) * SPEED;
			if (mPosition.y > 50) {
				mPosition.y = 50;
				mPhase = State.GOING_LEFT;
			}
		} else if (mPhase == State.GOING_LEFT) {
			mPosition.x -= (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x < 100) {
				mPosition.x = 100;
				mPhase = State.GOING_RIGHT;
			}
		} else if (mPhase == State.GOING_RIGHT) {
			mPosition.x += (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x + mBitmap.getWidth() > mSize.x - 100) {
				mPosition.x = mSize.x - 100 - mBitmap.getWidth();
				mPhase = State.GOING_LEFT;
			}
		}

	}

	@Override
	public boolean collidesWith(Bullet b) {
		return CollisionUtils.rectCollide(mPosition.y, mPosition.x + mBitmap.getWidth(),
				mPosition.y + mBitmap.getHeight(), mPosition.x, b.mPosition.y, b.mPosition.x
						+ b.mSize, b.mPosition.y + b.mSize, b.mPosition.x);
	}

	@Override
	public void fireBullets(long interval) {
		if (mPhase != State.ARRIVING) {
			mTimeUntilNextShot -= interval;
			if (mTimeUntilNextShot < 0) {
				for (int i = 0; i < 40; i++) {
					double angle = 2 * Math.PI * (i / 40f);

					sBullet.initializeLinearBullet(mAssets.laser1, false,
							(int) (Math.cos(angle) * 200), (int) (Math.sin(angle) * 200),
							mPosition.x + mShipRadius, mPosition.y + mBitmap.getHeight(), 6000, 12,
							1);
					mState.bullets.addBullet(sBullet);
				}
				mTimeUntilNextShot = FIRING_INTERVAL;
			}
		}

	}

	@Override
	public boolean isBoss() {
		return true;
	}

}
