package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.TimeActivator;
import com.me.bomb.behavior.Teramorf;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class FilledBomb extends AbstractBomb {

    private static final AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dyn");

    public FilledBomb(RecyclableArray array) {
        super(array);
    }

    public FilledBomb update(IPlayer player, int x, int y) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(Teramorf.class).update(x, y, 81);
        activator = MemoryManager.take(TimeActivator.class).update(this, 3000);

        return this;
    }

}
