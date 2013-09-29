package com.puc.sh.model.bullets;

import android.graphics.Bitmap;
import android.graphics.PointF;

public class Bullet {
	public enum BulletShape {
		Round, Square
	}

	public enum BulletType {
		Linear, Cluster, Curve, TwoStep
	};

	public boolean mBenign;

	private float mSpeedX;
	private float mSpeedY;
	private long mLifetime;
	public BulletType mType;
	public BulletShape mShape;

	public PointF mPosition;
	public boolean mDisplay;

	public float mSize;
	public float mSize2;

	public Bitmap mBitmap;

	public int mFirepower;

	public Bullet() {
		mPosition = new PointF();
	}

	public void initializeBullet(Bullet b/*
										 * boolean benign, float speedX, float
										 * speedY, PointF position, long
										 * lifetime, BulletType type
										 */) {
		mBenign = b.mBenign;
		mSpeedX = b.mSpeedX;
		mSpeedY = b.mSpeedY;
		mPosition.x = b.mPosition.x;
		mPosition.y = b.mPosition.y;
		mLifetime = b.mLifetime;
		mType = b.mType;
		mShape = b.mShape;
		mDisplay = b.mDisplay;

		mSize = b.mSize;
		mSize2 = b.mSize2;

		mBitmap = b.mBitmap;
		mFirepower = b.mFirepower;
	}

	/**
	 * 
	 * @param bitmap
	 *            The bitmap graphic associated with this bullet.
	 * @param benign
	 *            {@code true} if this bullet should harm foes, {@code false} if
	 *            it should harm the player
	 * @param speedX
	 *            The speed along the X axis of this bullet.
	 * @param speedY
	 *            The speed along the Y axis of this bullet.
	 * @param positionX
	 *            The starting X position.
	 * @param positionY
	 *            The starting Y position.
	 * @param lifetime
	 *            The amount of milliseconds this bullet should remain in memory
	 *            before being collected by the engine. This parameter is vital
	 *            for optimization purposes.
	 * @param size
	 *            The size of this bullet. For collisions, this would be the
	 *            radius of the bounding circle.
	 * @param firepower
	 */
	public void initializeLinearBullet(Bitmap bitmap, boolean benign, float speedX, float speedY,
			float positionX, float positionY, long lifetime, float size, int firepower) {
		mType = BulletType.Linear;
		mShape = BulletShape.Round;

		mBenign = benign;
		mSpeedX = speedX;
		mSpeedY = speedY;
		mPosition.x = positionX;
		mPosition.y = positionY;
		mLifetime = lifetime;
		mDisplay = true;
		mFirepower = firepower;

		mSize = mSize2 = size;

		mBitmap = bitmap;
	}

	public void update(long interval) {
		if (mDisplay) {
			mLifetime -= interval;
			if (mLifetime <= 0) {
				mDisplay = false;
			}

			mPosition.x += mSpeedX * interval / 1000;
			mPosition.y += mSpeedY * interval / 1000;
		}
	}

	public int getFirepower() {
		return 1;
	}

}
