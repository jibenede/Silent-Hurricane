package com.puc.soa;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.puc.sh.model.Audio;
import com.puc.sh.screens.GameScreen;
import com.puc.sh.screens.MainScreen;
import com.puc.sh.screens.Screen;

public class RenderView extends SurfaceView implements Runnable {
	private long startTime;
	private long lastTime;
	private long currentTime;

	private Thread renderThread = null;
	private SurfaceHolder holder;
	private Paint paint;
	private GameState mState;
	private AssetsHolder mAssets;

	private volatile boolean running = false;
	private Random rand = new Random();

	private int screenWidth;

	private Screen mCurrentScreen;
	private Audio mCurrentAudio;
	private AuroraContext mContext;

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	private Point mSize;
	private Rect mRect;

	public RenderView(Context context) {
		super(context);
		holder = this.getHolder();
		paint = new Paint();

		mAssets = new AssetsHolder(context);
		PersistanceManager persistance = new PersistanceManager(context);

		mState = new GameState(context, mAssets, persistance);

		mContext = mState.mContext;
		mCurrentScreen = new MainScreen(mContext, this);
		mCurrentAudio = mAssets.title_screen;
		mCurrentAudio.prepare();

		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		mSize = new Point();
		display.getSize(mSize);

		mRect = new Rect(0, 0, mSize.x, mSize.y);
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();

		mCurrentAudio.play();
		if (mContext.getState().getCurrentStage() != null) {
			mContext.getState().getCurrentStage().unlockAudio();
		}

		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.registerListener((GameScreen) mCurrentScreen, mAccelerometer,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public boolean onBackPressed() {
		return mCurrentScreen.onBackPressed();
	}

	public void pause() {
		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.unregisterListener((GameScreen) mCurrentScreen);
			((GameScreen) mCurrentScreen).enablePause();
		}

		if (mContext.getState().getCurrentStage() != null) {
			mContext.getState().getCurrentStage().lockAudio();
		}

		running = false;
		mCurrentAudio.pause();
		while (renderThread.isAlive()) {
			try {
				renderThread.join();
			} catch (InterruptedException e) {
			}
		}
	}

	public void destroy() {
		mCurrentAudio.stop();
	}

	@Override
	public void run() {
		startTime = lastTime = System.currentTimeMillis();
		while (running) {
			update();
			if (!holder.getSurface().isValid())
				continue;
			draw();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mSize.x != Globals.CANVAS_WIDTH) {
			float x = event.getX() * Globals.CANVAS_WIDTH / mSize.x;
			event.offsetLocation(x - event.getX(), 0);
		}
		if (mSize.y != Globals.CANVAS_HEIGHT) {
			float y = event.getY() * Globals.CANVAS_HEIGHT / mSize.y;
			event.offsetLocation(0, y - event.getY());
		}

		return mCurrentScreen.onTouchEvent(event);

	}

	private void draw() {
		Canvas canvas = holder.lockCanvas();

		Bitmap screenBmp = mCurrentScreen.getBitmap();
		canvas.drawBitmap(screenBmp, null, mRect, null);

		holder.unlockCanvasAndPost(canvas);
	}

	private void update() {
		currentTime = System.currentTimeMillis();
		mCurrentScreen.update(currentTime - lastTime);
		lastTime = currentTime;
	}

	public void transitionTo(Screen screen) {
		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.unregisterListener((GameScreen) mCurrentScreen);
		}

		mCurrentScreen = screen;
		Audio audio = screen.getAudio();
		if (audio != null && audio != mCurrentAudio) {
			mContext.getState().getCurrentStage().lockAudio();
			mCurrentAudio.stop();
			mCurrentAudio = audio;
			mCurrentAudio.prepare();
			mCurrentAudio.play();
			mContext.getState().getCurrentStage().unlockAudio();
		}

		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.registerListener((GameScreen) mCurrentScreen, mAccelerometer,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}

	public void setVolume(float volume) {
		mCurrentAudio.setVolume(volume);
	}

	public void stopAudio() {
		mCurrentAudio.stop();
	}

	public void startAudio(Audio audio) {
		mCurrentAudio = audio;
		mCurrentAudio.prepare();
		mCurrentAudio.play();
	}

}
