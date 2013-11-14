package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class StalkerA extends Foe {
    public enum Origin {
        BOTTOM_LEFT, BOTTOM_RIGHT
    }

    private enum State {
        GOING_UP, ROTATING, GOING_DOWN
    }

    private State mPhase;
    private float mAngle;
    private Origin mOrigin;
    private float mShipRadius;

    private final int PADDING = 50;
    private final int SPEED = 600;

    private final int FIRING_INTERVAL = 500;
    private final int BULLET_SPEED = 300;

    private int mRadius;

    private long mTimeUntilNextShot;

    public StalkerA(AuroraContext context, int hp, Origin origin) {
        super(context, hp, context.getAssets().stalkerA);

        if (origin == Origin.BOTTOM_LEFT) {
            mPosition = new PointF(PADDING, Globals.CANVAS_HEIGHT + PADDING);
        } else if (origin == Origin.BOTTOM_RIGHT) {
            mPosition = new PointF(Globals.CANVAS_WIDTH - PADDING,
                    Globals.CANVAS_HEIGHT + PADDING);
        }
        mPhase = State.GOING_UP;
        mRadius = Globals.CANVAS_WIDTH / 2 - 50;
        mAngle = 0;
        mOrigin = origin;

        mTimeUntilNextShot = 1000;
        mShipRadius = mBitmap.getWidth() / 2;
    }

    @Override
    public float getAngle() {
        return mAngle;
    }

    @Override
    public boolean isOnScreen() {
        return mHp > 0
                && !(mPhase == State.GOING_DOWN && mPosition.y > Globals.CANVAS_HEIGHT + 100);
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

            if (mOrigin == Origin.BOTTOM_LEFT && mAngle > Math.PI
                    || mOrigin == Origin.BOTTOM_RIGHT && mAngle < -Math.PI) {
                mAngle = (float) Math.PI;
                mPhase = State.GOING_DOWN;
            }

            if (mOrigin == Origin.BOTTOM_LEFT) {
                mPosition.x = (float) (-Math.cos(mAngle) * mRadius + Globals.CANVAS_WIDTH / 2);
                mPosition.y = (float) ((PADDING + mRadius) - Math.sin(mAngle)
                        * mRadius);
            } else if (mOrigin == Origin.BOTTOM_RIGHT) {
                mPosition.x = (float) (Math.cos(mAngle) * mRadius + Globals.CANVAS_WIDTH / 2);
                mPosition.y = (float) ((PADDING + mRadius) + Math.sin(mAngle)
                        * mRadius);
            }

        } else if (mPhase == State.GOING_DOWN) {
            mPosition.y += (SPEED * interval) / 1000;
        }

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            float vX = mContext.getState().mShip.mShipPosition.x - mPosition.x;
            float vY = mContext.getState().mShip.mShipPosition.y - mPosition.y;
            float factor = (float) (Math.sqrt(vX * vX + vY * vY) / BULLET_SPEED);
            if (factor != 0) {
                vX /= factor;
                vY /= factor;

                mBullet.initializeLinearBullet(
                        mContext.getAssets().redBullet12, false, (int) vX,
                        (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                        mPosition.y + mBitmap.getHeight() / 2, 6000, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            mTimeUntilNextShot = FIRING_INTERVAL;
        }
    }

}
