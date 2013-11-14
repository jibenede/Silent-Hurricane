package com.puc.sh.model;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.utils.Utils;

public class Powerup implements Renderable {
    public enum PowerupType {
        WideSpread, Heavy, Support
    }

    private final int SPEED = 200;

    public PointF mPosition;
    public PowerupType mType;
    public Bitmap mBitmap;
    private boolean mShow;

    public Powerup(AuroraContext context, int startX) {
        int random = Utils.sRandom.nextInt(2);
        if (random == 0) {
            mType = PowerupType.WideSpread;
        } else {
            mType = PowerupType.Heavy;
        }

        mBitmap = context.getAssets().sword;
        mPosition = new PointF(startX, -mBitmap.getHeight() + 5);
        mShow = true;
    }

    public boolean isOnScreen() {
        return mShow && mPosition.x > -mBitmap.getWidth()
                && mPosition.x < Globals.CANVAS_WIDTH
                && mPosition.y > -mBitmap.getHeight()
                && mPosition.y < Globals.CANVAS_HEIGHT;
    }

    public void consume() {
        mShow = false;
    }

    public void update(long interval) {
        mPosition.y += SPEED * (interval / 1000f);

    }

}
