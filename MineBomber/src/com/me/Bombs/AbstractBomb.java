package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.me.Bombs.Activator.IActivator;
import com.me.Bombs.Behavior.IBehavior;
import com.me.minebomber.AbstractGameObject;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.ObjectMaskHelper.MaskController;
import com.me.Players.IPlayer;

import java.util.Calendar;

/**
 * Created by tretyakov on 09.04.2014.
 */
public abstract class AbstractBomb extends AbstractGameObject {
    public IBehavior behavior = null;
    public IActivator activator = null;

    public long ActivationTime = Long.MAX_VALUE;

    public AbstractBomb(IPlayer player, Vector2I pos, AnimatedSprite animatedSprite) {
        //FIXME life from bomb
        super(player, pos, 1, animatedSprite);
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
        behavior.detonate(this,time);
        return true;
    }

    public  boolean activate(long time)
    {
        return false;
    }

    public  abstract void detonate(long time);

    public abstract void digdamage(long time);

}
