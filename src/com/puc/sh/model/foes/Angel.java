package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AuroraContext;

public class Angel extends Foe {
    private final double SQRT_2 = Math.sqrt(2);
    private final float ANGULAR_SPEED = (float) (Math.PI / 4);
    private final int AMPLITUDE = 200;
    private final int BULLET_SPEED = 500;
    private final int FIRING_INTERVAL = 200;

    private float t;

    private PointF mDestination;
    private float mSpeedX;
    private float mSpeedY;
    private boolean mInPosition;

    private long mTimeUntilNextShot;

    private int mCounter;

    private static final int SPEED = 500;

    public Angel(AuroraContext context, int hp) {
        super(context, hp, context.getAssets().dart);

        t = (float) (Math.PI / 2);
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
        return 0;
    }

    @Override
    public boolean isOnScreen() {
        return mHp > 0;
    }

    @Override
    protected void updatePosition(long interval) {
        if (!mInPosition) {
            mPosition.x += mSpeedX * (interval / 1000f);
            mPosition.y += mSpeedY * (interval / 1000f);

            if (CollisionUtils.squareDistance(mPosition.x, mPosition.y,
                    mDestination.x, mDestination.y) < 100) {
                mPosition.x = mDestination.x;
                mPosition.y = mDestination.y;
                mInPosition = true;
            }
        } else {
            t += ANGULAR_SPEED * (interval / 1000f);

            mPosition.x = (float) (mDestination.x + (AMPLITUDE * SQRT_2 * Math
                    .cos(t)) / (Math.sin(t) * Math.sin(t) + 1));
            mPosition.y = (float) (mDestination.y + (AMPLITUDE * SQRT_2
                    * Math.cos(t) * Math.sin(t))
                    / (Math.sin(t) * Math.sin(t) + 1));
        }

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            int temp = 8;
            for (int i = 1; i < temp; i++) {
                for (int j = 0; j < 4; j++) {
                    float angle = (float) (i * 2 * Math.PI / temp);
                    if (mCounter % 2 == 1) {
                        angle += (float) (Math.PI / temp);
                    }

                    float vX = (float) (Math.cos(angle) * BULLET_SPEED * (1 + j * 0.2));
                    float vY = (float) (Math.sin(angle) * BULLET_SPEED * (1 + j * 0.2));

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().yellowBullet24, false, (int) vX,
                            (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                            mPosition.y + mBitmap.getHeight(), 6000, 24, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }

            }
            mTimeUntilNextShot = FIRING_INTERVAL;
            mCounter++;
        }

    }

    @Override
    public boolean holdsStage() {
        return true;
    }

}
