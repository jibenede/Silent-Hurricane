package com.puc.sh.model.foes.boss;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.sh.model.bullets.patterns.BulletPattern;
import com.puc.sh.model.bullets.patterns.Pattern_A;
import com.puc.sh.model.bullets.patterns.Pattern_B;
import com.puc.sh.model.bullets.patterns.Pattern_C;
import com.puc.sh.model.bullets.patterns.Pattern_D;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class BossA extends Foe {
    private BulletPattern[] mBulletPatterns;
    private int mCurrentPattern;

    private enum MovementPattern {
        ARRIVING, HOLDING, GOING_LEFT, GOING_RIGHT, GOING_UP, RESET, STILL
    }

    private float mShipRadius;

    private int mStartingHp;

    private MovementPattern mMovementPhase;

    private final int SPEED = 100;

    private float mSpeedX;
    private float mSpeedY;

    private boolean mSpeedCalculated;

    private long mTimeOfLastPatternChange;

    private float resetX;
    private float resetY;

    public BossA(AuroraContext context, int hp) {
        super(context, hp, context.getAssets().boss);
        mStartingHp = hp;

        mShipRadius = mBitmap.getWidth() / 2;

        mPosition = new PointF(Globals.CANVAS_WIDTH / 2 - mShipRadius,
                -mBitmap.getHeight());
        mMovementPhase = MovementPattern.ARRIVING;

        mBulletPatterns = new BulletPattern[] { new Pattern_B(this, mContext),
                new Pattern_A(this, mContext), new Pattern_D(this, mContext),
                new Pattern_C(this, mContext) };
        mCurrentPattern = 0;

        resetX = mPosition.x;
        resetY = 50;
        mInvulnerable = true;
    }

    @Override
    public float getAngle() {
        return 0;
    }

    @Override
    public boolean isOnScreen() {
        return true;
    }

    @Override
    protected void updatePosition(long interval) {
        if (mMovementPhase == MovementPattern.ARRIVING) {
            mPosition.y += (interval / 1000.0) * SPEED;
            if (mPosition.y > 50) {
                mPosition.y = 50;
                mMovementPhase = MovementPattern.GOING_LEFT;
                mInvulnerable = false;
            }
        } else if (mMovementPhase == MovementPattern.GOING_LEFT) {
            mPosition.x -= (interval / 1000.0) * (SPEED / 2);
            if (mPosition.x < 100) {
                mPosition.x = 100;
                mMovementPhase = MovementPattern.GOING_RIGHT;
            }
        } else if (mMovementPhase == MovementPattern.GOING_RIGHT) {
            mPosition.x += (interval / 1000.0) * (SPEED / 2);
            if (mPosition.x + mBitmap.getWidth() > Globals.CANVAS_WIDTH - 100) {
                mPosition.x = Globals.CANVAS_WIDTH - 100 - mBitmap.getWidth();
                mMovementPhase = MovementPattern.GOING_LEFT;
            }
        } else if (mMovementPhase == MovementPattern.GOING_UP) {
            mPosition.y -= (interval / 1000.0) * (SPEED / 2);
            if (mPosition.y < -mBitmap.getHeight() / 2) {
                mPosition.y = -mBitmap.getHeight() / 2;
                mMovementPhase = MovementPattern.STILL;
            }
        } else if (mMovementPhase == MovementPattern.RESET) {
            if (!mSpeedCalculated) {
                float vX = resetX - mPosition.x;
                float vY = resetY - mPosition.y;
                float factor = (float) (Math.sqrt(vX * vX + vY * vY) / SPEED);
                if (factor != 0) {
                    mSpeedX = vX / factor;
                    mSpeedY = vY / factor;
                } else {
                    mSpeedX = mSpeedY = 0;
                }

                mSpeedCalculated = true;
            }

            mPosition.x += (interval / 1000.0) * mSpeedX;
            mPosition.y += (interval / 1000.0) * mSpeedY;

            float d = CollisionUtils.squareDistance(mPosition.x, mPosition.y,
                    resetX, resetY);
            if (d < 15) {
                mPosition.x = resetX;
                mPosition.y = resetY;

                if (mCurrentPattern == 2) {

                } else {
                    mMovementPhase = MovementPattern.STILL;
                }
            }

        }

    }

    @Override
    public boolean collidesWith(Bullet b) {
        return CollisionUtils
                .rectCollide(mPosition.y, mPosition.x + mBitmap.getWidth(),
                        mPosition.y + mBitmap.getHeight(), mPosition.x,
                        b.mPosition.y, b.mPosition.x + b.mSize, b.mPosition.y
                                + b.mSize, b.mPosition.x);
    }

    @Override
    public void fireBullets(long interval) {
        if (mTicks - mTimeOfLastPatternChange > 3000) {

            if (mMovementPhase != MovementPattern.ARRIVING
                    && mMovementPhase != MovementPattern.RESET) {
                mBulletPatterns[mCurrentPattern].update(interval);
            }

        }

    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    protected void updatePattern() {
        if (mCurrentPattern == 0 && mHp < 0.75 * mStartingHp) {
            mCurrentPattern++;
            mMovementPhase = MovementPattern.RESET;
            mSpeedCalculated = false;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 1 && mHp < 0.5 * mStartingHp) {
            mCurrentPattern++;
            mMovementPhase = MovementPattern.GOING_UP;
            mSpeedCalculated = false;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 2 && mHp < 0.25 * mStartingHp) {
            mCurrentPattern++;
            mMovementPhase = MovementPattern.RESET;
            mSpeedCalculated = false;

            mTimeOfLastPatternChange = mTicks;
        }
    }

}
