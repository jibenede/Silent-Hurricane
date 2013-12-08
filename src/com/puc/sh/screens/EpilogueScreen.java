package com.puc.sh.screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.MotionEvent;

import com.puc.sh.model.Audio;
import com.puc.sh.model.Widget;
import com.puc.sh.model.Widget.OnTouchListener;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class EpilogueScreen extends Screen {
	private final static int TEXT_INTERVAL = 9000;

	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Rect mRect;

	private float mDeltaX;
	private long mTicks;
	private int mAlpha;
	private Paint mPaint;
	private TextPaint mTextPaint;
	private StaticLayout mLayout;

	private String[] mTextBoxes;
	private int mCurrentText;

	private long mTimeOfLastText;
	private long mTimeForNextText;

	private Widget mButtonNext;

	public EpilogueScreen(AuroraContext context, RenderView renderer) {
		super(context, renderer);
		mDeltaX = 0;
		mTicks = 0;
		mTimeOfLastText = 0;
		mTimeForNextText = TEXT_INTERVAL;
		mRect = new Rect();

		mButtonNext = new Widget(mContext.getAssets().buttonExit, Globals.CANVAS_WIDTH - 320,
				Globals.CANVAS_HEIGHT - 280);
		mButtonNext.setListener(new OnTouchListener() {
			@Override
			public void onTouchEvent(MotionEvent event) {
				StageSelectionScreen screen = new StageSelectionScreen(mContext, mRenderer);
				mRenderer.transitionTo(screen);
			}
		});

		mTextBoxes = new String[] {
				"In the year 2832, a terrible war took\nplace that nearly decimated humankind.",
				"In 2842 we took our revenge.",
				"While we layed waste to the alien armada,\nthe enemy admiral was thrown into\nthe vacuum of space.",
				"Let it be a reminder for all those\nto come of the strength of the\nhuman willpower.",
				"Let it be known that should we\nbe pushed to the edge...",
				"our vengeance shall be swift...", "and deadly!" };
	}

	@Override
	public Bitmap getBitmap() {
		if (mBitmap == null) {
			mBitmap = Globals.ScreenBitmap;
			mCanvas = new Canvas(mBitmap);
			mPaint = new Paint();
			mTextPaint = new TextPaint(mPaint);
		}

		drawCanvas();

		return mBitmap;
	}

	private void drawCanvas() {
		mCanvas.drawBitmap(mContext.getAssets().epilogueBackground, -mDeltaX, 0, null);

		mTextPaint.setTextSize(36);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mTextPaint.setColor(Color.YELLOW);
		mTextPaint.setAlpha(mAlpha);

		if (mCurrentText < mTextBoxes.length) {
			String text = mTextBoxes[mCurrentText];
			// mTextPaint.getTextBounds(text, 0, text.length(), mRect);

			mLayout = new StaticLayout(text, mTextPaint, mCanvas.getWidth(),
					Alignment.ALIGN_NORMAL, 1.0f, 1.0f, false);

			float textX = Globals.CANVAS_WIDTH / 2;// Globals.CANVAS_WIDTH / 2 -
													// mLayout.getWidth() /
			// 2;
			float textY = Globals.CANVAS_HEIGHT / 2 - mLayout.getHeight() / 2;

			mCanvas.save();

			mCanvas.translate(textX, textY);
			mLayout.draw(mCanvas);

			mCanvas.restore();

			// mCanvas.drawText(mTextBoxes[mCurrentText],
			// Globals.CANVAS_WIDTH / 2 - mRect.width() / 2,
			// Globals.CANVAS_HEIGHT / 2 - mRect.height() / 2, mPaint);
		}

		mCanvas.drawBitmap(mButtonNext.mBitmap, mButtonNext.X, mButtonNext.Y, null);
	}

	@Override
	public void update(long interval) {
		mTicks += interval;

		float increment = (float) (20 * (interval / 1000.0));
		if (mDeltaX + increment < mContext.getAssets().epilogueBackground.getWidth()
				- Globals.CANVAS_WIDTH) {
			mDeltaX += increment;
		} else {
			mDeltaX = mContext.getAssets().epilogueBackground.getWidth() - Globals.CANVAS_WIDTH;
		}

		mTimeForNextText -= interval;
		if (mTimeForNextText < 0) {
			mCurrentText++;
			mTimeOfLastText = mTicks;
			mTimeForNextText = TEXT_INTERVAL;
		}

		if (mTicks - mTimeOfLastText < 500) {
			mAlpha = 0;
		} else if (mTimeForNextText < 500) {
			mAlpha = 0;
		} else if (mTicks - mTimeOfLastText < 1500) {
			mAlpha = (int) (255.0 * (mTicks - mTimeOfLastText - 500) / 1000.0);
		} else if (mTimeForNextText < 1500) {
			mAlpha = (int) (255.0 * (mTimeForNextText - 500) / 1000.0);
		}

	}

	@Override
	public Audio getAudio() {
		return mContext.getAssets().intro;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mButtonNext.hitTest(event);
		return true;
	}

	@Override
	public boolean onBackPressed() {
		StageSelectionScreen screen = new StageSelectionScreen(mContext, mRenderer);
		mRenderer.transitionTo(screen);
		return true;
	}

}
