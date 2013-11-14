package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class PredatorA extends Foe {
    private float mShipRadius;

    private final int SPEED = 250;

    private final int FIRING_INTERVAL = 500;
    private final int BULLET_SPEED = 500;
    private long mTimeUntilNextShot;

    public PredatorA(AuroraContext context, int hp, int xPosition) {
        super(context, hp, context.getAssets().predatorA);

        mPosition = new PointF(xPosition - mBitmap.getWidth() / 2,
                -mBitmap.getHeight());
        mShipRadius = mBitmap.getWidth() / 2;

        mTimeUntilNextShot = 500;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public boolean isOnScreen() {
        return mHp > 0 && mPosition.y < Globals.CANVAS_HEIGHT;
    }

    @Override
    protected void updatePosition(long interval) {
        mPosition.y += SPEED * (interval / 1000.0);

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 1; i < 10; i++) {
                float angle = (float) (i * Math.PI / 10);

                float vX = (float) (Math.cos(angle) * BULLET_SPEED);
                float vY = (float) (Math.sin(angle) * BULLET_SPEED);

                mBullet.initializeLinearBullet(
                        mContext.getAssets().blueBullet12, false, (int) vX,
                        (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                        mPosition.y + mBitmap.getHeight(), 6000, 12, 1);

                // sBullet.initializeBullet(false, (int) vX, (int) vY,
                // mPosition.x + mBitmap.getWidth() / 2, mPosition.y +
                // mBitmap.getHeight(), 6000,
                // BulletType.Laser1, 12, 12);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }
            mTimeUntilNextShot = FIRING_INTERVAL;
        }
    }

}
