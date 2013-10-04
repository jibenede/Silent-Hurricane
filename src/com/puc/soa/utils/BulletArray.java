package com.puc.soa.utils;

import com.puc.sh.model.bullets.Bullet;
import com.puc.soa.AuroraContext;

public class BulletArray {
    private AuroraContext mContext;
    private int lowerBound;
    private int upperBound;
    private Bullet[] array;

    public BulletArray(AuroraContext context, int capacity) {
        mContext = context;
        array = new Bullet[capacity];

        lowerBound = upperBound = 0;
    }

    public void addBullet(Bullet b) {
        if (mContext.getState().mBombActivated) {
            return;
        }

        if (array[upperBound] == null)
            array[upperBound] = new Bullet(mContext);
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

    public void emptyArray() {
        lowerBound = upperBound;
    }

}
