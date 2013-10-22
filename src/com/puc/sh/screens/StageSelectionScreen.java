package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.puc.sh.model.Audio;
import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.sh.model.stages.Stage;
import com.puc.sh.model.stages.Stage2;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class StageSelectionScreen extends Screen {

    private Widget mStage1;
    private Widget mStage2;
    private Widget mStage3;

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private boolean mFading;

    private int mAlpha;
    private long mFadeDuration;

    public StageSelectionScreen(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        mStage1 = new Widget(mContext.getAssets().buttonStage1,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonPlay.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2
                        + mContext.getAssets().buttonPlay.getHeight() / 2);
        mStage1.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                fade(1000);
            }
        });
        mStage2 = new Widget(mContext.getAssets().buttonStage2,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonPlay.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2
                        - mContext.getAssets().buttonPlay.getHeight() / 2);
        mStage3 = new Widget(mContext.getAssets().buttonStage3,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonPlay.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2
                        + mContext.getAssets().buttonPlay.getHeight());

        mAlpha = 255;
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
        int alpha = Math.max(0, mAlpha);

        mCanvas.drawRGB(0, 0, 0);

        Paint paint = new Paint();
        paint.setAlpha(alpha);

        mCanvas.drawBitmap(mContext.getAssets().mainBackground, 0, 0, paint);
        mCanvas.drawBitmap(mStage1.mBitmap, mStage1.X, mStage1.Y, paint);
        // mCanvas.drawBitmap(mStage2.mBitmap, mStage2.X, mStage2.Y, paint);
        // mCanvas.drawBitmap(mStage3.mBitmap, mStage3.X, mStage3.Y, paint);

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

    public void fade(long duration) {
        mFading = true;
        mFadeDuration = duration;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mStage1.hitTest(event);
        return true;
    }

    private void onFadeFinished() {
        Stage stage = new Stage2(mContext, mRenderer);
        GameScreen screen = new GameScreen(mContext, mRenderer, stage);
        mContext.getState().setCurrentStage(stage);
        mRenderer.transitionTo(screen);
    }

    @Override
    public Audio getAudio() {
        return mContext.getAssets().intro;
    }

}
