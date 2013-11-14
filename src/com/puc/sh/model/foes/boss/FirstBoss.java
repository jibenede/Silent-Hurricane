package com.puc.sh.model.foes.boss;

import android.graphics.PointF;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.sh.model.bullets.patterns.BulletPattern;
import com.puc.sh.model.bullets.patterns.Pattern_K;
import com.puc.sh.model.bullets.patterns.Pattern_L;
import com.puc.sh.model.bullets.patterns.Pattern_M;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;

public class FirstBoss extends Foe {
    private enum MovementPattern {
        Arriving, Still
    }

    private final int SPEED = 100;

    private BulletPattern[] mBulletPatterns;
    private int mCurrentPattern;

    private MovementPattern mMovementPhase;
    private int mStartingHp;

    private long mTimeOfLastPatternChange;

    public FirstBoss(AuroraContext context, int hp) {
        super(context, hp, context.getAssets().komashr);

        mStartingHp = hp;
        mPosition = new PointF(Globals.CANVAS_WIDTH / 2 - mBitmap.getWidth()
                / 2, -mBitmap.getHeight());
        mMovementPhase = MovementPattern.Arriving;

        mBulletPatterns = new BulletPattern[] { new Pattern_K(this, context),
                new Pattern_L(this, context), new Pattern_M(this, context) };

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
        if (mMovementPhase == MovementPattern.Arriving) {
            mPosition.y += (interval / 1000.0) * SPEED;
            if (mPosition.y > 50) {
                mPosition.y = 50;
                mMovementPhase = MovementPattern.Still;
                mInvulnerable = false;
            }
        }

    }

    @Override
    public void fireBullets(long interval) {
        if (mTicks - mTimeOfLastPatternChange > 3000) {
            if (mMovementPhase != MovementPattern.Arriving) {
                mBulletPatterns[mCurrentPattern].update(interval);
            }
        }

    }

    @Override
    public boolean isBoss() {
        return true;
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
    protected void updatePattern() {
        if (mCurrentPattern == 0 && mHp < (2.0 / 3) * mStartingHp) {
            mCurrentPattern++;
            mTimeOfLastPatternChange = mTicks;
        } else if (mCurrentPattern == 1 && mHp < (1.0 / 3) * mStartingHp) {
            mCurrentPattern++;
            mTimeOfLastPatternChange = mTicks;
        }
    }

}
