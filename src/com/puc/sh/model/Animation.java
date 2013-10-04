package com.puc.sh.model;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.soa.AuroraContext;

public class Animation implements Renderable {
    private Bitmap[] mFrames;
    private int mFrameInterval;

    private int mCurrentFrame;
    private long mTimer;
    private boolean mFinished;

    public PointF mPosition;

    public Animation(AuroraContext context, Bitmap[] frames, int frameInterval,
            float x, float y) {
        mFrames = frames;
        mFrameInterval = frameInterval;
        mPosition = new PointF();
        mPosition.x = x;
        mPosition.y = y;
    }

    public void prepare() {
        mCurrentFrame = 0;
        mTimer = 0;
        mFinished = false;
    }

    public void update(long interval) {
        mTimer += interval;
        if (mTimer > mFrameInterval) {
            mCurrentFrame++;
            mTimer -= mFrameInterval;
        }
        if (mCurrentFrame >= mFrames.length) {
            mFinished = true;
        }
    }

    public Bitmap getFrame() {
        return mFrames[mCurrentFrame];
    }

    public boolean isFinished() {
        return mFinished;
    }

    public boolean isOnScreen() {
        return !mFinished;
    }

    public int getFrameHeight() {
        return mFrames[0].getHeight();
    }

    public int getFrameWidth() {
        return mFrames[0].getWidth();
    }

}
