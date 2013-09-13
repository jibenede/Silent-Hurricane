package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.Bullet.BulletType;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;

public class BossA extends Foe {
	private enum State {
		ARRIVING, HOLDING, GOING_LEFT, GOING_RIGHT
	}

	private PointF mPosition;
	private Bitmap mBitmap;
	private State mPhase;

	private final int SPEED = 100;

	private final int FIRING_INTERVAL = 500;
	private final int BULLET_SPEED = 500;
	private long mTimeUntilNextShot;

	public BossA(Context context, GameState state, AssetsHolder assets, int hp) {
		super(context, state, assets, hp);

		mBitmap = assets.boss;

		mPosition = new PointF(mSize.x / 2, -mBitmap.getHeight());
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
	public boolean isGone() {
		return mHp <= 0;
	}

	@Override
	protected void updatePosition(long interval) {
		if (mPhase == State.ARRIVING) {
			mPosition.y += (interval / 1000.0) * SPEED;
			if (mPosition.y > 250) {
				mPosition.y = 250;
				mPhase = State.GOING_LEFT;
			}
		} else if (mPhase == State.GOING_LEFT) {
			mPosition.x -= (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x < 150) {
				mPosition.x = 150;
				mPhase = State.GOING_RIGHT;
			}
		} else if (mPhase == State.GOING_RIGHT) {
			mPosition.x += (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x > mSize.x - 150) {
				mPosition.x = mSize.x - 150;
				mPhase = State.GOING_LEFT;
			}
		}

	}

	@Override
	public boolean collidesWith(Bullet b) {
		sFoeRect.set((int) mPosition.x, (int) mPosition.y,
				(int) (mPosition.x + mBitmap.getWidth()), (int) (mPosition.y + mBitmap.getHeight()));

		sBulletRect.set((int) b.getX(), (int) b.positionY, (int) (b.positionX + b.sizeX),
				(int) (b.positionY + b.sizeY));

		return sFoeRect.intersect(sBulletRect);
	}

	@Override
	public void fireBullets(long interval) {
		if (mPhase != State.ARRIVING) {
			mTimeUntilNextShot -= interval;
			if (mTimeUntilNextShot < 0) {
				for (int i = 0; i < 40; i++) {
					double angle = 2 * Math.PI * (i / 40f);

					sBullet.initializeBullet(false, (int) (Math.cos(angle) * 200),
							(int) (Math.sin(angle) * 200), getX() + mBitmap.getWidth() / 2, getY()
									+ mBitmap.getHeight(), 6000, BulletType.Laser1, 12, 12);
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
