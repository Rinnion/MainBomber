package com.me.Bombs.Behavior;

import com.me.Bombs.Vector2IDamage;

/**
 * Created by alekseev on 18.09.2014.
 */
public class RandomCircleExplosion extends CircleExplosion {


    public Vector2IDamage[] ExplodeMask;

    public RandomCircleExplosion() {
        super(getMinDamage(), getMaxDamage(), getRange());
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

}
