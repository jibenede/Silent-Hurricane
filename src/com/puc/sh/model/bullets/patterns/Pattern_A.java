package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_A extends BulletPattern {
    private long mTimeUntilNextShot;
    private float mDelta;

    public Pattern_A(Foe foe, AuroraContext context) {
        super(foe, context);
        mTimeUntilNextShot = 0;
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 16; j++) {
                    float angle = (float) (2 * Math.PI * (i / 8f)
                            + (Math.PI / 8) * (j / 16f) + mDelta);

                    float vX = (float) (Math.cos(angle) * (350 + 4 * j));
                    float vY = (float) (Math.sin(angle) * (350 + 4 * j));

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().yellowBullet24, false, vX, vY,
                            mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2,
                            mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2
                                    + (j * 3), 3000, 24, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 16; j++) {
                    float angle = (float) (2 * Math.PI * ((7 - i) / 8f)
                            - (Math.PI / 8) * (j / 16f) + Math.PI / 8);

                    float vX = (float) (Math.cos(angle) * (350 + 4 * j));
                    float vY = (float) (Math.sin(angle) * (350 + 4 * j));

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().yellowBullet24, false, vX, vY,
                            mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2,
                            mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2
                                    + (j * 6), 3000, 24, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            }
            mTimeUntilNextShot = 400;
            mDelta += (float) (Math.PI / 32);
        }
    }

}
