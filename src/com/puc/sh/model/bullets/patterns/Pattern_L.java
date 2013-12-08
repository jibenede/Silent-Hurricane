package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class Pattern_L extends BulletPattern {
    private final int BULLET_SPEED = 50;

    private long mTimeUntilNextShot1;
    private long mTimeUntilNextShot2;

    private int mCounter;

    public Pattern_L(Foe foe, AuroraContext context) {
        super(foe, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot1 -= interval;
        if (mTimeUntilNextShot1 < 0) {
            for (int i = 0; i < Globals.CANVAS_WIDTH; i += 60) {
                mBullet.initializeLinearBullet(
                        mContext.getAssets().greenBullet12, false, 0,
                        BULLET_SPEED, i, 0, 0, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            int h = Globals.CANVAS_WIDTH / 2;

            for (int i = 0; i < h; i += 100) {
                mBullet.initializeLinearBullet(
                        mContext.getAssets().greenBullet12, false, BULLET_SPEED,
                        -BULLET_SPEED, i, Globals.CANVAS_HEIGHT, 0, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);

                mBullet.initializeLinearBullet(
                        mContext.getAssets().greenBullet12, false, BULLET_SPEED,
                        -BULLET_SPEED, 0, Globals.CANVAS_HEIGHT - i, 0, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            for (int i = 0; i < h; i += 100) {
                mBullet.initializeLinearBullet(
                        mContext.getAssets().greenBullet12, false, -BULLET_SPEED,
                        -BULLET_SPEED, Globals.CANVAS_WIDTH - i,
                        Globals.CANVAS_HEIGHT, 0, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);

                mBullet.initializeLinearBullet(
                        mContext.getAssets().greenBullet12, false, -BULLET_SPEED,
                        -BULLET_SPEED, Globals.CANVAS_WIDTH,
                        Globals.CANVAS_HEIGHT - i, 0, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            mTimeUntilNextShot1 = 1200;
        }

        mTimeUntilNextShot2 -= interval;
        if (mTimeUntilNextShot2 < 0) {
            float vX = mContext.getState().mShip.mShipPosition.x
                    + mContext.getState().mShip.mBitmap.getWidth() / 2
                    - mFoe.mPosition.x - mFoe.mBitmap.getWidth() / 2;
            float vY = mContext.getState().mShip.mShipPosition.y
                    + mContext.getState().mShip.mBitmap.getHeight() / 2
                    - mFoe.mPosition.y - mFoe.mBitmap.getHeight() / 2;
            ;
            float factor = (float) (Math.sqrt(vX * vX + vY * vY) / 150);
            if (factor != 0) {
                vX /= factor;
                vY /= factor;

                mBullet.initializeLinearBullet(mContext.getAssets().blueBullet96,
                        false, (int) vX, (int) vY, mFoe.mPosition.x
                                + mFoe.mBitmap.getWidth() / 2 - 48,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 48,
                        6000, 96, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            mTimeUntilNextShot2 = 3000;
        }

    }

}
