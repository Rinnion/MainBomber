package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;
import com.me.Utility.RecyclableArray;

public class RandomTimeActivator extends TimeActivator {

    public RandomTimeActivator(RecyclableArray array) {
        super(array);
    }

    private static long getRandomTime() {
        return ((long) (Math.random() * (CASUAL_MAX_TIME - CASUAL_MIN_TIME))) + CASUAL_MIN_TIME;
    }

    public RandomTimeActivator update(AbstractBomb bomb) {
        super.update(bomb, getRandomTime());
        return this;
    }

}
