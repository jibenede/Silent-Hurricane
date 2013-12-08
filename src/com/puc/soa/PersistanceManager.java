package com.puc.soa;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PersistanceManager {
	private static final String STORAGE = "SilentHurricaneStorage2";

	private Context mContext;
	private SharedPreferences mPreferences;

	public PersistanceManager(Context context) {
		mContext = context;
		mPreferences = context.getSharedPreferences(STORAGE, 0);
	}

	public boolean isStageUnlocked(int stage) {
		return mPreferences.getBoolean("STAGE " + stage, false);
	}

	public void unlockStage(int stage) {
		Editor edit = mPreferences.edit();
		edit.putBoolean("STAGE " + stage, true);
		edit.commit();
	}

	public int getLives() {
		return mPreferences.getInt("LIVES", Globals.DEFAULT_LIVES);
	}

	public void increaseLives() {
		Editor edit = mPreferences.edit();
		int lives = getLives();
		edit.putInt("LIVES", Math.min(lives + 1, 100));
		edit.commit();
	}

	public long getHighScore(int stage) {
		return mPreferences.getLong("HIGHSCORE " + stage, 0);
	}

	public void setHighScore(int stage, long score) {
		long hs = getHighScore(stage);
		if (score > hs) {
			Editor edit = mPreferences.edit();
			edit.putLong("HIGHSCORE " + stage, score);
			edit.commit();
		}
	}

}
