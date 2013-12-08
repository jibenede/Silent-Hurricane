package com.puc.soa;

import android.content.Context;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Player;
import com.puc.sh.model.Player.PlayerState;
import com.puc.sh.model.Powerup;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.sh.model.stages.Stage;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.CircularArray;
import com.puc.soa.utils.Utils;

public class GameState {
	private static final int POWERUP_INTERVAL = 20000;

	public AuroraContext mContext;
	private AssetsHolder mAssets;

	public Player mShip;

	public BulletArray mEnemyBullets;
	public BulletArray mPlayerBullets;
	public CircularArray mSpecialBullets;

	public long mScore;

	public CircularArray mEnemies;
	public CircularArray mAnimations;
	public CircularArray mPowerups;

	public Bullet mBullet;

	private Stage mCurrentStage;

	public boolean mBombActivated;

	private long mTimeOfLastBomb;
	private long mTimeForNextPowerup;
	public long mTimeWhenBossWasDefeated;

	private long mTicks;

	public GameState(Context context, AssetsHolder assets, PersistanceManager manager) {
		mAssets = assets;

		mContext = new AuroraContext(context, this, assets, manager);

		mEnemyBullets = new BulletArray(mContext, Globals.BULLET_LIMIT);
		mPlayerBullets = new BulletArray(mContext, 200);
		mSpecialBullets = new CircularArray(100);

		mShip = new Player(mContext);

		mEnemies = new CircularArray(Globals.ENEMY_LIMIT);
		mAnimations = new CircularArray(Globals.ANIMATIONS_LIMIT);
		mPowerups = new CircularArray(10);

		mBullet = new Bullet(mContext);

		reset();
	}

	public void reset() {
		mEnemyBullets.emptyArray();
		mPlayerBullets.emptyArray();
		mSpecialBullets.emptyArray();
		mAnimations.emptyArray();
		mEnemies.emptyArray();
		mPowerups.emptyArray();

		mTicks = 0;
		mTimeOfLastBomb = 0;
		mTimeForNextPowerup = POWERUP_INTERVAL;
		mScore = 0;
		mBombActivated = false;
		mTimeWhenBossWasDefeated = 0;

		mShip.reset();
		if (mCurrentStage != null) {
			mCurrentStage.reset();
		}

	}

	public void bossDefeated() {
		mTimeWhenBossWasDefeated = mTicks;
	}

	public Stage getCurrentStage() {
		return mCurrentStage;
	}

	public void setCurrentStage(Stage stage) {
		mCurrentStage = stage;
	}

	public void setDestination(int x, int y) {
		mShip.setDestination(x, y);
	}

	public void update(long interval) {
		mTicks += interval;

		mTimeForNextPowerup -= interval;
		if (mTimeForNextPowerup < 0 && !mCurrentStage.hasBossAppeared()) {
			Powerup powerup = new Powerup(mContext,
					50 + Utils.sRandom.nextInt(Globals.CANVAS_WIDTH - 100));
			mPowerups.add(powerup);

			mTimeForNextPowerup = POWERUP_INTERVAL;
		}

		if (mBombActivated) {
			animateBomb();
			animateBomb();
			if (mTicks - mTimeOfLastBomb > 1000) {
				mBombActivated = false;
			}
		}

		mShip.update(interval);

		for (int i = 0; i < mEnemies.size(); i++) {
			mEnemies.get(i).update(interval);
		}

		for (int i = 0; i < mEnemyBullets.size(); i++) {
			mEnemyBullets.getBullet(i).update(interval);
		}

		for (int i = 0; i < mPlayerBullets.size(); i++) {
			mPlayerBullets.getBullet(i).update(interval);
		}

		for (int i = 0; i < mSpecialBullets.size(); i++) {
			mSpecialBullets.get(i).update(interval);
		}

		for (int i = 0; i < mAnimations.size(); i++) {
			mAnimations.get(i).update(interval);
		}

		for (int i = 0; i < mPowerups.size(); i++) {
			mPowerups.get(i).update(interval);
		}

		mEnemies.clean();
		mEnemyBullets.clean();
		mPlayerBullets.clean();
		mSpecialBullets.clean();
		mAnimations.clean();
	}

	private void animateBomb() {
		int x = -150 + Utils.sRandom.nextInt(Globals.CANVAS_WIDTH);
		int y = -150 + Utils.sRandom.nextInt(Globals.CANVAS_HEIGHT);
		Animation a = new Animation(mContext, mAssets.bomb, 50, x, y);
		a.prepare();
		mAnimations.add(a);
	}

	public void detonateBomb() {
		mEnemyBullets.emptyArray();
		mSpecialBullets.emptyArray();
		for (int i = 0; i < mEnemies.size(); i++) {
			((Foe) mEnemies.get(i)).bomb();
		}

		mBombActivated = true;
		mTimeOfLastBomb = mTicks;
	}

	public CircularArray getEnemies() {
		return mEnemies;
	}

	public boolean isPlayerContinuing() {
		return mShip.mStatus != PlayerState.DEFEATED;
	}
}
