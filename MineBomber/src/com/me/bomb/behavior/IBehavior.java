package com.me.bomb.behavior;

import com.me.bomb.AbstractBomb;

/**
 * Created by alekseev on 18.09.2014.
 */
public interface IBehavior {
    boolean detonate(AbstractBomb bomb, long time);

}
