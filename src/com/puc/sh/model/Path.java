package com.puc.sh.model;

public abstract class Path {
	public abstract float getDeltaX();

	public abstract float getDeltaY();

	public abstract void update(long interval);

	public abstract boolean isFinished();

	public abstract void restart();

}
