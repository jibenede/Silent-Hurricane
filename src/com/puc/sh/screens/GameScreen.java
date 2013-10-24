package com.puc.sh.screens;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Audio;
import com.puc.sh.model.Player.PlayerState;
import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.sh.model.stages.Stage;
import com.puc.soa.AssetsHolder;
import com.puc.soa.AuroraContext;
import com.puc.soa.GameState;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.CircularArray;

public class GameScreen extends Screen implements SensorEventListener {
    private enum GameplayState {
        RESUME, PAUSED, VICTORY, DEFEAT
    }

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Matrix mMatrix;
    private Stage mStage;

    private long mTimeOfLastBomb;
    private long mTimeSinceBossAppeared;
    private int mBackgroundDelta;

    private GameState mState;
    private AssetsHolder mAssets;

    private Rect mSourceRect;
    private Rect mDestRect;

    private Rect mTempRect1;
    private Rect mTempRect2;

    private long mTicks;

    private GameplayState mGameplayState;

    private List<Widget> mWidgets;

    public GameScreen(AuroraContext context, RenderView renderer, Stage stage) {
        super(context, renderer);

        mPaint = new Paint();
        mMatrix = new Matrix();
        mStage = stage;
        mBackgroundDelta = mStage.getBackground().getHeight()
                - Globals.CANVAS_HEIGHT;

        mState = context.getState();
        mAssets = context.getAssets();

        mGameplayState = GameplayState.RESUME;
        mWidgets = new ArrayList<Widget>();

        mSourceRect = new Rect(0, mBackgroundDelta, Globals.CANVAS_WIDTH,
                mBackgroundDelta + Globals.CANVAS_HEIGHT);
        mDestRect = new Rect(0, 0, Globals.CANVAS_WIDTH, Globals.CANVAS_HEIGHT);

        mTempRect1 = new Rect();
        mTempRect2 = new Rect();
    }

