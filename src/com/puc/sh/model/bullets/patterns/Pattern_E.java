package com.puc.sh.model.bullets.patterns;

import android.util.Log;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_E extends BulletPattern {
    private final int BULLET_SPEED = 500;

    private long mTimeUntilNextShot;
    private int mCounter;

    private long tickCounter;

    public Pattern_E(Foe foe, AuroraContext context) {
        super(foe, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {

            long a = System.nanoTime();

            for (int i = 0; i < 8; i++) {
                for (int j = -8; j <= 8; j++) {
                    float angle = (float) (2 * Math.PI * (i / 8f) + (Math.PI / 8)
                            * (j / 16f));

                    if (mCounter % 2 == 0) {
                        angle += Math.PI / 8;
                    }

                    float vX = (float) (Math.cos(angle) * (BULLET_SPEED));
                    float vY = (float) (Math.sin(angle) * (BULLET_SPEED));

                    mBullet.initializeLinearBullet(
                            mContext.getAssets().redBullet36, false, vX, vY,
                            mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2,
                            mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2,
                            3000, 36, 1);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            }
            mTimeUntilNextShot = 300;
            mCounter++;

            tickCounter += System.nanoTime() - a;
            if (mCounter % 10 == 0) {
                Log.i("Game", "Time: " + ((double) tickCounter / mCounter));
            }
        }

    }

}
