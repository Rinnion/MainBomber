package com.me.Bombs;

import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.FastTeramorf;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class FastFilledBomb extends AbstractBomb {

    AnimatedSprite animatedSprited = AnimatedSprite.FactoryMethos.CreateBomb("dyn");

    public FastFilledBomb(RecyclableArray array) {
        super(array);
    }

    public FastFilledBomb update(IPlayer player, int x, int y) {

        super.update(player, x, y, animatedSprited);

        behavior = MemoryManager.take(FastTeramorf.class).update(x, y, 82);
        activator = MemoryManager.take(TimeActivator.class).update(this, 3000);

        return this;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {

        if (time < ActivationTime) return false;
        super.calculate(time);
        //ActivationTime =time+10;
        return true;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

}
