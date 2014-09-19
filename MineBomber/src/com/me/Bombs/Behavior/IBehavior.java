package com.me.Bombs.Behavior;

import com.me.Bombs.AbstractBomb;

/**
 * Created by alekseev on 18.09.2014.
 */
public interface IBehavior {
    void detonate(AbstractBomb bomb,long time);

}
