package com.puc.sh.model;

import android.content.Context;
import android.graphics.Rect;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.Bullet.BulletType;
import com.puc.soa.Globals;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.Utilities;

public class Enemy extends Sprite {
	private Context context;

	private int sizeX;
	private int sizeY;

	private int HP;
	private int timeSinceLastBullet;

	private int bulletInterval;
	private Path[] paths;
	private int currentPath = 0;

	private BulletArray bullets;
	private Bullet b;

	private Rect self;
	private Rect bulletRect;

	public Enemy(int HP, float positionX, float positionY, int bulletInterval, Path[] paths,
			BulletArray bullets, Context context) {
		this.HP = HP;
		this.positionX = positionX;
		this.positionY = positionY;
		this.bulletInterval = bulletInterval;
		this.paths = paths;
		this.bullets = bullets;
		this.render = render;
		this.b = new Bullet();

		this.sizeX = (int) Utilities.dimToPx(Globals.BOSS_SIZE_X, context);
		this.sizeY = (int) Utilities.dimToPx(Globals.BOSS_SIZE_Y, context);

		self = new Rect();
		bulletRect = new Rect();

		this.context = context;
	}

	public void update(long interval) {
		paths[currentPath].update(interval);

		this.positionX += paths[currentPath].getDeltaX();
		this.positionY += paths[currentPath].getDeltaY();

		if (paths[currentPath].isFinished()) {
			paths[currentPath].restart();
			currentPath = (currentPath + 1) % paths.length;
		}

		timeSinceLastBullet += interval;
		if (timeSinceLastBullet > this.bulletInterval) {
			timeSinceLastBullet = 0;
			this.fireBullets();
		}

		this.hitTest();
	}

	private void fireBullets() {
		for (int i = 0; i < 40; i++) {
			double angle = 2 * Math.PI * (i / 40f);

			b.initializeBullet(false, (int) (Math.cos(angle) * 200), (int) (Math.sin(angle) * 200),
					this.positionX + this.sizeX / 2, this.positionY + this.sizeY / 2, 6000,
					BulletType.Laser1, Utilities.dimToPx(Globals.LASER_SIZE, context),
					Utilities.dimToPx(Globals.LASER_SIZE, context));
			this.bullets.addBullet(b);
		}
	}

	private void hitTest() {
		Bullet b;
		self.set((int) this.positionX, (int) this.positionY, (int) (this.positionX + this.sizeX),
				(int) (this.positionY + this.sizeY));

		for (int i = 0; i < this.bullets.size(); i++) {
			b = this.bullets.getBullet(i);

			bulletRect.set((int) b.getX(), (int) b.positionY, (int) (b.positionX + b.sizeX),
					(int) (b.positionY + b.sizeY));

			if (b.isBenign() && b.getRender() && self.intersect(bulletRect)
			// b.getX() > this.positionX + 10 && b.getX() + b.getSizeX() <
			// this.positionX + this.sizeX - 10 &&
			// b.getY() > this.positionY + 10 && b.getY() + b.getSizeY() <
			// this.positionY + this.sizeY - 10
			) {
				if (this.HP > 0)
					this.HP--;
				b.render = false;
			}
		}
	}

	public int getHp() {
		return this.HP;
	}
}
