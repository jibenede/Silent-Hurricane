package com.puc.sh.model;

public class LinearPath extends Path {
	private float destX;
	private float destY;

	private float positionX = 0;
	private float positionY = 0;

	private float lastPositionX = 0;
	private float lastPositionY = 0;

	private float speedX;
	private float speedY;

	public LinearPath(float destX, float destY, float speed) {
		this.destX = destX;
		this.destY = destY;

		double delta = Math.sqrt(destY * destY + destX * destX);

		this.speedX = (float) (destX / delta * speed);
		this.speedY = (float) (destY / delta * speed);
	}

	@Override
	public float getDeltaX() {
		if (destX >= 0)
			return Math.min(this.positionX - this.lastPositionX, destX - this.lastPositionX);
		else
			return Math.max(this.positionX - this.lastPositionX, destX - this.lastPositionX);
	}

	@Override
	public float getDeltaY() {
		if (destY >= 0)
			return Math.min(this.positionY - this.lastPositionY, destY - this.lastPositionY);
		else
			return Math.max(this.positionY - this.lastPositionY, destY - this.lastPositionY);
	}

	@Override
	public void update(long interval) {
		lastPositionX = positionX;
		lastPositionY = positionY;

		positionX += this.speedX * interval / 1000;
		positionY += this.speedY * interval / 1000;

	}

	@Override
	public boolean isFinished() {
		return (this.destX >= 0 ? this.positionX > destX : this.positionX < destX)
				|| (this.destY >= 0 ? this.positionY > destY : this.positionY < destY);
	}

	@Override
	public void restart() {
		this.positionX = this.positionY = this.lastPositionX = this.lastPositionY = 0;
	}

}
