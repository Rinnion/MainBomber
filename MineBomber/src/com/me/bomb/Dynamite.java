package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.TimeActivator;
import com.me.bomb.behavior.CircleExplosion;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class Dynamite extends AbstractBomb {
    AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dyn");

    public Dynamite(RecyclableArray array) {
        super(array);
    }

    public Dynamite update(IPlayer player, int x, int y, long activationTime) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(CircleExplosion.class).update(100, 200, 24);
        activator = MemoryManager.take(TimeActivator.class).update(this, activationTime);

        return this;
    }

}
