package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.puc.sh.model.Audio;
import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.sh.model.stages.Stage;
import com.puc.sh.model.stages.Stage0;
import com.puc.sh.model.stages.Stage1;
import com.puc.sh.model.stages.Stage2;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class StageSelectionScreen extends Screen {

    private Widget mStage1;
    private Widget mStage2;
    private Widget mStage3;

    private int mSelectedStage;

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private boolean mFading;

    private int mAlpha;
    private long mFadeDuration;
    private long mTicks;

    public StageSelectionScreen(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        mStage1 = new Widget(mContext.getAssets().buttonStage1,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonStage1.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2);
        mStage1.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                if (mSelectedStage == 0) {
                    mSelectedStage = 1;
                    fade(1000);
                }

            }
        });

        mStage2 = new Widget(mContext.getAssets().buttonStage2,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonStage2.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2 + 150);
        mStage2.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                if (mSelectedStage == 0) {
                    mSelectedStage = 2;
                    fade(1000);
                }
            }
        });

        mStage3 = new Widget(mContext.getAssets().buttonStage3,
                Globals.CANVAS_WIDTH / 2
                        - mContext.getAssets().buttonStage3.getWidth() / 2,
                Globals.CANVAS_HEIGHT / 2 + 300);
        mStage3.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                if (mSelectedStage == 0) {
                    mSelectedStage = 3;
                    fade(1000);
                }
            }
        });

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
        mCanvas.drawBitmap(mStage2.mBitmap, mStage2.X, mStage2.Y, paint);
        mCanvas.drawBitmap(mStage3.mBitmap, mStage3.X, mStage3.Y, paint);

    }

    @Override
    public void update(long interval) {
        mTicks += interval;

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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mStage1.hitTest(event);
            mStage2.hitTest(event);
            mStage3.hitTest(event);
        }
        return true;
    }

    private void onFadeFinished() {
        Stage stage;
        if (mSelectedStage == 1) {
            stage = new Stage0(mContext, mRenderer);
        } else if (mSelectedStage == 2) {
            stage = new Stage1(mContext, mRenderer);
        } else {
            stage = new Stage2(mContext, mRenderer);
        }

        mContext.getState().setCurrentStage(stage);
        mContext.getState().reset();
        GameScreen screen = new GameScreen(mContext, mRenderer, stage);
        mSelectedStage = 0;

        mRenderer.transitionTo(screen);
    }

    @Override
    public Audio getAudio() {
        return mContext.getAssets().intro;
    }

}
