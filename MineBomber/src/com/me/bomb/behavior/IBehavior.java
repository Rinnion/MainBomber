package com.me.bomb.behavior;

import com.me.bomb.AbstractBomb;

/**
 * Created by alekseev on 18.09.2014.
 */
public interface IBehavior {
    boolean detonate(AbstractBomb bomb, long time);
    //boolean damage(AbstractBomb bomb,IPlayer who, int dmg, long time);
    //boolean take(AbstractBomb bomb,IPlayer who, long time);
    //boolean dig(AbstractBomb bomb,IPlayer who, long time);

}
