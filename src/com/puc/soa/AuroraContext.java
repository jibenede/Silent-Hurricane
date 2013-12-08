package com.puc.soa;

import android.content.Context;

public class AuroraContext {
	private Context mContext;
	private GameState mState;
	private AssetsHolder mAssets;
	private PersistanceManager mPersistance;

	public AuroraContext(Context context, GameState state, AssetsHolder assets,
			PersistanceManager persistance) {
		mContext = context;
		mState = state;
		mAssets = assets;
		mPersistance = persistance;
	}

	public Context getContext() {
		return mContext;
	}

	public GameState getState() {
		return mState;
	}

	public AssetsHolder getAssets() {
		return mAssets;
	}

	public PersistanceManager getPersistanceManager() {
		return mPersistance;
	}

}
