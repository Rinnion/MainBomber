package com.me.controlers;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;
import com.me.Utility.MyArray;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alekseev on 27.03.2014.
 */
public class BombController {

    private static final int DEF_MAX_COUNT=2000;
    public static final ArrayList<AbstractBomb> mBombList=new ArrayList<AbstractBomb>(DEF_MAX_COUNT) ;
    private static final ArrayList<AbstractBomb> bombToRemove=new ArrayList<AbstractBomb>(DEF_MAX_COUNT);

    public static void Initialize()
    {

    }

    public static void DetonateBomb(IPlayer owner)
    {
        ArrayList<AbstractBomb> bombList=mBombList;

        for(int i=0;i<mBombList.size();i++)
        {
            AbstractBomb tmpBomb=bombList.get(i);

            if(tmpBomb.owner.equals(owner)) {
                tmpBomb.detonate(Calendar.getInstance().getTimeInMillis());
                return;
            }
        }
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

        //ArrayList<AbstractBomb> bombsToRemove = new ArrayList<AbstractBomb>(mBombList.size());


        for (AbstractBomb b: mBombList){
            if (b.ActivationTime>time) continue;
            bombToRemove.add(b);
            Vector2I position = b.position;
            MapManager.addDamageToField(b.ExplodeMask, position.x, position.y);
        }

        for(AbstractBomb btr: bombToRemove){
            ParticleManager.Fire(btr.position.x*MapManager.rowW, btr.position.y*MapManager.rowH,btr.Property.range*4);
            BombController.Remove(btr);
        }

        bombToRemove.clear();
    }

}
