package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Stingray extends Foe {
    public enum Direction {
        LEFT, RIGHT
    }

    private Direction mDirection;
    private long mTimeUntilNextShot;

    private static final int SPEED = 250;

    public Stingray(AuroraContext context, int hp, int yPosition,
            Direction direction) {
        super(context, hp, context.getAssets().stingray);

        mDirection = direction;

        if (direction == Direction.RIGHT) {
            mPosition = new PointF(-mBitmap.getWidth(), yPosition);
        } else {
            mPosition = new PointF(Globals.CANVAS_WIDTH, yPosition);
        }

        mTimeUntilNextShot = 500 + Utils.sRandom.nextInt(500);

    }

    @Override
    public float getAngle() {
        // TODO Auto-generated method stub
        return (float) (mDirection == Direction.LEFT ? -Math.PI / 2
                : Math.PI / 2);
    }

    @Override
    public boolean isOnScreen() {
        if (mDirection == Direction.RIGHT) {
            return mHp > 0 && mPosition.x < Globals.CANVAS_WIDTH;
        } else {
            return mHp > 0 && mPosition.x > -mBitmap.getWidth();
        }
    }

    @Override
    protected void updatePosition(long interval) {
        if (mDirection == Direction.RIGHT) {
            mPosition.x += SPEED * (interval / 1000f);
        } else {
            mPosition.x -= SPEED * (interval / 1000f);
        }

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            int random = 500 + Utils.sRandom.nextInt(1500);

            mBullet.initializeLinearClusterBullet(mContext.getAssets().laser2,
                    0, 400, mPosition.x + mBitmap.getWidth() / 2, mPosition.y
                            + mBitmap.getHeight() / 2, random, 16, 1);
            mContext.getState().mEnemyBullets.addBullet(mBullet);

            mTimeUntilNextShot = 500 + Utils.sRandom.nextInt(500);
        }

    }

}
