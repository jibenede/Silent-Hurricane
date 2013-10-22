package com.puc.sh.model.stages;

import android.graphics.Bitmap;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.Apollo;
import com.puc.sh.model.foes.Eclipse;
import com.puc.sh.model.foes.Mawler;
import com.puc.sh.model.foes.Stingray;
import com.puc.sh.model.foes.Thanatos;
import com.puc.soa.AuroraContext;
import com.puc.soa.Globals;
import com.puc.soa.RenderView;

public class Stage2 extends Stage {

    public Stage2(AuroraContext context, RenderView renderer) {
        super(context, renderer);

        for (int i = 0; i < 10; i++) {
            Thanatos foe = new Thanatos(context, 10, Thanatos.Direction.RIGHT,
                    Globals.CANVAS_HEIGHT / 2);
            addFoeAtTime(foe, 1000 * i + 1000);
        }

        int[] positionsX = { 100, 620, 200, 520, 300, 400, 200, 520 };
        int[] positionsY = { 50, 50, 100, 100, 150, 150, 50, 50 };
        for (int i = 0; i < positionsX.length; i++) {
            Mawler foe = new Mawler(context, 20, i * 2000);
            if (positionsX[i] < 360) {
                foe.setPositions(positionsX[i] - foe.mBitmap.getWidth() / 2
                        - 200, -foe.mBitmap.getHeight() - 100, positionsX[i]
                        - foe.mBitmap.getWidth() / 2, positionsY[i]);
            } else {
                foe.setPositions(positionsX[i] + foe.mBitmap.getWidth() / 2
                        + 200, -foe.mBitmap.getHeight() - 100, positionsX[i]
                        - foe.mBitmap.getWidth() / 2, positionsY[i]);
            }
            addFoeAtTime(foe, 12000);

        }

        for (int i = 0; i < 30; i++) {
            Eclipse foe1 = new Eclipse(context, 12, Eclipse.Direction.RIGHT,
                    100);
            Eclipse foe2 = new Eclipse(context, 12, Eclipse.Direction.LEFT,
                    Globals.CANVAS_WIDTH - 100);

            addFoeAtTime(foe1, 14000 + i * 1000);
            addFoeAtTime(foe2, 14000 + i * 1000);
        }

        for (int i = 0; i < 4; i++) {
            Thanatos foe = new Thanatos(context, 10, Thanatos.Direction.RIGHT,
                    Globals.CANVAS_HEIGHT / 2 - 200);
            addFoeAtTime(foe, 16000 + i * 7000);
        }

        for (int i = 0; i < 10; i++) {
            Thanatos foe = new Thanatos(context, 10, Thanatos.Direction.RIGHT,
                    Globals.CANVAS_HEIGHT / 2 - 200);
            addFoeAtTime(foe, 46000 + i * 1000);
        }

        {
            Apollo foe = new Apollo(context, 200);
            foe.setPositions(Globals.CANVAS_WIDTH / 2 - foe.mBitmap.getWidth()
                    / 2, -foe.mBitmap.getHeight(), Globals.CANVAS_WIDTH / 2
                    - foe.mBitmap.getWidth() / 2, 250);
            addFoeAtTime(foe, 60000);
        }

        for (int i = 0; i < 14; i++) {
            Stingray foe1 = new Stingray(context, 10, 40,
                    Stingray.Direction.RIGHT);
            Stingray foe2 = new Stingray(context, 10, 120,
                    Stingray.Direction.LEFT);
            Stingray foe3 = new Stingray(context, 10, 200,
                    Stingray.Direction.RIGHT);
            Stingray foe4 = new Stingray(context, 10, 280,
                    Stingray.Direction.LEFT);
            addFoeAtTime(foe1, 62000 + 2000 * i);
            addFoeAtTime(foe2, 62000 + 2000 * i);
            addFoeAtTime(foe3, 62000 + 2000 * i);
            addFoeAtTime(foe4, 62000 + 2000 * i);
        }

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
