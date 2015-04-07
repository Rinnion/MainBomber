package com.me.Bombs;

import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
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

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {
        if (time < ActivationTime) return false;
        super.calculate(time);
        return true;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

}
