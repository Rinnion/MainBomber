package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.controlers.ActionController;
import com.me.controlers.actions.PutBombAction;

import java.util.Calendar;


/**
 * Created by alekseev on 27.03.2014.
 */
public class RandomBomb extends AbstractBomb {

    private final int DEF_ActivationTimeDelay = 1000;
    private final int DEF_ActivationTime = 5000;


    public long ActivationTime;

    public boolean isActivated;

    private int px;
    private int py;


    private int jumps;

    public RandomBomb(IPlayer player, Vector2 pos) {
        this(player, pos, 15, 0);
    }

    public RandomBomb(IPlayer player, Vector2 pos, int jumps, int activationTime) {
        super(player, new CircleExplosion(100, 200, 14), new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("bomb"));

        if (activationTime == 0)
            ActivationTime = DEF_ActivationTime;
        else
            ActivationTime = activationTime;

        px = (int) pos.x;
        py = (int) pos.y;

        this.jumps = jumps;


        //       BombProperty(player,BombType.DYNAMITE,3000000,100,200,20,false,true,true)

        ActivationTime += Calendar.getInstance().getTimeInMillis();


    }


    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {


        if (time < ActivationTime) return false;


        if (jumps <= 0) {
            super.calculate(time);
            return true;

        }


        //int rX=MapManager.

        int dXInt = ((int) (Math.random() * 20)) - 10;
        int dYInt = ((int) (Math.random() * 20)) - 10;

        int newX = px + dXInt;
        int newY = py + dYInt;

        if (newX < 10)
            newX = 10;
        if (newY < 10)
            newY = 10;


        if (MapManager.mapInfo[(newY / MapManager.rowH) * MapManager.maxCel + (newX / MapManager.rowW)].mTile.group.id != 0) {
            ActivationTime = time + 100;
            return false;
        }
        jumps--;

        px = newX;
        py = newY;

        ActionController.Add(new PutBombAction(owner, time, new RandomBomb(owner, new Vector2(px, py), jumps, DEF_ActivationTimeDelay)));
        //GameObjectController.Add(new RandomBomb(owner,new Vector2(px,py),jumps,DEF_ActivationTimeDelay));


        //SetPosition(newX,newY);

        super.calculate(time);
        return true;

    }


    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }
}
