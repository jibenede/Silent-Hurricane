package com.puc.sh.model.bullets.patterns;

import com.puc.sh.model.bullets.Bullet;
import com.puc.sh.model.foes.Foe;
import com.puc.soa.AuroraContext;

public abstract class BulletPattern {
    protected AuroraContext mContext;
    protected Foe mFoe;

    protected Bullet mBullet;

    public BulletPattern(Foe foe, AuroraContext context) {
        mContext = context;
        mFoe = foe;
        mBullet = new Bullet(context);
    }

    public abstract void update(long interval);
}
