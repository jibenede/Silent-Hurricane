package com.puc.sh.model;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.TypedValue;
import android.view.WindowManager;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.bullets.Bullet.BulletType;
import com.puc.soa.Globals;
import com.puc.soa.utils.BulletArray;
import com.puc.soa.utils.Utilities;

public class Player {
	private Context context;

	private boolean alive;

	private int sizeX;
	private int sizeY;
	private int bulletSize;

	private PointF shipPosition;
	private PointF shipDestination;

	private int life;
	private int timeSinceLastBullet;
	private int timeSinceDeath;

	private BulletArray bulletArray;
	private Bullet b;

	public Player(Context context, BulletArray array) {
		this.context = context;

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;

		int shipX = (width - Globals.SHIP_SIZE) / 2;
		int shipY = height - Globals.SHIP_SIZE - 50;
		shipPosition = new PointF(shipX, shipY);
		shipDestination = new PointF(-1, -1);

		this.life = 10;
		this.timeSinceLastBullet = 0;
		this.bulletArray = array;
		this.b = new Bullet();

		this.sizeX = this.sizeY = (int) Utilities.dimToPx(Globals.SHIP_SIZE, context);
		this.bulletSize = (int) Utilities.dimToPx(Globals.BULLET_SIZE, context);

		this.alive = true;
	}

	public void update(long interval) {
		if (this.alive) {
			this.updateShipPosition(interval);

			timeSinceLastBullet += interval;
			if (timeSinceLastBullet > Globals.BULLET_INTERVAL) {
				timeSinceLastBullet = 0;
				this.fireBullets();
			}

			if (this.hitTest())
				this.die();
		} else
			timeSinceDeath += interval;
	}

	private void die() {
		this.alive = false;
	}

	private void fireBullets() {
		float x = shipPosition.x + (this.sizeX - this.bulletSize) / 2;

		b.initializeBullet(true, -50, Globals.SHIP_BULLET_Y_SPEED, x, shipPosition.y, 3000,
				BulletType.Plasma, this.bulletSize, this.bulletSize);
		bulletArray.addBullet(b);

		b.initializeBullet(true, 0, Globals.SHIP_BULLET_Y_SPEED, x, shipPosition.y, 3000,
				BulletType.Plasma, this.bulletSize, this.bulletSize);
		bulletArray.addBullet(b);

		b.initializeBullet(true, 50, Globals.SHIP_BULLET_Y_SPEED, x, shipPosition.y, 3000,
				BulletType.Plasma, this.bulletSize, this.bulletSize);
		bulletArray.addBullet(b);
	}

	public void setDestination(int x, int y) {
		int deltaY = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context
				.getResources().getDisplayMetrics());
		if (x != -1 && y != -1)
			shipDestination.set(x - this.sizeX / 4, y - deltaY);
		else
			shipDestination.set(x, y);
	}

	public PointF getShipPosition() {
		return this.shipPosition;
	}

	public boolean isAlive() {
		return this.alive;
	}

	private void updateShipPosition(long interval) {
		if (shipDestination.x != -1 && shipDestination.y != -1) {
			float xDelta = shipDestination.x - shipPosition.x;
			float yDelta = shipDestination.y - shipPosition.y;

			float delta = FloatMath.sqrt(xDelta * xDelta + yDelta * yDelta);

			float xSpeed = delta < 1 ? 0 : xDelta / delta * Globals.MAX_SPEED * interval / 1000;
			float ySpeed = delta < 1 ? 0 : yDelta / delta * Globals.MAX_SPEED * interval / 1000;

			if (xDelta >= 0)
				shipPosition.x += Math.min(xSpeed, xDelta);
			else
				shipPosition.x += Math.max(xSpeed, xDelta);

			if (yDelta >= 0)
				shipPosition.y += Math.min(ySpeed, yDelta);
			else
				shipPosition.y += Math.max(ySpeed, yDelta);
		}
	}

	private boolean hitTest() {
		Bullet b;

		float hitboxX = this.shipPosition.x + this.sizeX / 2;
		float hitboxY = this.shipPosition.y + this.sizeY / 2;
		for (int i = 0; i < this.bulletArray.size(); i++) {
			b = this.bulletArray.getBullet(i);

			if (!b.isBenign() && b.getX() < hitboxX && b.getX() + b.getSizeX() > hitboxX
					&& b.getY() < hitboxY && b.getY() + b.getSizeY() > hitboxY) {
				return true;
			}
		}
		return false;
	}

}
