package com.puc.sh.model.bullets.patterns;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.SpiralBullet;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.Utils;

public class Pattern_H extends BulletPattern {
    private long mTimeUntilNextShot1;
    private long mTimeUntilNextShot2;

    private double mCounter;

    public Pattern_H(Foe foe, AuroraContext context) {
        super(foe, context);
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot1 -= interval;
        if (mTimeUntilNextShot1 < 0) {
            float angle = (float) (Math.sin(mCounter) * Math.PI / 8);

            float vX = (float) (Math.cos(angle) * 400);
            float vY = (float) (Math.sin(angle) * 400);

            Bullet b = mContext.getState().mBullet;
            b.initializeLinearBullet(mContext.getAssets().yellowBullet24, false,
                    (int) vX, (int) vY,
                    mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 12,
                    mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 12, 0,
                    24, 1);
            mContext.getState().mEnemyBullets.addBullet(b);

            angle = (float) (-angle + Math.PI);

            vX = (float) (Math.cos(angle) * 400);
            vY = (float) (Math.sin(angle) * 400);

            b.initializeLinearBullet(mContext.getAssets().yellowBullet24, false,
                    (int) vX, (int) vY,
                    mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 12,
                    mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 12, 0,
                    24, 1);
            mContext.getState().mEnemyBullets.addBullet(b);

            mTimeUntilNextShot1 = 100;
            mCounter += 0.1;
        }

        mTimeUntilNextShot2 -= interval;
        if (mTimeUntilNextShot2 < 0) {
            float angle = (float) (Math.PI / 4 + Math.PI / 2
                    * Utils.sRandom.nextDouble());

            float vX = (float) (Math.cos(angle) * 100);
            float vY = (float) (Math.sin(angle) * 100);

            SpiralBullet bullet = new SpiralBullet(mContext, new PointF(
                    mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2 - 48,
                    mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2 - 48), vX,
                    vY);
            mContext.getState().mSpecialBullets.add(bullet);

            mTimeUntilNextShot2 = 2500;
        }
    }

}
