package com.me.Bombs.Behavior;

import com.me.Utility.RecyclableArray;

public class RandomCircleExplosion extends CircleExplosion {

    public RandomCircleExplosion(RecyclableArray array) {
        super(array);
    }

    private static int getMinDamage() {
        return MIN_DAMAGE;
    }

    private static int getMaxDamage() {
        return MAX_DAMAGE;
    }

    private static int getRange() {
        return ((int) (Math.random() * (MAX_RADIUS - MIN_RADIUS))) + MIN_RADIUS;
    }

    public RandomCircleExplosion update() {
        super.update(getMinDamage(), getMaxDamage(), getRange());
        return this;
    }

}
