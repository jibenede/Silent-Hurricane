package com.puc.sh.model.bullets;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.sh.model.Renderable;
import com.puc.soa.AuroraContext;

public abstract class BulletEmitter implements Renderable {
    public PointF mPosition;
    protected AuroraContext mContext;
    public int mSize;
    public Bitmap mBitmap;

    public BulletEmitter(AuroraContext context, PointF startingPosition,
            int size, Bitmap bitmap) {
        mPosition = startingPosition;
        mContext = context;
        mSize = size;
        mBitmap = bitmap;
    }

    public void update(long interval) {
        updateMovement(interval);
        fireBullets(interval);
    }

    public abstract void updateMovement(long interval);

    public abstract void fireBullets(long interval);
}
