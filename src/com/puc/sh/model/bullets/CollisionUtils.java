package com.puc.sh.model.bullets;

import com.puc.sh.model.Player;

public class CollisionUtils {
	public static boolean playerBulletCollide(Player player, Bullet bullet) {
		float hitboxX = player.mShipPosition.x + player.mBitmap.getWidth() / 2;
		float hitboxY = player.mShipPosition.y + player.mBitmap.getHeight() / 2;

		if ((hitboxX - (bullet.mPosition.x + bullet.mSize / 2))
				* (hitboxX - (bullet.mPosition.x + bullet.mSize / 2))
				+ (hitboxY - (bullet.mPosition.y + bullet.mSize / 2))
				* (hitboxY - (bullet.mPosition.y + bullet.mSize / 2)) < (bullet.mSize / 2)
				* (bullet.mSize / 2)) {
			return true;

		}
		return false;

	}

	public static boolean circleCollide(float c1_x, float c1_y, float c1_r, float c2_x, float c2_y,
			float c2_r) {
		return (c1_x - c2_x) * (c1_x - c2_x) + (c1_y - c2_y) * (c1_y - c2_y) < (c1_r + c2_r)
				* (c1_r + c2_r);
	}

	public static boolean rectCollide(float r1_top, float r1_right, float r1_bottom, float r1_left,
			float r2_top, float r2_right, float r2_bottom, float r2_left) {
		return !(r2_left > r1_right || r2_right < r1_left || r2_top > r1_bottom || r2_bottom < r1_top);
	}

}
