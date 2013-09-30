package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.FloatMath;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;

public class BossA extends Foe {
	private enum BulletPattern {
		Pattern1, Pattern2
	}

	private enum MovementPattern {
		ARRIVING, HOLDING, GOING_LEFT, GOING_RIGHT, RESET, STILL
	}

	private PointF mPosition;
	private float mShipRadius;
	private Bitmap mBitmap;

	private int mStartingHp;

	private MovementPattern mMovementPhase;
	private BulletPattern mBulletPhase;

	private final int SPEED = 100;

	private float mSpeedX;
	private float mSpeedY;

	private boolean mSpeedCalculated;

	private final int FIRING_INTERVAL = 500;
	private final int BULLET_SPEED = 500;
	private long mTimeUntilNextShot;

	private float resetX;
	private float resetY;

	public BossA(Context context, GameState state, AssetsHolder assets, int hp) {
		super(context, state, assets, hp);
		mStartingHp = hp;

		mBitmap = assets.boss;
		mShipRadius = mBitmap.getWidth() / 2;

		mPosition = new PointF(mSize.x / 2 - mShipRadius, -mBitmap.getHeight());
		mMovementPhase = MovementPattern.ARRIVING;
		mBulletPhase = BulletPattern.Pattern2;

		mTimeUntilNextShot = 500;

		resetX = mPosition.x;
		resetY = 50;
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
		if (mMovementPhase == MovementPattern.ARRIVING) {
			mPosition.y += (interval / 1000.0) * SPEED;
			if (mPosition.y > 50) {
				mPosition.y = 50;
				mMovementPhase = MovementPattern.STILL;
			}
		} else if (mMovementPhase == MovementPattern.GOING_LEFT) {
			mPosition.x -= (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x < 100) {
				mPosition.x = 100;
				mMovementPhase = MovementPattern.GOING_RIGHT;
			}
		} else if (mMovementPhase == MovementPattern.GOING_RIGHT) {
			mPosition.x += (interval / 1000.0) * (SPEED / 2);
			if (mPosition.x + mBitmap.getWidth() > mSize.x - 100) {
				mPosition.x = mSize.x - 100 - mBitmap.getWidth();
				mMovementPhase = MovementPattern.GOING_LEFT;
			}
		} else if (mMovementPhase == MovementPattern.RESET) {
			if (!mSpeedCalculated) {
				float vX = resetX - mPosition.x;
				float vY = resetY - mPosition.y;
				float factor = FloatMath.sqrt(vX * vX + vY * vY) / SPEED;
				mSpeedX = vX / factor;
				mSpeedY = vY / factor;
				mSpeedCalculated = true;
			}

			mPosition.x += (interval / 1000.0) * mSpeedX;
			mPosition.y += (interval / 1000.0) * mSpeedY;

			float d = CollisionUtils.squareDistance(mPosition.x, mPosition.y, resetX, resetY);
			// Log.i("game", "resetX: " + resetX + "; posX : " + mPosition.x +
			// "; distance: " + d);
			if (d < 15) {
				mPosition.x = resetX;
				mPosition.y = resetY;
				mMovementPhase = MovementPattern.STILL;
			}

		}

	}

	@Override
	public boolean collidesWith(Bullet b) {
		return CollisionUtils.rectCollide(mPosition.y, mPosition.x + mBitmap.getWidth(),
				mPosition.y + mBitmap.getHeight(), mPosition.x, b.mPosition.y, b.mPosition.x
						+ b.mSize, b.mPosition.y + b.mSize, b.mPosition.x);
	}

	private float delta = 0;

	@Override
	public void fireBullets(long interval) {
		if (mMovementPhase != MovementPattern.ARRIVING && mMovementPhase != MovementPattern.RESET) {
			if (mBulletPhase == BulletPattern.Pattern1) {
				mTimeUntilNextShot -= interval;
				if (mTimeUntilNextShot < 0) {
					for (int i = 0; i < 40; i++) {
						double angle = 2 * Math.PI * (i / 40f);

						sBullet.initializeLinearBullet(mAssets.laser1, false,
								(int) (Math.cos(angle) * 200), (int) (Math.sin(angle) * 200),
								mPosition.x + mShipRadius, mPosition.y + mBitmap.getHeight(), 6000,
								12, 1);
						mState.mBullets.addBullet(sBullet);
					}
					mTimeUntilNextShot = FIRING_INTERVAL;
				}
			} else if (mBulletPhase == BulletPattern.Pattern2) {
				mTimeUntilNextShot -= interval;
				if (mTimeUntilNextShot < 0) {
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 16; j++) {
							float angle = (float) (2 * Math.PI * (i / 8f) + (Math.PI / 8)
									* (j / 16f) + delta);

							float vX = FloatMath.cos(angle) * (350 + 4 * j);
							float vY = FloatMath.sin(angle) * (350 + 4 * j);

							sBullet.initializeLinearBullet(mAssets.fireball, false, vX, vY,
									mPosition.x + mBitmap.getWidth() / 2,
									mPosition.y + mBitmap.getHeight() / 2 + (j * 3), 3000, 24, 1);
							mState.mBullets.addBullet(sBullet);
						}
					}
					for (int i = 0; i < 8; i++) {
						for (int j = 0; j < 16; j++) {
							float angle = (float) (2 * Math.PI * ((7 - i) / 8f) - (Math.PI / 8)
									* (j / 16f) + Math.PI / 8);

							float vX = FloatMath.cos(angle) * (350 + 4 * j);
							float vY = FloatMath.sin(angle) * (350 + 4 * j);

							sBullet.initializeLinearBullet(mAssets.fireball, false, vX, vY,
									mPosition.x + mBitmap.getWidth() / 2,
									mPosition.y + mBitmap.getHeight() / 2 + (j * 6), 3000, 24, 1);
							mState.mBullets.addBullet(sBullet);
						}
					}
					mTimeUntilNextShot = 400;
					delta += (float) (Math.PI / 32);

				}
			}

		}

	}

	@Override
	public boolean isBoss() {
		return true;
	}

	@Override
	protected void updatePattern() {
		if (mBulletPhase == BulletPattern.Pattern1 && mHp < 0.8 * mStartingHp) {
			mBulletPhase = BulletPattern.Pattern2;
			mMovementPhase = MovementPattern.RESET;
			mSpeedCalculated = false;
			mTimeUntilNextShot = 0;
		}
	}

}
