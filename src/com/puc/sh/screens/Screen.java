package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.view.MotionEvent;

import com.puc.sh.model.Audio;
import com.puc.soa.AuroraContext;
import com.puc.soa.RenderView;

public abstract class Screen {
    protected AuroraContext mContext;
    protected RenderView mRenderer;

    public Screen(AuroraContext context, RenderView renderer) {
        mContext = context;
        mRenderer = renderer;
    }

    public abstract Bitmap getBitmap();

    public abstract void update(long interval);

    public Audio getAudio() {
        return null;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
