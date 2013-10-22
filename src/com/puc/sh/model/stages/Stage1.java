package com.puc.sh.model.stages;

import android.graphics.Bitmap;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.BossA;
import com.puc.sh.model.foes.Dart;
import com.puc.sh.model.foes.PredatorA;
import com.puc.sh.model.foes.StalkerA;
import com.puc.sh.model.foes.Stingray;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class Stage1 extends Stage {

    public Stage1(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        for (int i = 0; i < 10; i++) {
            StalkerA foe = new StalkerA(context, 5, StalkerA.Origin.BOTTOM_LEFT);
            addFoeAtTime(foe, 500 * i);
        }

        for (int i = 0; i < 10; i++) {
            StalkerA foe = new StalkerA(context, 5,
                    StalkerA.Origin.BOTTOM_RIGHT);
            addFoeAtTime(foe, 7000 + 500 * i);
        }

        int xPos[] = { 200, 600, 350, 150, 650, 400, 250, 550, 100, 180, 540,
                470, 500, 300, 240, 600, 350, 150, 650, 400, 250, 550, 100 };
        for (int i = 0; i < xPos.length; i++) {
            PredatorA foe = new PredatorA(context, 7, xPos[i]);
            addFoeAtTime(foe, 15000 + 1000 * i);
        }

        for (int i = 0; i < 10; i++) {
            StalkerA foe = new StalkerA(context, 5, StalkerA.Origin.BOTTOM_LEFT);
            addFoeAtTime(foe, 35000 + 500 * i);
        }

        Dart dart = new Dart(context, 150);
        dart.setPositions(Globals.CANVAS_WIDTH / 2 - dart.mBitmap.getWidth()
                / 2, -dart.mBitmap.getHeight(), Globals.CANVAS_WIDTH / 2
                - dart.mBitmap.getWidth() / 2, 250);
        addFoeAtTime(dart, 46000);

        dart = new Dart(context, 150);
        dart.setPositions(Globals.CANVAS_WIDTH / 3 - dart.mBitmap.getWidth()
                / 2, -dart.mBitmap.getHeight(), Globals.CANVAS_WIDTH / 3
                - dart.mBitmap.getWidth() / 2, 250);
        addFoeAtTime(dart, 48000);

        dart = new Dart(context, 150);
        dart.setPositions(
                2 * Globals.CANVAS_WIDTH / 3 - dart.mBitmap.getWidth() / 2,
                -dart.mBitmap.getHeight(), 2 * Globals.CANVAS_WIDTH / 3
                        - dart.mBitmap.getWidth() / 2, 250);
        addFoeAtTime(dart, 48000);

        for (int i = 0; i < 10; i++) {
            Stingray foe1 = new Stingray(context, 10, 40,
                    Stingray.Direction.RIGHT);
            Stingray foe2 = new Stingray(context, 10, 120,
                    Stingray.Direction.LEFT);
            Stingray foe3 = new Stingray(context, 10, 200,
                    Stingray.Direction.RIGHT);
            addFoeAtTime(foe1, 50000 + 2000 * i);
            addFoeAtTime(foe2, 50000 + 2000 * i);
            addFoeAtTime(foe3, 50000 + 2000 * i);
        }

        BossA boss = new BossA(context, 1000);
        addFoeAtTime(boss, 74000);

        prepare();
    }

    @Override
    public Audio getStageTheme() {
        return mContext.getAssets().stage1_audio;
    }

    @Override
    public Audio getBossTheme() {
        return mContext.getAssets().music;
    }

    @Override
    public Bitmap getBackground() {
        return mContext.getAssets().stage2Background;
    }

}
