package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;
import com.puc.soa.utils.Utils;

public class Pattern_M extends BulletPattern {
	private static int BULLET_SPEED = 80;
	private static int BULLET_INTERVAL = 160;

	private long mTimeUntilNextShot;

	public Pattern_M(Foe foe, AuroraContext context) {
		super(foe, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(long interval) {
		mTimeUntilNextShot -= interval;
		if (mTimeUntilNextShot < 0) {
			int x = Utils.sRandom.nextInt(700);
			int speed = (int) (BULLET_SPEED + 40 * Utils.sRandom.nextDouble());

			mBullet.initializeLinearBullet(mContext.getAssets().redBullet36, false, 0, speed, x, 0,
					0, 36, 1);
			mContext.getState().mEnemyBullets.addBullet(mBullet);

			x = Utils.sRandom.nextInt(700);
			speed = (int) (BULLET_SPEED + 40 * Utils.sRandom.nextDouble());

			mBullet.initializeLinearBullet(mContext.getAssets().yellowBullet24, false, 0, speed, x,
					0, 0, 24, 1);
			mContext.getState().mEnemyBullets.addBullet(mBullet);

			x = Utils.sRandom.nextInt(700);
			speed = (int) (BULLET_SPEED + 40 * Utils.sRandom.nextDouble());

			mBullet.initializeLinearBullet(mContext.getAssets().whiteBullet48, false, 0, speed, x,
					0, 0, 48, 1);
			mContext.getState().mEnemyBullets.addBullet(mBullet);

			mTimeUntilNextShot = BULLET_INTERVAL;
		}

	}

}
