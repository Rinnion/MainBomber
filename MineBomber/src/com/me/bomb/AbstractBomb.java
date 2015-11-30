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

    public boolean activated = false;

    public AbstractBomb(RecyclableArray array) {
        super(array);
    }

    public void update(IPlayer player, int x, int y, AnimatedSprite animatedSprite) {
        super.update(player, x, y, 1, animatedSprite);
        activated = false;
    }

    @Override
    public void damage(IPlayer who, int dmg, long time) {
//        behavior.damage(this, who, dmg, time);
//        ActivationTime = time;
        activated = true;
        life -= dmg;
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        return false;
    }

    @Override
    public void dig(IPlayer who, long time) {
    }

    @Override
    public boolean logic(long time) {
        activated = activated | activator.logic(time);
        if (!activated) return false;
        return behavior.detonate(this, time);
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
