package com.puc.sh.model.foes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

import com.puc.sh.model.bullets.Bullet;
import com.puc.soa.AssetsHolder;
import com.puc.soa.GameState;
import com.puc.soa.utils.BulletArray;

public abstract class Foe {
	protected Context mContext;
	protected GameState mState;
	protected AssetsHolder mAssets;
	protected int mHp;
	protected Point mSize;

	private float X = Float.MIN_VALUE;
	private float Y = Float.MIN_VALUE;

	protected static Rect sFoeRect;
	protected static Rect sBulletRect;
	protected static Bullet sBullet;

	static {
		sFoeRect = new Rect();
		sBulletRect = new Rect();
		sBullet = new Bullet();
	}

	public Foe(Context context, GameState state, AssetsHolder assets, int hp) {
		mContext = context;
		mState = state;
		mAssets = assets;
		mHp = hp;

		WindowManager wManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		Display display = wManager.getDefaultDisplay();
		mSize = new Point();
		display.getSize(mSize);
	}

	public abstract PointF getPosition();

	public float getX() {
		return getPosition().x - getBitmap().getWidth() / 2;
	}

	public float getY() {
		return getPosition().y - getBitmap().getHeight() / 2;
	}

	public void update(long interval) {
		updatePosition(interval);
		fireBullets(interval);
		hitTest();
	}

	public abstract Bitmap getBitmap();

	public abstract float getAngle();

	public abstract boolean isGone();

	protected abstract void updatePosition(long interval);

	protected void hitTest() {
		BulletArray bullets = mState.getBullets();
		Bullet b;
		for (int i = 0; i < bullets.size(); i++) {
			b = bullets.getBullet(i);

			if (b.isBenign() && b.getRender() && collidesWith(b)) {
				mHp -= b.getFirepower();
				b.render = false;
			}
		}
	}

	public abstract boolean collidesWith(Bullet b);

	public abstract void fireBullets(long interval);

	public boolean isBoss() {
		return false;
	}
}
