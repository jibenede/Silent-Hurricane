package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class MainScreen extends Screen {
    private Bitmap mBitmap;
    private Canvas mCanvas;

    private double mAlpha;

    private boolean mFading;
    private long mFadeDuration;

    private Widget mPlayButton;

    public MainScreen(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        mPlayButton = new Widget(mContext.getAssets().buttonPlay,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonPlay.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2
                        - mContext.getAssets().buttonPlay.getHeight() / 2);
        mPlayButton.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                StageSelectionScreen screen = new StageSelectionScreen(
                        mContext, mRenderer);
                mRenderer.transitionTo(screen);
            }
        });
        mAlpha = 255;
    }

    public void reset() {
        mAlpha = 255;
        mFading = false;
        mFadeDuration = 0;
    }

    @Override
    public Bitmap getBitmap() {
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(Globals.CANVAS_WIDTH,
                    Globals.CANVAS_HEIGHT, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
            drawCanvas();
        }

        if (mFading) {
            drawCanvas();
        }

        return mBitmap;
    }

    private void drawCanvas() {
        int alpha = (int) Math.max(0, mAlpha);

        mCanvas.drawRGB(0, 0, 0);

        Paint paint = new Paint();
        paint.setAlpha(alpha);

        mCanvas.drawBitmap(mContext.getAssets().mainBackground, 0, 0, paint);
        mCanvas.drawBitmap(mPlayButton.mBitmap, mPlayButton.X, mPlayButton.Y,
                paint);
    }

    @Override
    public void update(long interval) {
        if (mFading) {
            mAlpha -= (255.0 * interval / mFadeDuration);
            if (mAlpha <= 0) {
                mAlpha = 0;
                mFading = false;
                onFadeFinished();
            }
        }
    }

    private void onFadeFinished() {
        StageSelectionScreen screen = new StageSelectionScreen(mContext,
                mRenderer);
        mRenderer.transitionTo(screen);
    }

    /**
     * Starts a fading effect on this screen. All subsequent drawing will be
     * applied an alpha.
     * 
     * @param duration The duration in milliseconds this fade effect should
     *            take.
     */
    public void fade(long duration) {
        mFading = true;
        mFadeDuration = duration;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPlayButton.hitTest(event);
        return false;
    }

}
