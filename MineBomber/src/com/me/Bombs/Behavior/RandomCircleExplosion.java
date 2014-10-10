package com.me.Bombs.Behavior;

import com.me.Utility.RecyclableArray;

/**
 * Created by alekseev on 18.09.2014.
 */
public class RandomCircleExplosion extends CircleExplosion {

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

    public static class Factory implements RecyclableArray.Factory<RandomCircleExplosion> {

        @Override
        public RandomCircleExplosion newItem() {
            return new RandomCircleExplosion();
        }
    }

}
