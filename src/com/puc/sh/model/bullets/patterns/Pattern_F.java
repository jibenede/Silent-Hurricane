package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.Utils;

public class Pattern_F extends BulletPattern {
    private final int BULLET_SPEED = 400;

    private long mTimeUntilNextShot;

    public Pattern_F(Foe foe, AuroraContext context) {
        super(foe, context);
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            int safePosition = 2 + Utils.sRandom.nextInt(14);
            for (int i = 0; i < 18; i++) {
                if (i != safePosition) {
                    mBullet.initializeLinearBullet(
                            mContext.getAssets().whiteBullet48, false, 0,
                            BULLET_SPEED, i * 40, 0, 0, 48, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            }
            mTimeUntilNextShot = 800;
        }
    }

}
