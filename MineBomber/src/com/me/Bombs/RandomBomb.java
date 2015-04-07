package com.me.Bombs;

import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
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
