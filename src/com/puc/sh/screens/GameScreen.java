package com.puc.sh.screens;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.puc.sh.model.Audio;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.Bullet.BulletType;
import com.puc.sh.model.foes.Foe;
import com.puc.sh.model.stages.Stage;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.FoeArray;

public class GameScreen extends Screen {
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mPaint;
    private Matrix mMatrix;
    private Stage mStage;

    public GameScreen(Context context, AssetsHolder assets, GameState state,
            RenderView renderer, Stage stage) {
        super(context, assets, state, renderer);

        mPaint = new Paint();
        mMatrix = new Matrix();
        mStage = stage;
    }

    @Override
    public Bitmap getBitmap() {
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(mSize.x, mSize.y,
                    Bitmap.Config.ARGB_8888);
            mCanvas = new Canvas(mBitmap);
        }

        drawCanvas();

        return mBitmap;
    }

    private boolean death;

    private void drawCanvas() {
        mCanvas.drawBitmap(mAssets.stageBackground, 0, 0, null);

        // mPaint.setColor(Color.BLACK);
        // mPaint.setTextSize(24);
        // mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        // mCanvas.drawText("BOSS", 20, 20, mPaint);
        //
        // mPaint.setColor(Color.rgb(255, 210, 0));
        // mPaint.setStrokeWidth(10);
        // float w = (mSize.x - 40) * mState.getBossHpRatio();
        // mCanvas.drawLine(20, 40, 20 + w, 40, mPaint);

        if (mState.isAlive()) {
            PointF shipPosition = mState.getShipPosition();
            mCanvas.drawBitmap(mAssets.ship, shipPosition.x, shipPosition.y,
                    null);
        } else {
            if (!death) {
                mAssets.death.prepare();
            }
            death = true;

            PointF shipPosition = mState.getShipPosition();
            Bitmap bmp = mAssets.death.getFrame();
            if (bmp != null) {
                mCanvas.drawBitmap(bmp, shipPosition.x, shipPosition.y, null);
            }
        }

        BulletArray bullets = mState.getBullets();
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.getBullet(i);
            if (b.getRender()) {
                if (b.getType() == BulletType.Plasma) {
                    mCanvas.drawBitmap(mAssets.plasma, b.getX(), b.getY(), null);
                } else {
                    mCanvas.drawBitmap(mAssets.laser1, b.getX(), b.getY(), null);
                }
            }
        }

        FoeArray enemies = mState.getEnemies();
        for (int i = 0; i < enemies.size(); i++) {
            Foe e = enemies.getEnemy(i);
            if (!e.isGone()) {
                mMatrix.reset();
                mMatrix.setTranslate(e.getX(), e.getY());
                float degrees = e.getAngle() * 360 / (2 * (float) Math.PI);
                mMatrix.postRotate(degrees, e.getX() + e.getBitmap().getWidth()
                        / 2, e.getY() + e.getBitmap().getHeight() / 2);

                mCanvas.drawBitmap(e.getBitmap(), mMatrix, null);
            }
        }
    }

    @Override
    public void update(long interval) {
        mStage.update(interval);
        mState.update(interval);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
        return true;
    }

    @Override
    public Audio getAudio() {
        return mStage.getStageTheme();
    }

}
