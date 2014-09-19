package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
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

    public long   ActivationTime=300000;

    public boolean isActivated;

    public DestBomb(IPlayer player, Vector2 pos)
    {
       super(player,new CircleExplosion(100,200,20), new Vector2I((int)pos.x/MapManager.rowW, (int)pos.y/MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dst_bomb"));
        //       BombProperty(player,BombType.DYNAMITE,3000000,100,200,20,false,true,true)

        ActivationTime += Calendar.getInstance().getTimeInMillis();
        isActivated=false;

    }

    @Override
    public boolean activate(long time) {
        boolean retActivation=!isActivated;
        ActivationTime=time;
        isActivated=true;
        return retActivation;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {

        if(!isActivated)return false;
        super.calculate(time);
        return true;
    }




    @Override
    public void detonate(long time) {
        activate(time);
    }
}
