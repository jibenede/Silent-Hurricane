package com.puc.sh.model.stages;

import android.graphics.Bitmap;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.Angel;
import com.puc.sh.model.foes.Raven;
import com.puc.sh.model.foes.Stingray;
import com.puc.sh.model.foes.Volt;
import com.puc.sh.model.foes.boss.FirstBoss;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class Stage0 extends Stage {

    public Stage0(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        for (int i = 0; i < 3; i++) {
            Raven foe = new Raven(context, 10, 5000, Globals.CANVAS_WIDTH / 2
                    - i * 80 - 100, 100);
            addFoeAtTime(foe, 1000 + 4000 * i);
        }

        for (int i = 0; i < 3; i++) {
            Raven foe = new Raven(context, 10, 5000, Globals.CANVAS_WIDTH / 2
                    + i * 80 + 100, 100);
            addFoeAtTime(foe, 4000 + 4000 * i);
        }

        {
            Raven foe = new Raven(context, 10, 5000, Globals.CANVAS_WIDTH / 2,
                    100);
            addFoeAtTime(foe, 14000);
        }

        for (int i = 0; i < 7; i++) {
            Volt foe = new Volt(context, 7, i * Globals.CANVAS_WIDTH / 8, 400,
                    -400, Volt.Type.VERTICAL);
            addFoeAtTime(foe, 18000 + 500 * i);
        }

        for (int i = 0; i < 3; i++) {
            Volt foe = new Volt(context, 7, 300 + i * 100, 400, -400,
                    Volt.Type.HORIZONTAL_RIGHT);
            addFoeAtTime(foe, 26000 + 1000 * i);
        }

        for (int i = 0; i < 3; i++) {
            Volt foe = new Volt(context, 7, 300 + i * 100, 400, -400,
                    Volt.Type.HORIZONTAL_LEFT);
            addFoeAtTime(foe, 26000 + 1000 * i);
        }

        {
            Angel foe = new Angel(context, 100);
            foe.setPositions(Globals.CANVAS_WIDTH / 2 - foe.mBitmap.getWidth()
                    / 2, -foe.mBitmap.getHeight(), Globals.CANVAS_WIDTH / 2
                    - foe.mBitmap.getWidth() / 2, 250);
            addFoeAtTime(foe, 35000);
        }

        for (int i = 0; i < 8; i++) {
            Stingray foe1 = new Stingray(context, 10, 40,
                    Stingray.Direction.RIGHT);
            Stingray foe2 = new Stingray(context, 10, 120,
                    Stingray.Direction.LEFT);
            addFoeAtTime(foe1, 38000 + 2000 * i);
            addFoeAtTime(foe2, 38000 + 2000 * i);
        }

        FirstBoss boss = new FirstBoss(context, 1800);
        addFoeAtTime(boss, 58000);

        prepare();
    }

    @Override
    public Audio getStageTheme() {
        return mContext.getAssets().stage1_audio;
    }

    @Override
    public Audio getBossTheme() {
        return mContext.getAssets().boss1_theme;
    }

    @Override
    public Bitmap getBackground() {
        return mContext.getAssets().stage2Background;
    }

}
