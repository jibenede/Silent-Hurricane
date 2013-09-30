package com.puc.soa;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.DisplayMetrics;
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

	private SensorManager mSensorManager;
	private Sensor mAccelerometer;

	public RenderView(Context context) {
		super(context);
		holder = this.getHolder();
		paint = new Paint();

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displaymetrics);
		screenWidth = displaymetrics.widthPixels;

		mAssets = new AssetsHolder(context);

		mState = new GameState(context, mAssets);

		mCurrentScreen = new MainScreen(context, mAssets, mState, this);
		mCurrentAudio = mAssets.intro;

		mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();

		mCurrentAudio.play();

		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.registerListener((GameScreen) mCurrentScreen, mAccelerometer,
					SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public void pause() {
		if (mCurrentScreen instanceof GameScreen) {
			mSensorManager.unregisterListener((GameScreen) mCurrentScreen);
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
		mCurrentAudio.dispose();
	}

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
		return mCurrentScreen.onTouchEvent(event);

	}

	private void draw() {
		Canvas canvas = holder.lockCanvas();

		Bitmap screenBmp = mCurrentScreen.getBitmap();
		canvas.drawBitmap(screenBmp, 0, 0, null);

		holder.unlockCanvasAndPost(canvas);
	}

	private void update() {
		currentTime = System.currentTimeMillis();
		mCurrentScreen.update(currentTime - lastTime);
		lastTime = currentTime;
	}

	public void transitionTo(Screen screen) {
		mCurrentScreen = screen;
		Audio audio = screen.getAudio();
		if (audio != null) {
			mCurrentAudio.dispose();
			mCurrentAudio = audio;
			mCurrentAudio.play();
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
		mCurrentAudio.dispose();
	}

	public void startAudio(Audio audio) {
		mCurrentAudio = audio;
		mCurrentAudio.play();
	}

}
