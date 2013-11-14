package com.puc.sh.model.foes.boss;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.sh.model.bullets.patterns.BulletPattern;
import com.puc.sh.model.bullets.patterns.Pattern_E;
import com.puc.sh.model.bullets.patterns.Pattern_F;
import com.puc.sh.model.bullets.patterns.Pattern_G;
import com.puc.sh.model.bullets.patterns.Pattern_H;
import com.puc.sh.model.bullets.patterns.Pattern_I;
import com.puc.sh.model.bullets.patterns.Pattern_J;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class Jawus extends Foe {
    private BulletPattern[] mBulletPatterns;
    private int mCurrentPattern;

    private enum MovementPattern {
        ARRIVING, STILL, RESET
    }

    private int mStartingHp;

    private MovementPattern mMovementPhase;

    private final int SPEED = 100;

    private long mTimeOfLastPatternChange;

    public Jawus(AuroraContext context, int hp) {
        super(context, hp, context.getAssets().jawus);
        mStartingHp = hp;

        mPosition = new PointF(Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth()
                / 2, -mBitmap.getHeight());
        mMovementPhase = MovementPattern.ARRIVING;

        mBulletPatterns = new BulletPattern[] { new Pattern_E(this, context),
                new Pattern_F(this, context), new Pattern_G(this, context),
                new Pattern_H(this, context), new Pattern_I(this, context),
                new Pattern_J(this, context) };
        mCurrentPattern = 0;

        mStartingHp = hp;
        mInvulnerable = true;
    }

    @Override
    public float getAngle() {
        // TODO Auto-generated method stub
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
                mMovementPhase = MovementPattern.STILL;
                mInvulnerable = false;
            }
        }

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
    public boolean collidesWith(Bullet b) {
        return CollisionUtils
                .rectCollide(mPosition.y, mPosition.x + mBitmap.getWidth(),
                        mPosition.y + mBitmap.getHeight(), mPosition.x,
                        b.mPosition.y, b.mPosition.x + b.mSize, b.mPosition.y
                                + b.mSize, b.mPosition.x);
    }

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    protected void updatePattern() {
        if (mCurrentPattern == 0 && mHp < (92.0 / 100) * mStartingHp) {
            mCurrentPattern++;
            // mMovementPhase = MovementPattern.RESET;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 1 && mHp < (80.0 / 100) * mStartingHp) {
            mCurrentPattern++;
            // mMovementPhase = MovementPattern.RESET;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 2 && mHp < (60.0 / 100) * mStartingHp) {
            mCurrentPattern++;
            // mMovementPhase = MovementPattern.RESET;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 3 && mHp < (40.0 / 100) * mStartingHp) {
            mCurrentPattern++;
            // mMovementPhase = MovementPattern.RESET;

            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 4 && mHp < (25.0 / 100) * mStartingHp) {
            mCurrentPattern++;
            // mMovementPhase = MovementPattern.RESET;

            mTimeOfLastPatternChange = mTicks;
        }
    }

}
