package com.puc.sh.model.stages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import android.content.Context;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;
import com.puc.soa.utils.Utils;

public abstract class Stage {
	protected Context mContext;
	protected GameState mState;
	protected AssetsHolder mAssets;
	protected RenderView mRenderer;

	protected Queue<FoeAppearance> mFoes;
	protected List<FoeAppearance> mTemp;

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
		mTemp = new ArrayList<Stage.FoeAppearance>();
		mTimeUntilBossAudio = 3000;
	}

	public void update(long interval) {
		if (!mHold) {
			mTimer += interval;
		}

		while (!mFoes.isEmpty() && mFoes.peek().mTime < mTimer) {
			Foe foe = mFoes.poll().mFoe;
			mState.getEnemies().add(foe);

			if (foe.isBoss() && !mBossAppeared) {
				mBossAppeared = true;
			}
		}

		if (mTimeUntilBossAudio >= 0 && mBossAppeared) {
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
		mTemp.add(new FoeAppearance(time, foe));
	}

	protected void prepare() {
		FoeAppearance[] temp = mTemp.toArray(new FoeAppearance[mTemp.size()]);
		Utils.quicksort(temp);
		for (FoeAppearance f : temp) {
			mFoes.offer(f);
		}
	}

	private class FoeAppearance implements Comparable<FoeAppearance> {
		private long mTime;
		private Foe mFoe;

		public FoeAppearance(long time, Foe foe) {
			mTime = time;
			mFoe = foe;
		}

		public int compareTo(FoeAppearance another) {
			return (int) (mTime - another.mTime);
		}
	}

	public abstract Audio getStageTheme();

	public abstract Audio getBossTheme();
}
