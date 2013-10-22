package com.puc.sh.model.foes;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.sh.model.Renderable;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.BulletArray;

public abstract class Foe implements Renderable {

    protected AuroraContext mContext;
    public int mHp;
    public int mOriginalHp;

    public PointF mPosition;
    public Bitmap mBitmap;

    protected Bullet mBullet;
    protected boolean mInvulnerable;
    protected long mTicks;

    public Foe(AuroraContext context, int hp, Bitmap bitmap) {
        mContext = context;
        mHp = mOriginalHp = hp;
        mBullet = new Bullet(context);
        mBitmap = bitmap;
    }

    public void update(long interval) {
        mTicks += interval;
        if (isOnScreen()) {
            updatePattern();
            updatePosition(interval);
            fireBullets(interval);
            hitTest();

        }
    }

    public abstract float getAngle();

    public abstract boolean isOnScreen();

    protected abstract void updatePosition(long interval);

    protected void hitTest() {
        BulletArray bullets = mContext.getState().mPlayerBullets;
        Bullet b;
        for (int i = 0; i < bullets.size(); i++) {
            b = bullets.getBullet(i);

            if (b.mDisplay && collidesWith(b)) {
                if (!mInvulnerable) {
                    mHp -= b.mFirepower;
                    mContext.getState().mScore += b.mFirepower
                            * mContext.getState().mShip.mLives * 100;
                }
                b.mDisplay = false;
                if (mHp <= 0 && holdsStage()) {
                    mContext.getState().getCurrentStage().unholdTimer();
                    onDestroyed();
                    break;
                }
            }
        }
    }

    public boolean collidesWith(Bullet b) {
        return CollisionUtils.circleCollide(mPosition.x, mPosition.y,
                mBitmap.getWidth() / 2, b.mPosition.x, b.mPosition.y,
                b.mSize / 2);
    }

    public abstract void fireBullets(long interval);

    public boolean isBoss() {
        return false;
    }

    public boolean holdsStage() {
        return false;
    }

    protected void onDestroyed() {

    }

    public void bomb() {
        if (isBoss()) {
            mHp -= 50;
        } else {
            mHp = 0;
        }

        if (holdsStage()) {
            mContext.getState().getCurrentStage().unholdTimer();
        }
    }

    protected void updatePattern() {
    }
}
