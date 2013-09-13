package com.puc.sh.model;

public abstract class Sprite {
	public float positionX;
	public float positionY;

	public float sizeX;
	public float sizeY;

	public boolean render;

	public boolean getRender() {
		return render;
	}

	public float getX() {
		return this.positionX;
	}

	public float getY() {
		return this.positionY;
	}

	public float getSizeX() {
		return this.sizeX;
	}

	public float getSizeY() {
		return this.sizeY;
	}

}
