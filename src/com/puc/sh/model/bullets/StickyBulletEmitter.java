package com.puc.sh.model.bullets;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class StickyBulletEmitter extends BulletEmitter {
    private long mTimeUntilNextShot;
    private float mSpeedX;
    private float mSpeedY;

    public StickyBulletEmitter(AuroraContext context, PointF startingPosition,
            int size, float speedX, float speedY) {
        super(context, startingPosition, size, context.getAssets().blueBullet96);

        mSpeedX = speedX;
        mSpeedY = speedY;
    }

    public boolean isOnScreen() {
        return mPosition.x > -mBitmap.getWidth()
                && mPosition.x < Globals.CANVAS_WIDTH
                && mPosition.y > -mBitmap.getHeight()
                && mPosition.y < Globals.CANVAS_HEIGHT;
    }

    @Override
    public void updateMovement(long interval) {
        mPosition.x += mSpeedX * (interval / 1000f);
        mPosition.y += mSpeedY * (interval / 1000f);
    }

    @Override
    public void fireBullets(long interval) {
        int deltaX = -50 + Utils.sRandom.nextInt(100);
        int deltaY = -50 + Utils.sRandom.nextInt(100);

        float x = mPosition.x + 48 - 12 + deltaX;
        float y = mPosition.y + 48 - 12 + deltaY;

        Bullet b = mContext.getState().mBullet;
        b.initializeStickyBullet(mContext.getAssets().yellowBullet24, x, y, 3000,
                24);
        mContext.getState().mEnemyBullets.addBullet(b);
    }

}
