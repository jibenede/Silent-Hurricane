package com.puc.soa;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private WakeLock wakeLock;
	private RenderView renderView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// Prevent window sleep
		PowerManager powerManager = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "My Lock");

		// Get fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);

		renderView = new RenderView(this);
		setContentView(renderView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onPause() {
		wakeLock.release();
		renderView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		wakeLock.acquire();
		renderView.resume();
		super.onResume();
	}

	@Override
	public void onDestroy() {
		renderView.destroy();
		super.onDestroy();
	}
}
