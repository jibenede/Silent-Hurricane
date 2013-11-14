package com.puc.sh.model.bullets.patterns;

import android.graphics.PointF;

import com.puc.sh.model.bullets.StickyBulletEmitter;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Pattern_I extends BulletPattern {
    private final int BULLET_SPEED = 1200;

    private long mTimeUntilNextShot;
    private long mFiringInterval;

    public Pattern_I(Foe foe, AuroraContext context) {
        super(foe, context);

        mFiringInterval = 3000;
    }

    @Override
    public void update(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            int x = Utils.sRandom.nextInt(Globals.CANVAS_WIDTH);

            float vX = mContext.getState().mShip.mShipPosition.x - x;
            float vY = mContext.getState().mShip.mShipPosition.y;
            float factor = (float) (Math.sqrt(vX * vX + vY * vY) / BULLET_SPEED);
            if (factor != 0) {
                vX /= factor;
                vY /= factor;

                StickyBulletEmitter bullet = new StickyBulletEmitter(mContext,
                        new PointF(x, 0), 90, vX, vY);
                mContext.getState().mSpecialBullets.add(bullet);
            }

            mTimeUntilNextShot = mFiringInterval;
            if (mFiringInterval > 1500) {
                mFiringInterval -= 250;
            }
        }

    }

}
