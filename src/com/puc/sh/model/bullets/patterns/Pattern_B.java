package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_B extends BulletPattern {
    private long mTimeUntilNextShot;

    public Pattern_B(Foe foe, AuroraContext context) {
        super(foe, context);
        mTimeUntilNextShot = 0;
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 0; i < 40; i++) {
                double angle = 2 * Math.PI * (i / 40f);

                mBullet.initializeLinearBullet(mContext.getAssets().greenBullet12,
                        false, (int) (Math.cos(angle) * 200),
                        (int) (Math.sin(angle) * 200), mFoe.mPosition.x
                                + mFoe.mBitmap.getWidth() / 2, mFoe.mPosition.y
                                + mFoe.mBitmap.getHeight(), 6000, 12, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }
            mTimeUntilNextShot = 500;
        }
    }
}
