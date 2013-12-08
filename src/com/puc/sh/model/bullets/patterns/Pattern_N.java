package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public class Pattern_N extends BulletPattern {
    private final long FIRING_INTERVAL_1 = 50;
    private final long FIRING_INTERVAL_2 = 300;

    private long mTimeUntilNextShot1;
    private float mCounter1;

    private long mTimeUntilNextShot2;
    private float mCounter2;

    public Pattern_N(Foe foe, AuroraContext context) {
        super(foe, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot1 -= interval;
        if (mTimeUntilNextShot1 < 0) {
            for (int i = 0; i < 6; i++) {

                float angle = (float) (2 * Math.PI * (i / 6.f));
                angle += Math.sin(mCounter1) * Math.PI / 6;

                float vX = (float) (Math.cos(angle) * 150);
                float vY = (float) (Math.sin(angle) * 150);

                Bullet b = mContext.getState().mBullet;
                b.initializeCurvedBullet(mContext.getAssets().yellowBullet24,
                        vX, vY, 0, 50,
                        mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 12,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 12,
                        24, 6000);
                mContext.getState().mEnemyBullets.addBullet(b);
            }

            mCounter1 += 0.1;
            mTimeUntilNextShot1 = FIRING_INTERVAL_1;
        }

        mTimeUntilNextShot2 -= interval;
        if (mTimeUntilNextShot2 < 0) {
            for (int i = 0; i < 12; i++) {

                float angle = (float) (2 * Math.PI * (i / 12.f));
                angle += mCounter2 * Math.PI / 24;

                float vX = (float) (Math.cos(angle) * 50);
                float vY = (float) (Math.sin(angle) * 50);

                Bullet b = mContext.getState().mBullet;
                b.initializeCurvedBullet(mContext.getAssets().blueBullet12, vX,
                        vY, 0, 75, mFoe.mPosition.x + mFoe.mBitmap.getWidth()
                                / 2 - 6,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 6,
                        12, 2000);
                mContext.getState().mEnemyBullets.addBullet(b);

                b = mContext.getState().mBullet;
                b.initializeCurvedBullet(mContext.getAssets().blueBullet12,
                        vX * 2, vY * 2, 0, 75,
                        mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 6,
                        mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 6,
                        12, 2000);
                mContext.getState().mEnemyBullets.addBullet(b);
            }

            mCounter2++;
            mTimeUntilNextShot2 = FIRING_INTERVAL_2;
        }

    }

}
