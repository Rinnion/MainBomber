package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.RandomTimeActivator;
import com.me.bomb.behavior.JumpBehavior;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public final class RandomBomb extends AbstractBomb {

    private static AnimatedSprite animatedSprite=null;

    static
    {
        if(animatedSprite==null)
            animatedSprite= AnimatedSprite.FactoryMethos.CreateBomb("bomb");
    }

    public RandomBomb(RecyclableArray array) {
        super(array);
    }

    public RandomBomb update(IPlayer player, int x, int y, int jumps, int radius) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(JumpBehavior.class).update(jumps, radius);
        activator = MemoryManager.take(RandomTimeActivator.class).update(this);

        return this;
    }
}
