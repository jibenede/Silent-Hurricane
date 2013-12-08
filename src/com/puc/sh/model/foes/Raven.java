package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;

public class Raven extends Foe {
	private final int SPEED = 100;
	private final int FIRING_INTERVAL = 2000;
	private final int BULLET_SPEED = 400;

	private long mTimeUntilExit;
	private long mTimeUntilNextShot;

	public Raven(AuroraContext context, int hp, int waitTime, int posX, int posY) {
		super(context, hp, context.getAssets().raven);

		mTimeUntilNextShot = 1000;
		mTimeUntilExit = waitTime;
		mPosition = new PointF(posX - mBitmap.getWidth() / 2, posY);
	}

	@Override
	public float getAngle() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isOnScreen() {
		// TODO Auto-generated method stub
		return mHp > 0 && mPosition.y > -mBitmap.getHeight();
	}

	@Override
	protected void updatePosition(long interval) {
		mTimeUntilExit -= interval;
		if (mTimeUntilExit < 0) {
			mPosition.y -= SPEED * (interval / 1000f);
		}

	}

	@Override
	public void fireBullets(long interval) {
		mTimeUntilNextShot -= interval;
		if (mTimeUntilNextShot < 0) {
			double deltaX = mContext.getState().mShip.mShipPosition.x - mPosition.x;
			double deltaY = mContext.getState().mShip.mShipPosition.y - mPosition.y;

			double angle = Math.atan2(deltaY, deltaX);

			for (int i = -3; i <= 3; i++) {
				for (int j = 0; j < 6; j++) {
					double a = angle + Math.PI / 100 * i;

					double vX = (1 + j * 0.2) * BULLET_SPEED * Math.cos(a);
					double vY = (1 + j * 0.2) * BULLET_SPEED * Math.sin(a);

					mBullet.initializeLinearBullet(mContext.getAssets().yellowBullet24, false,
							(int) vX, (int) vY, mPosition.x + mBitmap.getWidth() / 2 - 12,
							mPosition.y + mBitmap.getHeight() / 2 - 12, 6000, 24, 1);
					mContext.getState().mEnemyBullets.addBullet(mBullet);
				}

			}

			mTimeUntilNextShot = FIRING_INTERVAL;
		}

	}

}
