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
    public boolean destroed=false;

    public DestBomb(IPlayer player, Vector2 pos)
    {
       super(player,new CircleExplosion(100,200,20), new Vector2I((int)pos.x/MapManager.rowW, (int)pos.y/MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dst_bomb"));
        //       BombProperty(player,BombType.DYNAMITE,3000000,100,200,20,false,true,true)

        ActivationTime += Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void activate(long time) {
        ActivationTime=time;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public void calculate(long time) {
        super.calculate(time);
        if(destroed)
        canremove=true;
    }

    @Override
    public void detonate(long time) {
        if(!destroed) {
            super.detonatenow=true;
            super.detonate(time);

            destroed = true;
        }
    }
}
