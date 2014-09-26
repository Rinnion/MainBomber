package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;

/**
 * Created by tretyakov on 26.09.2014.
 */
public class RandomTimeActivator extends TimeActivator {

    public RandomTimeActivator(AbstractBomb bomb) {
        super(bomb, getRandomTime());
    }

    private static long getRandomTime() {
        return ((long) (Math.random() * (CASUAL_MAX_TIME - CASUAL_MIN_TIME))) + CASUAL_MIN_TIME;
    }
}
