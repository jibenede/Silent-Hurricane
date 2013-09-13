package com.puc.sh.model.stages;

import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;

public abstract class Stage {
	protected Context mContext;
	protected GameState mState;
	protected AssetsHolder mAssets;
	protected RenderView mRenderer;

	protected Queue<FoeAppearance> mFoes;

	private long mTimer;
	private boolean mHold;
	private boolean mBossAppeared;

	protected long mTimeUntilBossAudio;
	protected boolean mStageAudioStopped;

	public Stage(Context context, GameState state, AssetsHolder assets, RenderView renderer) {
		mContext = context;
		mState = state;
		mAssets = assets;
		mRenderer = renderer;

		mFoes = new LinkedList<Stage.FoeAppearance>();
		mTimeUntilBossAudio = 3000;
	}

	public void update(long interval) {
		if (!mHold) {
			mTimer += interval;
		}

		while (!mFoes.isEmpty() && mFoes.peek().mTime < mTimer) {
			Foe foe = mFoes.poll().mFoe;
			mState.getEnemies().addEnemy(foe);

			if (foe.isBoss() && !mBossAppeared) {
				mBossAppeared = true;
			}
		}

		if (mTimeUntilBossAudio > 0 && mBossAppeared) {
			mTimeUntilBossAudio -= interval;

			if (!mStageAudioStopped) {
				mRenderer.setVolume((mTimeUntilBossAudio - 1000) / 2000.0f);

				if (mTimeUntilBossAudio < 1000) {
					mRenderer.stopAudio();
					mStageAudioStopped = true;
				}
			}

			if (mTimeUntilBossAudio < 0) {
				mRenderer.startAudio(getBossTheme());
			}
		}

	}

	protected void addFoeAtTime(Foe foe, long time) {
		mFoes.offer(new FoeAppearance(time, foe));
	}

	private class FoeAppearance {
		private long mTime;
		private Foe mFoe;

		public FoeAppearance(long time, Foe foe) {
			mTime = time;
			mFoe = foe;
		}
	}

	public abstract Audio getStageTheme();

	public abstract Audio getBossTheme();
}
