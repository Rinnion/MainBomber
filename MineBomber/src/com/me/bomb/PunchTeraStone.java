package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.TimeActivator;
import com.me.bomb.behavior.PunchTera;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class PunchTeraStone extends AbstractBomb {

    private static final AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dyn");

    public PunchTeraStone(RecyclableArray array) {
        super(array);
    }

    public PunchTeraStone update(IPlayer player, int x, int y) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(PunchTera.class).update(x, y, 0);
        activator = MemoryManager.take(TimeActivator.class).update(this, TimeActivator.DEFAULT_TIME);

        return this;
    }
}
