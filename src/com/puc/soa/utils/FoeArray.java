package com.puc.soa.utils;

import com.puc.sh.model.foes.Foe;

public class FoeArray {
	private int lowerBound;
	private int upperBound;
	private Foe[] array;

	public FoeArray(int capacity) {
		array = new Foe[capacity];

		lowerBound = upperBound = 0;
	}

	public void addEnemy(Foe e) {
		/*
		 * if (array[upperBound] == null) array[upperBound] = new Enemy();
		 * array[upperBound].initializeBullet(b);
		 */
		array[upperBound] = e;

		upperBound = (upperBound + 1) % array.length;
	}

	public Foe getEnemy(int index) {
		return array[(lowerBound + index) % array.length];
	}

	public void clean() {
		while (lowerBound != upperBound && !array[lowerBound].isGone())
			lowerBound = (lowerBound + 1) % array.length;
	}

	public int size() {
		if (upperBound >= lowerBound)
			return upperBound - lowerBound;
		else
			return upperBound + array.length - lowerBound;
	}

}