    @Override
    public Bitmap getBitmap() {
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(Globals.CANVAS_WIDTH,
                    Globals.CANVAS_HEIGHT, Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        drawCanvas();

        return mBitmap;
    }

    @Override
    public boolean onBackPressed() {
        if (mGameplayState == GameplayState.RESUME) {
            enablePause();
        } else {
            exit();
        }
        return true;
    }

    private void exit() {
        StageSelectionScreen screen = new StageSelectionScreen(mContext,
                mRenderer);
        mRenderer.transitionTo(screen);
    }

    private void enablePause() {
        mGameplayState = GameplayState.PAUSED;
        Widget resume = new Widget(mContext.getAssets().buttonResume, 50, 600);
        resume.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                mGameplayState = GameplayState.RESUME;
            }
        });

        Widget exit = new Widget(mContext.getAssets().buttonExit, 380, 600);
        exit.setListener(new OnTouchListener() {
            public void onTouchEvent(MotionEvent event) {
                exit();
            }
        });

        mWidgets.clear();
        mWidgets.add(resume);
        mWidgets.add(exit);
    }

    private void drawCanvas() {
        mCanvas.drawBitmap(mStage.getBackground(), mSourceRect, mDestRect, null);

        // mPaint.setColor(Color.BLACK);
        // mPaint.setTextSize(24);
        // mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // mCanvas.drawText("BOSS", 20, 20, mPaint);
        //
        // mPaint.setColor(Color.rgb(255, 210, 0));
        // mPaint.setStrokeWidth(10);
        // float w = (mSize.x - 40) * mState.getBossHpRatio();
        // mCanvas.drawLine(20, 40, 20 + w, 40, mPaint);

        if (mState.isPlayerContinuing() && mState.mShip.shouldDraw()) {
            mCanvas.drawBitmap(mState.mShip.mBitmap,
                    mState.mShip.mShipPosition.x, mState.mShip.mShipPosition.y,
                    null);

            // if ((mTicks % 1000) / 500 == 0) {
            mPaint.setColor(Color.RED);
            mCanvas.drawCircle(
                    mState.mShip.mShipPosition.x
                            + mState.mShip.mBitmap.getWidth() / 2,
                    mState.mShip.mShipPosition.y
                            + mState.mShip.mBitmap.getHeight() / 2, 5, mPaint);
            // }

        }

        CircularArray animations = mState.mAnimations;
        for (int i = 0; i < animations.size(); i++) {
            Animation a = (Animation) animations.get(i);
            if (a.isOnScreen()) {
                mCanvas.drawBitmap(a.getFrame(), a.mPosition.x, a.mPosition.y,
                        null);
            }
        }

        CircularArray enemies = mState.mEnemies;
        for (int i = 0; i < enemies.size(); i++) {
            Foe e = (Foe) enemies.get(i);
            if (e.isOnScreen()) {
                mMatrix.reset();
                mMatrix.setTranslate(e.mPosition.x, e.mPosition.y);
                float degrees = e.getAngle() * 360 / (2 * (float) Math.PI);
                mMatrix.postRotate(degrees,
                        e.mPosition.x + e.mBitmap.getWidth() / 2, e.mPosition.y
                                + e.mBitmap.getHeight() / 2);

                mCanvas.drawBitmap(e.mBitmap, mMatrix, null);
            }
        }

        BulletArray bullets = mState.mPlayerBullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.getBullet(i);
            if (b.mDisplay) {
                mCanvas.drawBitmap(b.mBitmap, b.mPosition.x, b.mPosition.y,
                        null);

                // mTempRect1.left = 0;
                // mTempRect1.top = 0;
                // mTempRect1.right = b.mBitmap.getWidth();
                // mTempRect1.bottom = b.mBitmap.getHeight();
                //
                // mTempRect2.left = (int) b.mPosition.x;
                // mTempRect2.top = (int) b.mPosition.y;
                // mTempRect2.right = (int) (b.mPosition.x + b.mSize);
                // mTempRect2.bottom = (int) (b.mPosition.y + b.mSize);
                //
                // mCanvas.drawBitmap(b.mBitmap, mTempRect1, mTempRect2, null);
            }
        }
        bullets = mState.mEnemyBullets;
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.getBullet(i);
            if (b.mDisplay) {
                mCanvas.drawBitmap(b.mBitmap, b.mPosition.x, b.mPosition.y,
                        null);
            }
        }

        drawBossLifeBar();

        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(36);
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mCanvas.drawText(String.format("%09d", mContext.getState().mScore),
                Globals.CANVAS_WIDTH - 200, 30, mPaint);

        mCanvas.drawBitmap(mAssets.livesIcon, 370, 10, null);
        mCanvas.drawText("x" + (Math.max(mState.mShip.mLives - 1, 0)), 425, 30,
                mPaint);

        if (mGameplayState == GameplayState.PAUSED) {
            drawPauseScreen();
        }
    }

    private void drawPauseScreen() {
        mPaint.setColor(Color.BLACK);
        mPaint.setAlpha(128);

        mTempRect1.top = 0;
        mTempRect1.right = Globals.CANVAS_WIDTH;
        mTempRect1.bottom = Globals.CANVAS_HEIGHT;
        mTempRect1.left = 0;

        mCanvas.drawRect(mTempRect1, mPaint);
        for (Widget w : mWidgets) {
            mCanvas.drawBitmap(w.mBitmap, w.X, w.Y, null);
        }

    }

    private void drawBossLifeBar() {
        if (mStage.hasBossAppeared()) {
            if (mTimeSinceBossAppeared == 0) {
                mTimeSinceBossAppeared = mTicks;
            } else {
                mTempRect1.left = 0;
                mTempRect1.top = 0;
                mTempRect1.bottom = mContext.getAssets().bossHp.getHeight();

                mTempRect2.left = 20;
                mTempRect2.top = 10;
                mTempRect2.bottom = mContext.getAssets().bossHp.getHeight() + 10;

                int delta = (int) (mTicks - mTimeSinceBossAppeared);
                if (delta < 1000) {
                    mTempRect1.right = 20 + (int) (mContext.getAssets().bossHp
                            .getWidth() * (delta / 1000f));
                    mTempRect2.right = mTempRect1.right;
                    mCanvas.drawBitmap(mContext.getAssets().bossHp, mTempRect1,
                            mTempRect2, null);

                    int alpha = (int) (delta * (255 / 1000f));
                    mPaint.setAlpha(alpha);
                    mCanvas.drawBitmap(mContext.getAssets().bossIcon, 20, 10,
                            mPaint);
                } else {
                    mTempRect1.right = 20 + (int) (mContext.getAssets().bossHp
                            .getWidth() * (mStage.mBoss.mHp / (float) mStage.mBoss.mOriginalHp));
                    mTempRect2.right = mTempRect1.right;
                    mCanvas.drawBitmap(mContext.getAssets().bossHp, mTempRect1,
                            mTempRect2, null);

                    mCanvas.drawBitmap(mContext.getAssets().bossIcon, 20, 10,
                            null);
                }

            }
        }
    }

    @Override
    public void update(long interval) {
        if (mGameplayState == GameplayState.RESUME) {
            mTicks += interval;
            mStage.update(interval);
            mState.update(interval);

            if (mBackgroundDelta > 0) {
                mBackgroundDelta -= 1;
                mSourceRect.top -= 1;
                mSourceRect.bottom -= 1;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGameplayState == GameplayState.RESUME) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int shipX = (int) event.getX() - mAssets.ship.getWidth() / 2;
                int shipY = (int) event.getY() - mAssets.ship.getHeight() / 2;
                mState.setDestination(shipX, shipY);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int shipX = (int) event.getX() - mAssets.ship.getWidth() / 2;
                int shipY = (int) event.getY() - mAssets.ship.getHeight() / 2;
                mState.setDestination(shipX, shipY);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                mState.setDestination(-1, -1);
            }
        } else {
            for (Widget w : mWidgets) {
                w.hitTest(event);
            }
        }

        return true;
    }

    @Override
    public Audio getAudio() {
        return mStage.getStageTheme();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    public void onSensorChanged(SensorEvent event) {
        if (mTicks - mTimeOfLastBomb > 3000
                && (mState.mShip.mStatus == PlayerState.ALIVE || mState.mShip.mStatus == PlayerState.REVIVING)
                && (event.values[0] > Globals.ACCELEROMETER_THRESHOLD || event.values[0] < -Globals.ACCELEROMETER_THRESHOLD)) {
            if (mState.mShip.mBombs > 0) {
                mState.mShip.mBombs--;
                mState.detonateBomb();
                mTimeOfLastBomb = mTicks;
            }
        }
    }

}
