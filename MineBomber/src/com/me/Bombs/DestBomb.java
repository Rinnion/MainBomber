package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Behavior.IBehavior;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;


/**
 * Created by alekseev on 27.03.2014.
 */
public class DestBomb extends AbstractBomb {



    public DestBomb(IPlayer player, Vector2 pos)
    {
       super(player,new BombProperty(player,BombType.DYNAMITE,3000000,100,200,20,false,true,true) , new Vector2I((int)pos.x/MapManager.rowW, (int)pos.y/MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dst_bomb"));


    }

    @Override
    public void detonate(long time) {
        super.detonate(time);
    }
}
