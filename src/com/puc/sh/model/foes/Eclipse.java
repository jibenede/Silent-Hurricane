package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Eclipse extends Foe {
	public enum Direction {
		RIGHT, LEFT
	}

	private enum Phase {
		MOVING_UP, ROTATING, MOVING_SIDEWAYS
	}

	private final int PIVOT = 500;
	private final int BULLET_SPEED = 50;
	private final long FIRING_INTERVAL = 1000;
	private final int SPEED = 150;
	private final int RADIUS = 250;

	private Phase mPhase;
	private Direction mDirection;
	private int mStartX;
	private float mStartAngle;
	private int mCounter;

	private float mAngle;
	private long mTimeUntilNextShot = 1000;

	public Eclipse(AuroraContext context, int hp, Direction direction, int startX) {
		super(context, hp, context.getAssets().eclipse);

		mDirection = direction;
		mPhase = Phase.MOVING_UP;

		mStartX = startX - mBitmap.getWidth() / 2;

		mPosition = new PointF(mStartX, Globals.CANVAS_HEIGHT);
		mStartAngle = mAngle = 0;

	}

	@Override
	public float getAngle() {
		return (float) (Math.PI + mAngle);
	}

	@Override
	public boolean isOnScreen() {
		return mHp > 0 && mPosition.x >= -mBitmap.getWidth() && mPosition.x <= Globals.CANVAS_WIDTH;
	}

	@Override
	protected void updatePosition(long interval) {
		if (mPhase == Phase.MOVING_UP) {
			mPosition.y -= SPEED * (interval / 1000f);

			if (mPosition.y < PIVOT) {
				mPosition.y = PIVOT;
				mPhase = Phase.ROTATING;
			}

		} else if (mPhase == Phase.ROTATING) {
			if (mDirection == Direction.RIGHT) {
				mAngle += ((float) SPEED / RADIUS) * (interval / 1000.0);
				if (mAngle > mStartAngle + Math.PI / 2) {
					mAngle = (float) (mStartAngle + Math.PI / 2);
					mPhase = Phase.MOVING_SIDEWAYS;
				}
			} else {
				mAngle -= ((float) SPEED / RADIUS) * (interval / 1000.0);
				if (mAngle < mStartAngle - Math.PI / 2) {
					mAngle = (float) (mStartAngle - Math.PI / 2);
					mPhase = Phase.MOVING_SIDEWAYS;
				}
			}

			if (mDirection == Direction.RIGHT) {
				mPosition.x = (float) (-Math.cos(mAngle) * RADIUS + mStartX + RADIUS);
				mPosition.y = (float) (PIVOT - RADIUS * Math.sin(mAngle));
			} else {
				mPosition.x = (float) (Math.cos(mAngle) * RADIUS + mStartX - RADIUS);
				mPosition.y = (float) (PIVOT + RADIUS * Math.sin(mAngle));
			}

		} else {
			if (mDirection == Direction.RIGHT) {
				mPosition.x += SPEED * (interval / 1000f);
			} else {
				mPosition.x -= SPEED * (interval / 1000f);
			}
		}

	}

	@Override
	public void fireBullets(long interval) {
		mTimeUntilNextShot -= interval;
		if (mTimeUntilNextShot < 0) {
			for (int i = 1; i < 3; i++) {
				float angle = (float) (Utils.sRandom.nextFloat() * Math.PI * 2);

				float vX = (float) (Math.cos(angle) * BULLET_SPEED);
				float vY = (float) (Math.sin(angle) * BULLET_SPEED);

				mBullet.initializeLinearBullet(mContext.getAssets().redBullet12, false, (int) vX,
						(int) vY, mPosition.x + mBitmap.getWidth() / 2,
						mPosition.y + mBitmap.getHeight(), 0, 12, 1);
				mContext.getState().mEnemyBullets.addBullet(mBullet);
			}

			mTimeUntilNextShot = FIRING_INTERVAL;
		}

	}

}
