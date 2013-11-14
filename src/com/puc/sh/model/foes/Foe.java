package com.puc.sh.model.foes;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.util.Log;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Renderable;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.CollisionUtils;
import com.puc.soa.AssetsHolder;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.Utils;

public abstract class Foe implements Renderable {

    protected AuroraContext mContext;
    public int mHp;
    public int mOriginalHp;

    public PointF mPosition;
    public Bitmap mBitmap;

    protected Bullet mBullet;
    protected boolean mInvulnerable;
    protected long mTicks;
    protected long mTimeForNextExplosion;

    public Foe(AuroraContext context, int hp, Bitmap bitmap) {
        mContext = context;
        mHp = mOriginalHp = hp;
        mBullet = new Bullet(context);
        mBitmap = bitmap;
    }

    public void update(long interval) {
        mTicks += interval;
        if (isOnScreen()) {
            if (mHp > 0) {
                updatePattern();
                updatePosition(interval);
                fireBullets(interval);
                hitTest();
            }

            if (isBoss() && mHp <= 0) {
                mTimeForNextExplosion -= interval;
                if (mTimeForNextExplosion < 0) {
                    AssetsHolder assets = mContext.getAssets();

                    double x = -assets.blueExplosion[0].getWidth() / 2
                            + mPosition.x + Utils.sRandom.nextDouble()
                            * mBitmap.getWidth();
                    double y = -assets.blueExplosion[0].getHeight() / 2
                            + mPosition.y + Utils.sRandom.nextDouble()
                            * mBitmap.getHeight();

                    Animation a = new Animation(mContext, assets.blueExplosion,
                            50, (int) x, (int) y);
                    a.prepare();
                    mContext.getState().mAnimations.add(a);

                    mTimeForNextExplosion = 100;
                }

            }

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
                if (mHp <= 0) {
                    onDestroyed();
                    if (holdsStage()) {
                        mContext.getState().getCurrentStage().unholdTimer();
                    }
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
        if (isBoss()) {
            mContext.getState().bossDefeated();
        } else {
            Log.i("Game", "enemy destroyed");
            AssetsHolder assets = mContext.getAssets();
            Animation a = new Animation(mContext, assets.death, 15, mPosition.x
                    + mBitmap.getWidth() / 2 - assets.death[0].getWidth() / 2,
                    mPosition.y + mBitmap.getHeight() / 2
                            - assets.death[0].getHeight() / 2);
            a.prepare();
            mContext.getState().mAnimations.add(a);
        }
    }

    public void bomb() {
        if (mPosition.x >= -mBitmap.getWidth()
                && mPosition.x <= Globals.CANVAS_WIDTH
                && mPosition.y >= -mBitmap.getHeight()
                && mPosition.y <= Globals.CANVAS_HEIGHT) {
            mHp -= 50;
            if (mHp <= 0) {
                onDestroyed();
                if (holdsStage()) {
                    mContext.getState().getCurrentStage().unholdTimer();
                }
            }
        }
    }

    protected void updatePattern() {
    }
}
