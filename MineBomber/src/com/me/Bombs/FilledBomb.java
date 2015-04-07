package com.me.Bombs;

import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.Teramorf;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class FilledBomb extends AbstractBomb {

    private static final AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dyn");
    private int delay = 2000;

    public FilledBomb(RecyclableArray array) {
        super(array);
    }

    public FilledBomb update(IPlayer player, int x, int y) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(Teramorf.class).update(x, y, 81);
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
        ActivationTime = time + delay;
        //iteration--;
        return false;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

}
