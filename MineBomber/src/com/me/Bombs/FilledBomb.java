package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Bombs.Behavior.FastTeramorf;
import com.me.Bombs.Behavior.Teramorf;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;

import java.util.Calendar;


/**
 * Created by alekseev on 27.03.2014.
 */
public class FilledBomb extends AbstractBomb {

    private int delay=2000;

    public FilledBomb(IPlayer player, Vector2 pos) {
        super(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dyn"));
        behavior =new Teramorf((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH, 81);
        activator = new TimeActivator(this, 3000);
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {

        if(time<ActivationTime)return false;
        super.calculate(time);
        ActivationTime =time+delay;
        //iteration--;
        return false;
    }




    @Override
    public void detonate(long time) {
        ActivationTime=time;
    }
}
