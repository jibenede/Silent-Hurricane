package com.puc.soa;

import android.content.Context;
import android.graphics.PointF;

import com.puc.sh.model.Animation;
import com.puc.sh.model.Player;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.FoeArray;

public class GameState {
    private Context context;
    private AssetsHolder mAssets;

    private Player ship;

    public BulletArray bullets;
    public FoeArray enemies;
    Animation a;

    public GameState(Context context, AssetsHolder assets) {
        this.context = context;
        mAssets = assets;

        this.bullets = new BulletArray(Globals.BULLET_LIMIT);
        this.ship = new Player(context, this, assets);

        this.enemies = new FoeArray(Globals.ENEMY_LIMIT);
        //
        // Path[] path = new Path[] { new LinearPath(100, 50, 200), new
        // LinearPath(-100, 50, 200),
        // new LinearPath(-100, -50, 200), new LinearPath(100, -50, 200) };
        //
        // this.enemies.addEnemy(new Enemy(1000, 200, 50, 300, path, bullets,
        // context));
    }

    public void setDestination(int x, int y) {
        this.ship.setDestination(x, y);
    }

    public void update(long interval) {
        this.ship.update(interval);
        if (!this.ship.isAlive()) {

        }
        for (int i = 0; i < enemies.size(); i++) {
            Foe e = enemies.getEnemy(i);
            if (!e.isGone()) {
                e.update(interval);
            }

        }
        for (int i = 0; i < this.bullets.size(); i++) {
            this.bullets.getBullet(i).update(interval);
        }
        this.bullets.clean();
    }

    public PointF getShipPosition() {
        return this.ship.getShipPosition();
    }

    public BulletArray getBullets() {
        return this.bullets;
    }

    public FoeArray getEnemies() {
        return this.enemies;
    }

    public boolean isAlive() {
        return this.ship.isAlive();
    }

    public float getBossHpRatio() {
        return 0; // this.enemies.getEnemy(0).getHp() / 1000f;
    }

}
