package com.puc.soa.utils;

import com.puc.sh.model.Renderable;

public class CircularArray {
	private int lowerBound;
	private int upperBound;
	private Renderable[] array;

	public CircularArray(int capacity) {
		array = new Renderable[capacity];

		lowerBound = upperBound = 0;
	}

	public void add(Renderable e) {
		/*
		 * if (array[upperBound] == null) array[upperBound] = new Enemy();
		 * array[upperBound].initializeBullet(b);
		 */
		array[upperBound] = e;

		upperBound = (upperBound + 1) % array.length;
	}

	public Renderable get(int index) {
		return array[(lowerBound + index) % array.length];
	}

	public void clean() {
		while (lowerBound != upperBound && !array[lowerBound].isOnScreen())
			lowerBound = (lowerBound + 1) % array.length;
	}

	public int size() {
		if (upperBound >= lowerBound)
			return upperBound - lowerBound;
		else
			return upperBound + array.length - lowerBound;
	}
}
