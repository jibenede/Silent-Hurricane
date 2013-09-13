package com.puc.sh.model.bullets;

import com.puc.sh.model.Sprite;

public class Bullet extends Sprite {
	public enum BulletType {
		Plasma, Laser1, Laser2, Laser3
	};

	private boolean benign;

	private float speedX;
	private float speedY;
	private long lifetime;
	private BulletType type;

	public void initializeBullet(Bullet b/*
										 * boolean benign, float speedX, float
										 * speedY, PointF position, long
										 * lifetime, BulletType type
										 */) {
		this.benign = b.benign;
		this.speedX = b.speedX;
		this.speedY = b.speedY;
		this.positionX = b.positionX;
		this.positionY = b.positionY;
		this.lifetime = b.lifetime;
		this.type = b.type;
		this.render = true;

		this.sizeX = b.sizeX;
		this.sizeY = b.sizeY;
	}

	public void initializeBullet(boolean benign, float speedX, float speedY, float positionX,
			float positionY, long lifetime, BulletType type, float sizeX, float sizeY) {
		this.benign = benign;
		this.speedX = speedX;
		this.speedY = speedY;
		this.positionX = positionX;
		this.positionY = positionY;
		this.lifetime = lifetime;
		this.type = type;
		this.render = true;

		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}

	public void update(long interval) {
		if (this.render) {
			lifetime -= interval;
			if (lifetime <= 0)
				this.render = false;

			this.positionX += this.speedX * interval / 1000;
			this.positionY += this.speedY * interval / 1000;
		}
	}

	public BulletType getType() {
		return this.type;
	}

	public boolean isBenign() {
		return this.benign;
	}

	public int getFirepower() {
		return 1;
	}

	@Override
	public float getX() {
		return positionX - sizeX / 2;
	}

	@Override
	public float getY() {
		return positionY - sizeY / 2;
	}

}
