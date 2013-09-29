package com.puc.soa.utils;

import com.puc.sh.model.bullets.Bullet;

public class BulletArray {
	private int lowerBound;
	private int upperBound;
	private Bullet[] array;

	public BulletArray(int capacity) {
		array = new Bullet[capacity];

		lowerBound = upperBound = 0;
	}

	public void addBullet(Bullet b) {
		if (array[upperBound] == null)
			array[upperBound] = new Bullet();
		array[upperBound].initializeBullet(b);

		upperBound = (upperBound + 1) % array.length;
	}

	public Bullet getBullet(int index) {
		return array[(lowerBound + index) % array.length];
	}

	public void clean() {
		while (lowerBound != upperBound && !array[lowerBound].mDisplay)
			lowerBound = (lowerBound + 1) % array.length;
	}

	public int size() {
		if (upperBound >= lowerBound)
			return upperBound - lowerBound;
		else
			return upperBound + array.length - lowerBound;
	}

}
