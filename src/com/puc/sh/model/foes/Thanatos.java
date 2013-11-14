package com.puc.sh.model.foes;

import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class Thanatos extends Foe {
    public enum Direction {
        RIGHT, LEFT
    }

    private enum Phase {
        MOVING_SIDEWAYS_1, LOOPING, MOVING_SIDEWAYS_2
    }

    private final int BULLET_SPEED = 400;
    private final long FIRING_INTERVAL = 1500;
    private final int SPEED = 300;
    private final int RADIUS = 250;

    private Phase mPhase;
    private Direction mDirection;
    private int mStartY;
    private float mStartAngle;
    private int mCounter;

    private float mAngle;
    private long mTimeUntilNextShot = 1000;

    public Thanatos(AuroraContext context, int hp, Direction direction, int yPos) {
        super(context, hp, context.getAssets().dart);

        mDirection = direction;
        mPhase = Phase.MOVING_SIDEWAYS_1;

        mStartY = yPos;
        if (direction == Direction.RIGHT) {
            mPosition = new PointF(-mBitmap.getWidth(), yPos);
            mStartAngle = mAngle = (float) (Math.PI / 2);
        } else {
            mPosition = new PointF(Globals.CANVAS_WIDTH, yPos);
            mStartAngle = mAngle = (float) (-Math.PI / 2);
        }
    }

    @Override
    public float getAngle() {
        return -mAngle;
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
        if (mPhase == Phase.MOVING_SIDEWAYS_1
                || mPhase == Phase.MOVING_SIDEWAYS_2) {
            if (mDirection == Direction.RIGHT) {
                mPosition.x += SPEED * (interval / 1000f);

                if (mPhase == Phase.MOVING_SIDEWAYS_1
                        && mPosition.x >= Globals.CANVAS_WIDTH / 2
                                - mBitmap.getWidth() / 2) {
                    mPosition.x = Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth()
                            / 2;
                    mPhase = Phase.LOOPING;
                }
            } else {
                mPosition.x -= SPEED * (interval / 1000f);

                if (mPhase == Phase.MOVING_SIDEWAYS_1
                        && mPosition.x <= Globals.CANVAS_WIDTH / 2
                                - mBitmap.getWidth() / 2) {
                    mPosition.x = Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth()
                            / 2;
                    mPhase = Phase.LOOPING;
                }
            }

        } else {
            if (mDirection == Direction.RIGHT) {
                mAngle += ((float) SPEED / RADIUS) * (interval / 1000.0);
                if (mAngle > mStartAngle + 2 * Math.PI) {
                    mAngle = mStartAngle;
                    mPhase = Phase.MOVING_SIDEWAYS_2;
                }
            } else {
                mAngle -= ((float) SPEED / RADIUS) * (interval / 1000.0);
                if (mAngle < mStartAngle - 2 * Math.PI) {
                    mAngle = mStartAngle;
                    mPhase = Phase.MOVING_SIDEWAYS_2;
                }
            }

            mPosition.x = (float) (-Math.cos(mAngle) * RADIUS
                    + Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth() / 2);
            mPosition.y = (float) (mStartY - RADIUS + RADIUS * Math.sin(mAngle));
        }

    }

    @Override
    public void fireBullets(long interval) {
        mTimeUntilNextShot -= interval;
        if (mTimeUntilNextShot < 0) {
            double deltaX = mContext.getState().mShip.mShipPosition.x
                    - mPosition.x;
            double deltaY = mContext.getState().mShip.mShipPosition.y
                    - mPosition.y;

            double angle = Math.atan2(deltaY, deltaX);

            for (int i = -1; i <= 1; i++) {
                double a = angle + Math.PI / 20 * i;

                double vX = BULLET_SPEED * Math.cos(a);
                double vY = BULLET_SPEED * Math.sin(a);

                mBullet.initializeLinearBullet(mContext.getAssets().redBullet36,
                        false, (int) vX, (int) vY,
                        mPosition.x + mBitmap.getWidth() / 2, mPosition.y
                                + mBitmap.getHeight() / 2, 6000, 36, 1);
                mContext.getState().mEnemyBullets.addBullet(mBullet);
            }

            mCounter++;
            if (mCounter % 5 == 0) {
                mTimeUntilNextShot = FIRING_INTERVAL;
            } else {
                mTimeUntilNextShot = 200;
            }

        }
    }

}
