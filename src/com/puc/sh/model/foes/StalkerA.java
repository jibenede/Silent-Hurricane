package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.FloatMath;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;

public class StalkerA extends Foe {
	public enum Origin {
		BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private enum State {
		GOING_UP, ROTATING, GOING_DOWN
	}

	private PointF mPosition;
	private State mPhase;
	private Bitmap mBitmap;
	private float mAngle;
	private Origin mOrigin;
	private float mShipRadius;

	private final int PADDING = 50;
	private final int SPEED = 600;

	private final int FIRING_INTERVAL = 500;
	private final int BULLET_SPEED = 300;

	private int mRadius;

	private long mTimeUntilNextShot;

	public StalkerA(Context context, GameState state, AssetsHolder assets, int hp, Origin origin) {
		super(context, state, assets, hp);

		if (origin == Origin.BOTTOM_LEFT) {
			mPosition = new PointF(PADDING, mSize.y + PADDING);
		} else if (origin == Origin.BOTTOM_RIGHT) {
			mPosition = new PointF(mSize.x - PADDING, mSize.y + PADDING);
		}
		mPhase = State.GOING_UP;
		mRadius = mSize.x / 2 - 50;
		mAngle = 0;
		mBitmap = assets.stalkerA;
		mOrigin = origin;

		mTimeUntilNextShot = 1000;
		mShipRadius = mBitmap.getWidth() / 2;
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
		return mAngle;
	}

	@Override
	public boolean isOnScreen() {
		return mHp > 0 && !(mPhase == State.GOING_DOWN && mPosition.y > mSize.y + 100);
	}

	@Override
	public boolean collidesWith(Bullet b) {
		return CollisionUtils.circleCollide(mPosition.x, mPosition.y, mShipRadius, b.mPosition.x,
				b.mPosition.y, b.mSize);

	}

	@Override
	protected void updatePosition(long interval) {
		if (mPhase == State.GOING_UP) {
			mPosition.y -= (SPEED * interval) / 1000;
			if (mPosition.y < mRadius + PADDING) {
				mPosition.y = mRadius + PADDING;
				mPhase = State.ROTATING;
			}
		} else if (mPhase == State.ROTATING) {
			if (mOrigin == Origin.BOTTOM_LEFT) {
				mAngle += ((float) SPEED / mRadius) * (interval / 1000.0);
			} else if (mOrigin == Origin.BOTTOM_RIGHT) {
				mAngle -= ((float) SPEED / mRadius) * (interval / 1000.0);
			}

			if (mOrigin == Origin.BOTTOM_LEFT && mAngle > Math.PI || mOrigin == Origin.BOTTOM_RIGHT
					&& mAngle < -Math.PI) {
				mAngle = (float) Math.PI;
				mPhase = State.GOING_DOWN;
			}

			if (mOrigin == Origin.BOTTOM_LEFT) {
				mPosition.x = -FloatMath.cos(mAngle) * mRadius + mSize.x / 2;
				mPosition.y = (PADDING + mRadius) - FloatMath.sin(mAngle) * mRadius;
			} else if (mOrigin == Origin.BOTTOM_RIGHT) {
				mPosition.x = FloatMath.cos(mAngle) * mRadius + mSize.x / 2;
				mPosition.y = (PADDING + mRadius) + FloatMath.sin(mAngle) * mRadius;
			}

		} else if (mPhase == State.GOING_DOWN) {
			mPosition.y += (SPEED * interval) / 1000;
		}

	}

	@Override
	public void fireBullets(long interval) {
		mTimeUntilNextShot -= interval;
		if (mTimeUntilNextShot < 0) {
			float vX = mState.mShip.mShipPosition.x - mPosition.x;
			float vY = mState.mShip.mShipPosition.y - mPosition.y;
			float factor = FloatMath.sqrt(vX * vX + vY * vY) / BULLET_SPEED;
			vX /= factor;
			vY /= factor;

			sBullet.initializeLinearBullet(mAssets.laser1, false, (int) vX, (int) vY, mPosition.x
					+ mBitmap.getWidth() / 2, mPosition.y + mBitmap.getHeight() / 2, 6000, 12, 1);
			mState.bullets.addBullet(sBullet);

			mTimeUntilNextShot = FIRING_INTERVAL;
		}
	}

}
