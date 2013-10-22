package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.Utils;

public class Apollo extends Foe {
    private final int FIRING_INTERVAL_1 = 200;
    private final int FIRING_INTERVAL_2 = 600;
    private final int FIRING_INTERVAL_3 = 200;
    private final int SPEED = 500;

    private PointF mDestination;
    private float mSpeedX;
    private float mSpeedY;
    private boolean mInPosition;

    private long mTimeUntilNextShot1;
    private long mTimeUntilNextShot2;
    private long mTimeUntilNextShot3;

    private int mCounter1;
    private int mCounter2;

    public Apollo(AuroraContext context, int hp) {
        super(context, hp, context.getAssets().dart);

        mTimeUntilNextShot1 = FIRING_INTERVAL_1;
        mTimeUntilNextShot2 = FIRING_INTERVAL_2;
        mTimeUntilNextShot3 = FIRING_INTERVAL_3;
    }

    public void setPositions(float startX, float startY, float dstX, float dstY) {
        mPosition = new PointF(startX, startY);
        mDestination = new PointF(dstX, dstY);

        float vX = mDestination.x - mPosition.x;
        float vY = mDestination.y - mPosition.y;
        float factor = (float) (Math.sqrt(vX * vX + vY * vY) / SPEED);
        mSpeedX = vX / factor;
        mSpeedY = vY / factor;
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
        }

    }

    @Override
    public void fireBullets(long interval) {
        if (mInPosition) {
            mTimeUntilNextShot1 -= interval;
            if (mTimeUntilNextShot1 < 0) {
                double a = mCounter1 * Math.PI / 4;
                int posX = (int) (mPosition.x + mBitmap.getWidth() / 2 + 150
                        * Utils.sRandom.nextFloat() * Math.cos(a));
                int posY = (int) (mPosition.y + mBitmap.getHeight() / 2 + 150
                        * Utils.sRandom.nextFloat() * Math.sin(a));

                for (int i = 0; i < 16; i++) {
                    float angle = (float) (2 * Math.PI * (i / 16.f));

                    float vX = (float) (Math.cos(angle) * 300);
                    float vY = (float) (Math.sin(angle) * 300);

                    Bullet b = mContext.getState().mBullet;
                    b.initializeLinearBullet(mContext.getAssets().fireball,
                            false, (int) vX, (int) vY, posX, posY, 0, 24, 1);
                    mContext.getState().mEnemyBullets.addBullet(b);
                }

                mCounter1++;
                mTimeUntilNextShot1 = FIRING_INTERVAL_1;
            }

            mTimeUntilNextShot2 -= interval;
            if (mTimeUntilNextShot2 < 0) {
                float vX = mContext.getState().mShip.mShipPosition.x
                        - mPosition.x;
                float vY = mContext.getState().mShip.mShipPosition.y
                        - mPosition.y;
                float factor = (float) (Math.sqrt(vX * vX + vY * vY) / 300);
                if (factor != 0) {
                    vX /= factor;
                    vY /= factor;

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().blueBullet, false, (int) vX,
                            (int) vY,
                            mPosition.x + mBitmap.getWidth() / 2 - 48,
                            mPosition.y + mBitmap.getHeight() / 2 - 48, 6000,
                            96, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }

                mTimeUntilNextShot2 = FIRING_INTERVAL_2;
            }

            mTimeUntilNextShot3 -= interval;
            if (mTimeUntilNextShot3 < 0) {
                for (int i = 0; i < 8; i++) {
                    float angle = (float) (2 * Math.PI * (i / 8.f));
                    angle += mCounter2 * Math.PI / 64;

                    float vX = (float) (Math.cos(angle) * 300);
                    float vY = (float) (Math.sin(angle) * 300);

                    Bullet b = mContext.getState().mBullet;
                    b.initializeLinearBullet(mContext.getAssets().redBullet,
                            false, (int) vX, (int) vY,
                            mPosition.x + mBitmap.getWidth() / 2 - 18,
                            mPosition.y + mBitmap.getHeight() / 2 - 18, 0, 36,
                            1);
                    mContext.getState().mEnemyBullets.addBullet(b);
                }

                mCounter2++;
                mTimeUntilNextShot3 = FIRING_INTERVAL_3;
            }
        }

    }

    @Override
    public boolean holdsStage() {
        return true;
    }

}
