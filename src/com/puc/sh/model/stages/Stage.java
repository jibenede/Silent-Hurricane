package com.puc.sh.model.stages;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.RenderView;
import com.puc.soa.utils.Utils;

public abstract class Stage {
    protected AuroraContext mContext;
    protected RenderView mRenderer;

    protected List<FoeAppearance> mFoes;
    private int mCurrentFoe;

    protected List<FoeAppearance> mTemp;

    private long mTimer;
    private int mHold;
    private boolean mBossAppeared;

    protected long mTimeUntilBossAudio;
    protected boolean mStageAudioStopped;
    private boolean mAudioLock;

    public Foe mBoss;

    public Stage(AuroraContext context, RenderView renderer) {
        mContext = context;
        mRenderer = renderer;

        mFoes = new LinkedList<Stage.FoeAppearance>();
        mTemp = new ArrayList<Stage.FoeAppearance>();
        mTimeUntilBossAudio = 3000;
        mCurrentFoe = 0;
    }

    public void reset() {
        // TODO: implement
    }

    public void update(long interval) {
        if (mHold <= 0) {
            mTimer += interval;
        }

        while (mCurrentFoe < mFoes.size()
                && mFoes.get(mCurrentFoe).mTime < mTimer) {
            Foe foe = mFoes.get(mCurrentFoe).mFoe;
            mCurrentFoe++;

            mContext.getState().getEnemies().add(foe);

            if (foe.isBoss() && !mBossAppeared) {
                mBossAppeared = true;
                mBoss = foe;
            }

            if (foe.holdsStage()) {
                mHold++;
            }
        }

        if (mTimeUntilBossAudio >= 0 && mBossAppeared) {
            mTimeUntilBossAudio -= interval;

            if (!mStageAudioStopped && !mAudioLock) {
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

    public void unlockAudio() {
        mAudioLock = false;
    }

    public void lockAudio() {
        mAudioLock = true;
    }

    public boolean hasBossAppeared() {
        return mBossAppeared;
    }

    public void unholdTimer() {
        mHold--;
        if (mHold < 0) {
            mHold = 0;
        }
    }

    protected void addFoeAtTime(Foe foe, long time) {
        mTemp.add(new FoeAppearance(time, foe));
    }

    protected void prepare() {
        FoeAppearance[] temp = mTemp.toArray(new FoeAppearance[mTemp.size()]);
        Utils.quicksort(temp);
        for (FoeAppearance f : temp) {
            mFoes.add(f);
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

    public abstract Bitmap getBackground();
}
