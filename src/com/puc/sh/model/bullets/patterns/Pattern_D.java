package com.puc.sh.model.bullets.patterns;

import android.graphics.Bitmap;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Pattern_D extends BulletPattern {

    private long mTimeUntilNextBurst;
    private long mTimeUntilNextShot;
    private int mCounter;

    private static final int SHOT_SPEED = 130;

    public Pattern_D(Foe foe, AuroraContext context) {
        super(foe, context);
        mTimeUntilNextBurst = 0;
        mTimeUntilNextShot = 0;
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextBurst -= interval;
        mTimeUntilNextShot -= interval;

        if (mTimeUntilNextBurst < 0) {
            Bitmap bmp = mContext.getAssets().redBullet36;
            for (int i = 0; i < 4; i++) {
                float angle = 2 * (float) Math.PI * (i / 4f);
                int mult = mCounter % 8;
                angle += mult * (float) Math.PI / 16;

                for (int j = 0; j < 5; j++) {
                    mBullet.initializeRadialBullet(bmp, 400,
                            (float) Math.PI / 8,
                            mFoe.mPosition.x + mFoe.mBitmap.getWidth() / 2
                                    - bmp.getWidth() / 2,
                            mFoe.mPosition.y + mFoe.mBitmap.getHeight() / 2
                                    - bmp.getHeight() / 2, 36 * j, angle, 36,
                            1, 3000);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }

            }
            mTimeUntilNextBurst = 500;
            mCounter++;
        }

        if (mTimeUntilNextShot < 0) {
            int random = Utils.sRandom.nextInt(100);
            float angle = (float) (Math.PI / 2 + (random / 100f)
                    * (Math.PI / 4));
            mBullet.initializeLinearBullet(mContext.getAssets().greenBullet12,
                    false, (float) (Math.cos(angle) * SHOT_SPEED),
                    (float) (Math.sin(angle) * SHOT_SPEED),
                    Globals.CANVAS_WIDTH - 10, -10, 0, 12, 1);
            mContext.getState().mEnemyBullets.addBullet(mBullet);

            random = Utils.sRandom.nextInt(100);
            angle = (float) (Math.PI / 2 - (random / 100f) * (Math.PI / 4));
            mBullet.initializeLinearBullet(mContext.getAssets().greenBullet12,
                    false, (float) (Math.cos(angle) * SHOT_SPEED),
                    (float) (Math.sin(angle) * SHOT_SPEED), -10, -10, 0, 12, 1);
            mContext.getState().mEnemyBullets.addBullet(mBullet);

            random = Utils.sRandom.nextInt(100);
            angle = (float) (Math.PI / 2 + (random / 100f) * (2 * Math.PI / 3));
            mBullet.initializeLinearBullet(mContext.getAssets().greenBullet12,
                    false, (float) (Math.cos(angle) * SHOT_SPEED),
                    (float) (Math.sin(angle) * SHOT_SPEED),
                    Globals.CANVAS_WIDTH - 10, Globals.CANVAS_HEIGHT - 250, 0,
                    12, 1);
            mContext.getState().mEnemyBullets.addBullet(mBullet);

            random = Utils.sRandom.nextInt(100);
            angle = (float) (Math.PI / 2 - (random / 100f) * (2 * Math.PI / 3));
            mBullet.initializeLinearBullet(mContext.getAssets().greenBullet12,
                    false, (float) (Math.cos(angle) * SHOT_SPEED),
                    (float) (Math.sin(angle) * SHOT_SPEED), -10,
                    Globals.CANVAS_HEIGHT - 250, 0, 12, 1);
            mContext.getState().mEnemyBullets.addBullet(mBullet);

            mTimeUntilNextShot = 200;
        }

    }

}
