package com.me.controlers;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alekseev on 27.03.2014.
 */
public class BombController {

    private static ArrayList<AbstractBomb> mBombList=null;

    public static void Initialize()
    {
        mBombList=new ArrayList<AbstractBomb>();
    }

    public static void DetonateBomb(IPlayer owner)
    {
        for(int i=0;i<mBombList.size();i++)
        {
            if(mBombList.get(i).owner.equals(owner)) {
                mBombList.get(i).detonate(Calendar.getInstance().getTimeInMillis());
                return;
            }
        }
    }

    public static AbstractBomb[]  GetBombs()
    {
        return mBombList.toArray(new AbstractBomb[mBombList.size()]);
    }

    public static void Add(IPlayer player, BombProperty bombProperty, Vector2 position)
    {
        if (!GameObjectController.isRoom(position)) return;

        AbstractBomb bomb=null;
        switch (bombProperty.type)
        {
            case BombType.DSTBOMB:
                bomb = new DestBomb(player, bombProperty, position);
                break;
        }

        GameObjectController.Add(bomb);
        mBombList.add(bomb);
    }

    public static void Remove(AbstractBomb abstractBomb){
        GameObjectController.Remove(abstractBomb);
        mBombList.remove(abstractBomb);
    }

    public static void Calculate(long time){
        calculateDamage(mBombList, time);
    }

    private static void calculateDamage(ArrayList<AbstractBomb> mBombList, long time) {

        ArrayList<AbstractBomb> bombsToRemove = new ArrayList<AbstractBomb>(mBombList.size());
        for (AbstractBomb b: mBombList){
            if (b.ActivationTime>time) continue;
            bombsToRemove.add(b);
            Vector2I position = b.position;
            MapManager.addDamageToField(b.ExplodeMask, position.x, position.y);
        }

        for(AbstractBomb btr: bombsToRemove){
            ParticleManager.Fire(btr.position.x*MapManager.rowW, btr.position.y*MapManager.rowH,btr.Property.range*4);
            BombController.Remove(btr);
        }
    }

}
