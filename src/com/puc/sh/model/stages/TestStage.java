package com.puc.sh.model.stages;

import android.graphics.Bitmap;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.boss.FirstBoss;
import com.puc.soa.AuroraContext;
import com.puc.soa.RenderView;

public class TestStage extends Stage {

    public TestStage(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        FirstBoss foe = new FirstBoss(context, 10000);
        addFoeAtTime(foe, 2000);

        prepare();
    }

    @Override
    public Audio getStageTheme() {
        // TODO Auto-generated method stub
        return mContext.getAssets().stage1_audio;
    }

    @Override
    public Audio getBossTheme() {
        // TODO Auto-generated method stub
        return mContext.getAssets().boss1_theme;
    }

    @Override
    public Bitmap getBackground() {
        // TODO Auto-generated method stub
        return mContext.getAssets().stage2Background;
    }

}
