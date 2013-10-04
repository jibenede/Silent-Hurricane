package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class Dart extends Foe {
    private PointF mDestination;
    private float mSpeedX;
    private float mSpeedY;
    private boolean mInPosition;

    private long mTimeUntilNextShot;
    private long mFiringInterval;
    private int fireCycle;

    private static final int SPEED = 500;

    public Dart(AuroraContext context, int hp) {
        super(context, hp);
        mBitmap = context.getAssets().dart;
    }

    public Dart(AuroraContext context, int hp, float startX, float startY,
            float dstX, float dstY) {
        super(context, hp);
        mBitmap = context.getAssets().dart;

        if (startX == Foe.HORIZONTAL_CENTER) {
            startX = Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth() / 2;
        }

        if (dstX == Foe.HORIZONTAL_CENTER) {
            dstX = Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth() / 2;
        }

        if (startY == Foe.VERTICAL_TOP) {
            startY = -mBitmap.getHeight();
        }

        mPosition = new PointF(startX, startY);
        mDestination = new PointF(dstX, dstY);

        float vX = mDestination.x - mPosition.x;
        float vY = mDestination.y - mPosition.y;
        float factor = (float) (Math.sqrt(vX * vX + vY * vY) / SPEED);
        mSpeedX = vX / factor;
        mSpeedY = vY / factor;

        mTimeUntilNextShot = 0;
        mFiringInterval = 2000;
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
        mFiringInterval = 2000;
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
    public boolean collidesWith(Bullet b) {
        return CollisionUtils.circleCollide(mPosition.x, mPosition.y,
                mBitmap.getWidth() / 2, b.mPosition.x, b.mPosition.y, b.mSize);
    }

    @Override
    public void fireBullets(long interval) {
        if (mInPosition) {
            mTimeUntilNextShot -= interval;
            if (mTimeUntilNextShot < 0) {
                for (int i = 0; i < 100; i++) {
                    float angle = (float) (2 * Math.PI * (i / 100f));
                    if (fireCycle % 2 == 0) {
                        angle += (float) (Math.PI / 100);
                    }

                    float vX = (float) (Math.cos(angle) * 150);
                    float vY = (float) (Math.sin(angle) * 150);

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().fireball, false, (int) vX,
                            (int) vY,
                            mPosition.x + mBitmap.getWidth() / 2 - 12,
                            mPosition.y + mBitmap.getHeight(), 6000, 24, 1);

                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
                mTimeUntilNextShot = mFiringInterval;
                if (mFiringInterval > 1000) {
                    mFiringInterval -= 100;
                }
                fireCycle++;

            }
        }
    }

    @Override
    public boolean holdsStage() {
        return true;
    }

}
