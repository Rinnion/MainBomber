package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
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
    public IBehavior behavior;
    //public final BombProperty Property;

     public boolean detonatenow=false;



    public AbstractBomb(IPlayer player,IBehavior behavior, Vector2I pos, AnimatedSprite animatedSprite) {
        //FIXME life from bomb
        super(player, pos, 1, animatedSprite);

        this.behavior = behavior;

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

    public abstract void activate(long time);
    public  void detonate(long time)
    {
        if(detonatenow) {
            behavior.detonate(this, time);
            detonatenow=false;
        }

    }



    @Override
    public void calculate(long time) {



    }




    public abstract void digdamage(long time);

}
