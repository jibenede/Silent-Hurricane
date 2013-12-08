package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AuroraContext;

public class Mawler extends Foe {
    private final int SPEED = 300;
    private final long FIRING_INTERVAL = 2000;
    private final int BULLET_SPEED = 400;

    private PointF mDestination;
    private float mSpeedX;
    private float mSpeedY;
    private boolean mInPosition;

    private long mTimeUntilNextShot;
    private long mWait;

    public Mawler(AuroraContext context, int hp, long wait) {
        super(context, hp, context.getAssets().dart);
        mWait = wait;
    }

    public void setPositions(float startX, float startY, float dstX, float dstY) {
        mPosition = new PointF(startX, startY);
        mDestination = new PointF(dstX, dstY);

        float vX = mDestination.x - mPosition.x;
        float vY = mDestination.y - mPosition.y;
        float factor = (float) (Math.sqrt(vX * vX + vY * vY) / SPEED);
        mSpeedX = vX / factor;
        mSpeedY = vY / factor;

        mTimeUntilNextShot = 0;
    }

    @Override
    public float getAngle() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isOnScreen() {
        return mHp > 0;
    }

    @Override
    protected void updatePosition(long interval) {
        mWait -= interval;

        if (!mInPosition && mWait < 0) {
            mPosition.x += mSpeedX * (interval / 1000f);
            mPosition.y += mSpeedY * (interval / 1000f);

            if (CollisionUtils.squareDistance(mPosition.x, mPosition.y,
                    mDestination.x, mDestination.y) < 100) {
                mPosition.x = mDestination.x;
                mPosition.y = mDestination.y;
                mInPosition = true;
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

            for (int i = -3; i <= 3; i++) {
                for (int j = 0; j < 4; j++) {
                    double a = angle + Math.PI / 20 * i;

                    double vX = (1 + j * 0.2) * BULLET_SPEED * Math.cos(a);
                    double vY = (1 + j * 0.2) * BULLET_SPEED * Math.sin(a);

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().yellowBullet24, false, (int) vX,
                            (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                            mPosition.y + mBitmap.getHeight() / 2, 6000, 24, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }

            }

            mTimeUntilNextShot = FIRING_INTERVAL;
        }

    }

    @Override
    public boolean holdsStage() {
        return true;
    }

}
