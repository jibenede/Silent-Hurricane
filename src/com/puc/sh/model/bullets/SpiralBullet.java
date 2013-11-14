package com.puc.sh.model.bullets;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class SpiralBullet extends BulletEmitter {
    private float mSpeedX;
    private float mSpeedY;

    private long mTimeUntilNextShot;
    private int mCounter;

    public SpiralBullet(AuroraContext context, PointF startingPosition,
            float speedX, float speedY) {
        super(context, startingPosition, 90, context.getAssets().blueBullet96);

        mSpeedX = speedX;
        mSpeedY = speedY;
    }

    public boolean isOnScreen() {
        return mPosition.x > -mBitmap.getWidth()
                && mPosition.x < Globals.CANVAS_WIDTH
                && mPosition.y > -mBitmap.getHeight()
                && mPosition.y < Globals.CANVAS_HEIGHT;
    }

    @Override
    public void updateMovement(long interval) {
        mPosition.x += mSpeedX * (interval / 1000f);
        mPosition.y += mSpeedY * (interval / 1000f);
    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 0; i < 8; i++) {
                float angle = (float) (2 * Math.PI * (i / 8.f));
                angle += mCounter * Math.PI / 64;

                float vX = (float) (Math.cos(angle) * 250);
                float vY = (float) (Math.sin(angle) * 250);

                Bullet b = mContext.getState().mBullet;
                b.initializeLinearBullet(mContext.getAssets().redBullet36, false,
                        (int) vX, (int) vY, mPosition.x + mBitmap.getWidth()
                                / 2 - 18, mPosition.y + mBitmap.getHeight() / 2
                                - 18, 0, 36, 1);
                mContext.getState().mEnemyBullets.addBullet(b);
            }

            mCounter++;

            mTimeUntilNextShot = 400;
        }

    }

}
