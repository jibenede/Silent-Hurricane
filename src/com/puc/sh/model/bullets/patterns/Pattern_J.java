package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Pattern_J extends BulletPattern {
    private final int BULLET_SPEED = 600;

    private long mTimeUntilNextShot1;
    private long mTimeUntilNextShot2;

    private int mCounter1;
    private int mOrigin;

    private int mCounter2;

    public Pattern_J(Foe foe, AuroraContext context) {
        super(foe, context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot1 -= interval;
        if (mTimeUntilNextShot1 < 0) {
            if (mOrigin % 3 == 0) {
                for (int i = 0; i < 40; i++) {
                    float angle = (float) (Math.PI / 4 + Utils.sRandom
                            .nextDouble() * Math.PI / 4);

                    float vX = (float) (Math.cos(angle) * BULLET_SPEED);
                    float vY = (float) (Math.sin(angle) * BULLET_SPEED);

                    mBullet.initializeCurvedBullet(
                            mContext.getAssets().yellowBullet24, vX, vY, -20
                                    + Utils.sRandom.nextInt(40), -150,
                            Utils.sRandom.nextInt(Globals.CANVAS_WIDTH), 0, 24,
                            1000);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            } else if (mOrigin % 3 == 1) {
                for (int i = 0; i < 40; i++) {
                    float angle = (float) (-Utils.sRandom.nextDouble()
                            * Math.PI / 4);

                    float vX = (float) (Math.cos(angle) * BULLET_SPEED);
                    float vY = (float) (Math.sin(angle) * BULLET_SPEED);

                    mBullet.initializeCurvedBullet(
                            mContext.getAssets().yellowBullet24, vX, vY, -220,
                            -40, 0,
                            Globals.CANVAS_HEIGHT - Utils.sRandom.nextInt(300),
                            24, 1000);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            } else {
                for (int i = 0; i < 40; i++) {
                    float angle = (float) (Math.PI + Utils.sRandom.nextDouble()
                            * Math.PI / 4);

                    float vX = (float) (Math.cos(angle) * BULLET_SPEED);
                    float vY = (float) (Math.sin(angle) * BULLET_SPEED);

                    mBullet.initializeCurvedBullet(
                            mContext.getAssets().yellowBullet24, vX, vY, 220,
                            -40, Globals.CANVAS_WIDTH - 5,
                            Globals.CANVAS_HEIGHT - Utils.sRandom.nextInt(300),
                            24, 1000);
                    mContext.getState().mEnemyBullets.addBullet(mBullet);
                }
            }

            mCounter1++;

            if (mCounter1 % 5 == 0) {
                mTimeUntilNextShot1 = 1300;
                mOrigin++;
            } else {
                mTimeUntilNextShot1 = 50;
            }

        }

        mTimeUntilNextShot2 -= interval;
        if (mTimeUntilNextShot2 < 0) {
            for (int i = 0; i < 20; i++) {
                double angle = 2 * Math.PI * (i / 20f);
                if (mCounter2 % 2 == 0) {
                    angle += Math.PI / 20;
                }

                mBullet.initializeLinearBullet(
                        mContext.getAssets().redBullet36, false,
                        (int) (Math.cos(angle) * 300),
                        (int) (Math.sin(angle) * 300), mFoe.mPosition.x
                                + mFoe.mBitmap.getWidth() / 2, mFoe.mPosition.y
                                + mFoe.mBitmap.getHeight(), 6000, 36, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }
            mTimeUntilNextShot2 = 1500;
            mCounter2++;
        }

    }
}
