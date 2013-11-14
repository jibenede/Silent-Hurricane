package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_K extends BulletPattern {
    private final int FIRING_INTERVAL = 200;

    private long mTimeUntilNextShot;
    private int mCounter;

    public Pattern_K(Foe foe, AuroraContext context) {
        super(foe, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 0; i < 24; i++) {
                float angle = (float) (2 * Math.PI * (i / 24.f));
                angle += mCounter * Math.PI / 64;

                float vX = (float) (Math.cos(angle) * 300);
                float vY = (float) (Math.sin(angle) * 300);

                Bullet b = mContext.getState().mBullet;
                b.initializeLinearBullet(mContext.getAssets().redBullet36, false,
                        (int) vX, (int) vY,
                        mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 18,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 18,
                        0, 36, 1);
                mContext.getState().mEnemyBullets.addBullet(b);
            }

            mCounter++;
            mTimeUntilNextShot = FIRING_INTERVAL;
        }

    }

}
