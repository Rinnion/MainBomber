package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;
import com.me.Utility.RecyclableArray;

/**
 * Created by tretyakov on 26.09.2014.
 */
public class RandomTimeActivator extends TimeActivator {

    private static long getRandomTime() {
        return ((long) (Math.random() * (CASUAL_MAX_TIME - CASUAL_MIN_TIME))) + CASUAL_MIN_TIME;
    }

    public RandomTimeActivator update(AbstractBomb bomb) {
        super.update(bomb, getRandomTime());
        return this;
    }

    public static class Factory implements RecyclableArray.Factory<RandomTimeActivator> {

        @Override
        public RandomTimeActivator newItem() {
            return new RandomTimeActivator();
        }
    }
}
