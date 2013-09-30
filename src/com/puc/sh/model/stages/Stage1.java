package com.puc.sh.model.stages;

import android.content.Context;

import com.puc.sh.model.Audio;
import com.puc.sh.model.foes.BossA;
import com.puc.sh.model.foes.PredatorA;
import com.puc.sh.model.foes.StalkerA;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.RenderView;

public class Stage1 extends Stage {

	public Stage1(Context context, GameState state, AssetsHolder assets, RenderView renderer) {
		super(context, state, assets, renderer);

		for (int i = 0; i < 10; i++) {
			StalkerA foe = new StalkerA(context, state, assets, 5, StalkerA.Origin.BOTTOM_LEFT);
			addFoeAtTime(foe, 500 * i);
		}

		for (int i = 0; i < 10; i++) {
			StalkerA foe = new StalkerA(context, state, assets, 5, StalkerA.Origin.BOTTOM_RIGHT);
			addFoeAtTime(foe, 7000 + 500 * i);
		}

		int xPos[] = { 200, 600, 350, 150, 650, 400, 250, 550, 100, 180, 540, 470, 500, 300, 240,
				600, 350, 150, 650, 400, 250, 550, 100 };
		for (int i = 0; i < xPos.length; i++) {
			PredatorA foe = new PredatorA(context, state, assets, 7, xPos[i]);
			addFoeAtTime(foe, 15000 + 1000 * i);
		}

		for (int i = 0; i < 10; i++) {
			StalkerA foe = new StalkerA(context, state, assets, 5, StalkerA.Origin.BOTTOM_LEFT);
			addFoeAtTime(foe, 35000 + 500 * i);
		}

		BossA boss = new BossA(context, state, assets, 1000);
		addFoeAtTime(boss, 45000);

		prepare();
	}

	@Override
	public Audio getStageTheme() {
		return mAssets.stage1_audio;
	}

	@Override
	public Audio getBossTheme() {
		return mAssets.music;
	}

}
