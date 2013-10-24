package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class Volt extends Foe {
    public enum Type {
        HORIZONTAL_LEFT, HORIZONTAL_RIGHT, VERTICAL
    }

    private final int FIRING_INTERVAL = 2000;
    private final int BULLET_SPEED = 400;

    private Type mType;
    private int mSpeed;
    private int mAccel;

    private long mTimeUntilNextShot;

    public Volt(AuroraContext context, int hp, int pos, int speed, int accel,
            Type type) {
        super(context, hp, context.getAssets().dart);

        mSpeed = speed;
        mAccel = accel;
        mType = type;

        if (mType == Type.VERTICAL) {
            mPosition = new PointF(pos + mBitmap.getWidth() / 2,
                    -mBitmap.getHeight());
        } else if (mType == Type.HORIZONTAL_RIGHT) {
            mPosition = new PointF(-mBitmap.getWidth(), pos
                    + mBitmap.getHeight() / 2);
        } else {
            mPosition = new PointF(Globals.CANVAS_WIDTH, pos
                    + mBitmap.getHeight() / 2);
        }

        mTimeUntilNextShot = 1000;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public boolean isOnScreen() {
        // TODO Auto-generated method stub
        return mHp > 0
                && mPosition.y > -mBitmap.getHeight() - 100
                && mPosition.x > -mBitmap.getWidth() - 100
                && mPosition.x < Globals.CANVAS_WIDTH + mBitmap.getWidth()
                        + 100;
    }

    @Override
    protected void updatePosition(long interval) {
        if (mType == Type.VERTICAL) {
            mPosition.y += mSpeed * (interval / 1000f);
            mSpeed += mAccel * (interval / 1000f);
            if (mSpeed < 0) {
                mSpeed = 0;
            }
        } else if (mType == Type.HORIZONTAL_RIGHT) {
            mPosition.x += mSpeed * (interval / 1000f);
            mSpeed += mAccel * (interval / 1000f);
            if (mSpeed < 0) {
                mSpeed = 0;
            }
        } else {
            mPosition.x -= mSpeed * (interval / 1000f);
            mSpeed += mAccel * (interval / 1000f);
            if (mSpeed < 0) {
                mSpeed = 0;
            }
        }

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            double deltaX = mContext.getState().mShip.mShipPosition.x
                    - mPosition.x;
            double deltaY = mContext.getState().mShip.mShipPosition.y
                    - mPosition.y;

            double angle = Math.atan2(deltaY, deltaX);

            for (int i = -4; i <= 4; i++) {

                double a = angle + Math.PI / 40 * i;

                double vX = BULLET_SPEED * Math.cos(a);
                double vY = BULLET_SPEED * Math.sin(a);

                mBullet.initializeLinearBullet(mContext.getAssets().fireball,
                        false, (int) vX, (int) vY,
                        mPosition.x + mBitmap.getWidth() / 2, mPosition.y
                                + mBitmap.getHeight() / 2, 6000, 24, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);

            }

            mTimeUntilNextShot = FIRING_INTERVAL;
        }

    }

}
