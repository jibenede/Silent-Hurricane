package com.puc.soa;

import android.content.Context;

public class AuroraContext {
    private Context mContext;
    private GameState mState;
    private AssetsHolder mAssets;

    public AuroraContext(Context context, GameState state, AssetsHolder assets) {
        mContext = context;
        mState = state;
        mAssets = assets;
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

}
