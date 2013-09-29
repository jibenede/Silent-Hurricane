package com.puc.soa;

import android.content.Context;

import com.puc.sh.model.Player;
import com.puc.sh.model.Player.PlayerState;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.CircularArray;

public class GameState {
	private Context context;
	private AssetsHolder mAssets;

	public Player mShip;

	private int mPlayerBombs;

	public BulletArray bullets;
	public CircularArray mEnemies;
	public CircularArray mAnimations;

	public GameState(Context context, AssetsHolder assets) {
		this.context = context;
		mAssets = assets;

		mPlayerBombs = Globals.DEFAULT_BOMBS;

		this.bullets = new BulletArray(Globals.BULLET_LIMIT);
		mShip = new Player(context, this, assets);

		mEnemies = new CircularArray(Globals.ENEMY_LIMIT);
		mAnimations = new CircularArray(Globals.ANIMATIONS_LIMIT);
	}

	public void setDestination(int x, int y) {
		mShip.setDestination(x, y);
	}

	public void update(long interval) {
		mShip.update(interval);

		for (int i = 0; i < mEnemies.size(); i++) {
			mEnemies.get(i).update(interval);
		}

		for (int i = 0; i < this.bullets.size(); i++) {
			this.bullets.getBullet(i).update(interval);
		}

		for (int i = 0; i < mAnimations.size(); i++) {
			mAnimations.get(i).update(interval);
		}

		mEnemies.clean();
		this.bullets.clean();
		mAnimations.clean();
	}

	public BulletArray getBullets() {
		return this.bullets;
	}

	public CircularArray getEnemies() {
		return mEnemies;
	}

	public boolean isPlayerContinuing() {
		return mShip.mStatus != PlayerState.DEFEATED;
	}

	public float getBossHpRatio() {
		return 0; // this.enemies.getEnemy(0).getHp() / 1000f;
	}

}
