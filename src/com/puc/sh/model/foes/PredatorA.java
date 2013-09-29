package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.FloatMath;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;

public class PredatorA extends Foe {
	private Bitmap mBitmap;
	private float mShipRadius;
	private PointF mPosition;

	private final int SPEED = 250;

	private final int FIRING_INTERVAL = 500;
	private final int BULLET_SPEED = 500;
	private long mTimeUntilNextShot;

	public PredatorA(Context context, GameState state, AssetsHolder assets, int hp, int xPosition) {
		super(context, state, assets, hp);

		mBitmap = assets.predatorA;
		mPosition = new PointF(xPosition - mBitmap.getWidth() / 2, -mBitmap.getHeight());
		mShipRadius = mBitmap.getWidth() / 2;

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
		return mHp > 0 && mPosition.y < mSize.y;
	}

	@Override
	public boolean collidesWith(Bullet b) {
		return CollisionUtils.circleCollide(mPosition.x, mPosition.y, mShipRadius, b.mPosition.x,
				b.mPosition.y, b.mSize);

	}

	@Override
	protected void updatePosition(long interval) {
		mPosition.y += SPEED * (interval / 1000.0);

	}

	@Override
	public void fireBullets(long interval) {
		mTimeUntilNextShot -= interval;
		if (mTimeUntilNextShot < 0) {
			for (int i = 1; i < 10; i++) {
				float angle = (float) (i * Math.PI / 10);

				float vX = FloatMath.cos(angle) * BULLET_SPEED;
				float vY = FloatMath.sin(angle) * BULLET_SPEED;

				sBullet.initializeLinearBullet(mAssets.laser1, false, (int) vX, (int) vY,
						mPosition.x + mBitmap.getWidth() / 2, mPosition.y + mBitmap.getHeight(),
						6000, 12, 1);

				// sBullet.initializeBullet(false, (int) vX, (int) vY,
				// mPosition.x + mBitmap.getWidth() / 2, mPosition.y +
				// mBitmap.getHeight(), 6000,
				// BulletType.Laser1, 12, 12);
				mState.bullets.addBullet(sBullet);
			}
			mTimeUntilNextShot = FIRING_INTERVAL;
		}
	}

}
