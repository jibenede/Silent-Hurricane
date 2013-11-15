package com.puc.sh.model.bullets;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Bullet {
    public enum BulletShape {
        Round, Square
    }

    public enum BulletType {
        Linear, Cluster, Curve, TwoStep, Radial, Sticky
    };

    private AuroraContext mContext;

    private float mSpeedX;
    private float mSpeedY;
    private float mAccelX;
    private float mAccelY;

    private long mLifetime;
    public BulletType mType;
    public BulletShape mShape;

    public PointF mPosition;
    public boolean mDisplay;

    public float mSize;
    public float mSize2;

    public Bitmap mBitmap;

    public int mFirepower;

    public float mSpeedRadius;
    public float mSpeedAngle;
    public float mRadius;
    public float mAngle;

    public float mOriginX;
    public float mOriginY;

    public Bullet(AuroraContext context) {
        mPosition = new PointF();
        mContext = context;
    }

    public void initializeBullet(Bullet b) {
        mSpeedX = b.mSpeedX;
        mSpeedY = b.mSpeedY;
        mAccelX = b.mAccelX;
        mAccelY = b.mAccelY;
        mPosition.x = b.mPosition.x;
        mPosition.y = b.mPosition.y;
        mLifetime = b.mLifetime;
        mType = b.mType;
        mShape = b.mShape;
        mDisplay = b.mDisplay;

        mSize = b.mSize;
        mSize2 = b.mSize2;

        mBitmap = b.mBitmap;
        mFirepower = b.mFirepower;

        mSpeedAngle = b.mSpeedAngle;
        mSpeedRadius = b.mSpeedRadius;
        mRadius = b.mRadius;
        mAngle = b.mAngle;
        mOriginX = b.mOriginX;
        mOriginY = b.mOriginY;
    }

    /**
     * 
     * @param bitmap The bitmap graphic associated with this bullet.
     * @param benign {@code true} if this bullet should harm foes, {@code false}
     *            if it should harm the player
     * @param speedX The speed along the X axis of this bullet.
     * @param speedY The speed along the Y axis of this bullet.
     * @param positionX The starting X position.
     * @param positionY The starting Y position.
     * @param lifetime The amount of milliseconds this bullet should remain in
     *            memory before being collected by the engine. This parameter is
     *            vital for optimization purposes.
     * @param size The size of this bullet. For collisions, this would be the
     *            radius of the bounding circle.
     * @param firepower
     */
    public void initializeLinearBullet(Bitmap bitmap, boolean benign,
            float speedX, float speedY, float positionX, float positionY,
            long lifetime, float size, int firepower) {
        mType = BulletType.Linear;
        mShape = BulletShape.Round;

        mSpeedX = speedX;
        mSpeedY = speedY;
        mPosition.x = positionX;
        mPosition.y = positionY;
        mLifetime = lifetime;
        mDisplay = true;
        mFirepower = firepower;

        mSize = mSize2 = size;

        mBitmap = bitmap;
    }

    public void initializeCurvedBullet(Bitmap bitmap, float speedX,
            float speedY, float accelX, float accelY, float positionX,
            float positionY, float size, int lifetime) {
        mType = BulletType.Curve;
        mShape = BulletShape.Round;

        mSpeedX = speedX;
        mSpeedY = speedY;
        mAccelX = accelX;
        mAccelY = accelY;
        mPosition.x = positionX;
        mPosition.y = positionY;
        mDisplay = true;
        mFirepower = 1;
        mLifetime = lifetime;

        mSize = mSize2 = size;

        mBitmap = bitmap;
    }

    public void initializeRadialBullet(Bitmap bitmap, float speedR,
            float speedAngle, float positionX, float positionY,
            float startRadius, float startAngle, float size, int firepower,
            int lifetime) {
        mType = BulletType.Radial;
        mShape = BulletShape.Round;

        mSpeedRadius = speedR;
        mSpeedAngle = speedAngle;
        mOriginX = mPosition.x = positionX;
        mOriginY = mPosition.y = positionY;
        mDisplay = true;
        mFirepower = firepower;

        mRadius = startRadius;
        mAngle = startAngle;

        mSize = mSize2 = size;

        mBitmap = bitmap;
        mLifetime = lifetime;
    }

    public void initializeLinearClusterBullet(Bitmap bitmap, float speedX,
            float speedY, float positionX, float positionY, long timeToCluster,
            float size, int firepower) {
        mType = BulletType.Cluster;
        mShape = BulletShape.Round;

        mSpeedX = speedX;
        mSpeedY = speedY;
        mPosition.x = positionX;
        mPosition.y = positionY;
        mLifetime = timeToCluster;
        mDisplay = true;
        mFirepower = firepower;

        mSize = mSize2 = size;

        mBitmap = bitmap;
    }

    public void initializeStickyBullet(Bitmap bitmap, float positionX,
            float positionY, long lifetime, float size) {
        mType = BulletType.Sticky;
        mShape = BulletShape.Round;

        mPosition.x = positionX;
        mPosition.y = positionY;
        mLifetime = lifetime;
        mDisplay = true;
        mFirepower = 1;

        mSize = mSize2 = size;

        mBitmap = bitmap;
    }

    public void update(long interval) {
        if (mDisplay) {
            if (mType == BulletType.Radial) {
                if (mLifetime < 0) {
                    mDisplay = false;
                }
            } else if (mType == BulletType.Curve) {
                if (mLifetime < 0
                        && (mPosition.x < -mSize
                                || mPosition.x > Globals.CANVAS_WIDTH
                                || mPosition.y < -mSize || mPosition.y > Globals.CANVAS_HEIGHT)) {
                    mDisplay = false;
                }
            } else {
                if (mPosition.x < -mSize || mPosition.x > Globals.CANVAS_WIDTH
                        || mPosition.y < -mSize
                        || mPosition.y > Globals.CANVAS_HEIGHT) {
                    mDisplay = false;
                }
            }

            if (mType == BulletType.Linear) {
                mPosition.x += mSpeedX * interval / 1000;
                mPosition.y += mSpeedY * interval / 1000;
            } else if (mType == BulletType.Curve) {
                mLifetime -= interval;
                mPosition.x += mSpeedX * interval / 1000;
                mPosition.y += mSpeedY * interval / 1000;

                mSpeedX += mAccelX * interval / 1000;
                mSpeedY += mAccelY * interval / 1000;
            } else if (mType == BulletType.Cluster) {
                mLifetime -= interval;
                if (mLifetime < 0) {
                    mDisplay = false;
                    fireCluster();
                }

                mPosition.x += mSpeedX * interval / 1000;
                mPosition.y += mSpeedY * interval / 1000;
            } else if (mType == BulletType.Radial) {
                mLifetime -= interval;
                mRadius += mSpeedRadius * interval / 1000;
                mAngle += mSpeedAngle * interval / 1000;

                mPosition.x = mOriginX + (float) (mRadius * Math.cos(mAngle));
                mPosition.y = mOriginY + (float) (mRadius * Math.sin(mAngle));

            } else if (mType == BulletType.Sticky) {
                mLifetime -= interval;
                if (mLifetime < 0) {
                    mDisplay = false;
                    stickyDetonate();
                }
            }

        }
    }

    private void stickyDetonate() {
        for (int i = 0; i < 2; i++) {
            float angle = (float) (2 * Math.PI * Utils.sRandom.nextDouble());

            float vX = (float) (Math.cos(angle) * 70);
            float vY = (float) (Math.sin(angle) * 70);

            Bullet b = mContext.getState().mBullet;
            b.initializeLinearBullet(mContext.getAssets().greenBullet12, false,
                    (int) vX, (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                    mPosition.y + mBitmap.getHeight(), 6000, 12, 1);
            mContext.getState().mEnemyBullets.addBullet(b);
        }

    }

    private void fireCluster() {
        for (int i = 0; i < 8; i++) {
            float angle = (float) (2 * Math.PI * (i / 8.f));

            float vX = (float) (Math.cos(angle) * 300);
            float vY = (float) (Math.sin(angle) * 300);

            Bullet b = mContext.getState().mBullet;
            b.initializeLinearBullet(mContext.getAssets().redBullet12, false,
                    (int) vX, (int) vY, mPosition.x + mBitmap.getWidth() / 2,
                    mPosition.y + mBitmap.getHeight(), 6000, 12, 1);
            mContext.getState().mEnemyBullets.addBullet(b);
        }
    }

}
