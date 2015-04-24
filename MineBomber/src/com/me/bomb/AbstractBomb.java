package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.RandomTimeActivator;
import com.me.bomb.activator.RecyclableActivator;
import com.me.bomb.behavior.RecyclableBehavior;
import com.me.minebomber.AbstractGameObject;

/**
 * Created by tretyakov on 09.04.2014.
 *
 */
public abstract class AbstractBomb extends AbstractGameObject {
    public RecyclableBehavior behavior = null;
    public RecyclableActivator activator = null;

    public long ActivationTime = Long.MAX_VALUE;

    public AbstractBomb(RecyclableArray array) {
        super(array);
    }

    public void update(IPlayer player, int x, int y, AnimatedSprite animatedSprite) {
        super.update(player, x, y, 1, animatedSprite);
    }

    @Override
    public void applyDamage(IPlayer who, int dmg, long time) {
        detonate(time);
        life -= dmg;
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        return false;
    }

    @Override
    public void applyDig(IPlayer who, long time) {
        digdamage(time);
    }

    @Override
    public boolean calculate(long time) {
        //TODO algorithm for checking activation
        //if active then detonate
        if (ActivationTime > time) return false;
        return behavior.detonate(this, time);
    }

    public void detonate(long time) {
        ActivationTime = time;
    }

    public void digdamage(long time) {

    }

    @Override
    public void recycle() {
        behavior.recycle();
        activator.recycle();
        super.recycle();
    }

    @Override
    public String toString() {
        String t = this.getClass().getSimpleName();
        String a = String.valueOf(activator);
        String b = String.valueOf(behavior);
        return String.format("%s: [a: %s] [b: %s]", t, a, b);
    }

    public void setActivator(RandomTimeActivator update) {
        activator.recycle();
        activator = update;
    }
}
