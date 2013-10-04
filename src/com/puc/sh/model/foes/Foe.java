package com.puc.sh.model.foes;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.sh.model.Renderable;
import com.puc.sh.model.bullets.Bullet;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.BulletArray;

public abstract class Foe implements Renderable {
    public static final int HORIZONTAL_CENTER = -1000;
    public static final int VERTICAL_TOP = -1001;

    protected AuroraContext mContext;
    public int mHp;
    public int mOriginalHp;

    public PointF mPosition;
    public Bitmap mBitmap;

    protected Bullet mBullet;
    protected boolean mInvulnerable;

    public Foe(AuroraContext context, int hp) {
        mContext = context;
        mHp = mOriginalHp = hp;
        mBullet = new Bullet(context);
    }

    public void update(long interval) {
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
                }
            }
        }
    }

    public abstract boolean collidesWith(Bullet b);

    public abstract void fireBullets(long interval);

    public boolean isBoss() {
        return false;
    }

    public boolean holdsStage() {
        return false;
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
