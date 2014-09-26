package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.DestinationActivator;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Bombs.Behavior.IBehavior;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.MaskController;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;

import java.util.Calendar;


/**
 * Created by alekseev on 27.03.2014.
 */
public class DestBomb extends AbstractBomb {

    public DestBomb(IPlayer player, Vector2 pos) {
        super(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dst_bomb"));

        behavior = new CircleExplosion(100, 200, 20);
        activator = new DestinationActivator(this);
    }

    @Override
    public boolean activate(long time) {
        ActivationTime=time;
        return true;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {
        if(!(ActivationTime < time)) return false;
        super.calculate(time);
        return true;
    }




    @Override
    public void detonate(long time) {
        activate(time);
    }
}
