package com.puc.soa;

import android.content.Context;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Player;
import com.puc.sh.model.Player.PlayerState;
import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.sh.model.stages.Stage;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.CircularArray;
import com.puc.soa.utils.Utils;

public class GameState {
    private AuroraContext mContext;
    private AssetsHolder mAssets;

    public Player mShip;

    private int mPlayerBombs;

    public BulletArray mEnemyBullets;
    public BulletArray mPlayerBullets;

    public long mScore;

    public CircularArray mEnemies;
    public CircularArray mAnimations;

    public Bullet mBullet;

    private Stage mCurrentStage;

    public boolean mBombActivated;

    private long mTimeOfLastBomb;

    public GameState(Context context, AssetsHolder assets) {
        mAssets = assets;

        mPlayerBombs = Globals.DEFAULT_BOMBS;

        mContext = new AuroraContext(context, this, assets);

        mEnemyBullets = new BulletArray(mContext, Globals.BULLET_LIMIT);
        mPlayerBullets = new BulletArray(mContext, 200);

        mShip = new Player(mContext);

        mEnemies = new CircularArray(Globals.ENEMY_LIMIT);
        mAnimations = new CircularArray(Globals.ANIMATIONS_LIMIT);

        mBullet = new Bullet(mContext);
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
        if (mBombActivated) {
            animateBomb();
            animateBomb();
            if (System.currentTimeMillis() - mTimeOfLastBomb > 1000) {
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

        for (int i = 0; i < mAnimations.size(); i++) {
            mAnimations.get(i).update(interval);
        }

        mEnemies.clean();
        mEnemyBullets.clean();
        mPlayerBullets.clean();
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
        for (int i = 0; i < mEnemies.size(); i++) {
            ((Foe) mEnemies.get(i)).bomb();
        }

        mBombActivated = true;
        mTimeOfLastBomb = System.currentTimeMillis();
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
