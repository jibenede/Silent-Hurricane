package com.puc.sh.model.bullets.patterns;

import android.graphics.Bitmap;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_C extends BulletPattern {

    private long mTimeUntilNextShot;
    private int mCounter;

    public Pattern_C(Foe foe, AuroraContext context) {
        super(foe, context);
        mTimeUntilNextShot = 500;
        mCounter = 0;
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            for (int i = 0; i < 40; i++) {
                double angle = 2 * Math.PI * (i / 40f);
                if (mCounter % 2 == 0) {
                    angle += Math.PI / 40;
                }

                Bitmap bitmap = mContext.getAssets().blueBullet;
                mBullet.initializeLinearBullet(
                        bitmap,
                        false,
                        (int) (Math.cos(angle) * 200),
                        (int) (Math.sin(angle) * 200),
                        mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2
                                - bitmap.getWidth() / 2,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2
                                - bitmap.getHeight() / 2, 6000, 88, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }
            mTimeUntilNextShot = 500;
            mCounter++;
        }

    }

}
