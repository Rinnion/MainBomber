package com.me.Bombs;

import com.me.Bombs.Activator.IActivator;
import com.me.Bombs.Behavior.IBehavior;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.minebomber.AbstractGameObject;

/**
 * Created by tretyakov on 09.04.2014.
 */
public abstract class AbstractBomb extends AbstractGameObject {
    public IBehavior behavior = null;
    public IActivator activator = null;

    public long ActivationTime = Long.MAX_VALUE;

    public void update(IPlayer player, Vector2I pos, AnimatedSprite animatedSprite) {
        super.update(player, pos, 1, animatedSprite);
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

    }

    @Override
    public boolean calculate(long time) {
        behavior.detonate(this, time);
        return true;
    }

    public boolean activate(long time) {
        return false;
    }

    public abstract void detonate(long time);

    public abstract void digdamage(long time);

    @Override
    public String toString() {
        String t = this.getClass().getSimpleName();
        String a = String.valueOf(activator);
        String b = String.valueOf(behavior);
        return String.format("%s: [a: %s] [b: %s]", t, a, b);
    }
}
