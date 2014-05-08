package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.Map.AbstractGameObject;
import com.me.Map.MapManager;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;
import com.me.logger.Log;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by alekseev on 27.03.2014.
 */
public class BombController {

    private static ArrayList<AbstractBomb> mBombList=null;
    static boolean canDetonate=true;
    final static Object syncDetonator=new Object();
    static int curBombsDetonations=0;

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

    public static void Place(IPlayer player, BombProperty bombProperty, Vector2 position)
    {
        AbstractBomb bomb=null;
        int x = (int) position.x/MapManager.rowW;
        int y = (int) position.y/MapManager.rowH;
        int index = y*MapManager.maxCel + x;

        if (MapManager.fieldObjects[index].size() == MapManager.FIELD_CAPACITY){
            return;
        }

        switch (bombProperty.type)
        {
            case BombType.DSTBOMB:
                bomb = new DestBomb(player, bombProperty, position);
                break;
        }

        MapManager.fieldObjects[index].add(bomb);
        mBombList.add(bomb);
    }

    public static void Calculate(long time){
        calculateDamage(mBombList, time);
    }

    public static void Render(Batch bt)
    {
        AbstractBomb bomb;

        for (int i=0; i<mBombList.size(); i++)
        {
            bomb=mBombList.get(i);
            bomb.Render(bt);
        }
    }

    private static void calculateDamage(ArrayList<AbstractBomb> mBombList, long time) {

        ArrayList<AbstractBomb> bombsToRemove = new ArrayList<AbstractBomb>(mBombList.size());
        for (AbstractBomb b: mBombList){
            if (b.ActivationTime>time) continue;
            bombsToRemove.add(b);
            Vector2 position = b.position;
            MapManager.addDamageToField(b.ExplodeMask, position.x, position.y);
        }

        for(AbstractBomb btr: bombsToRemove){
            ParticleManager.Fire(btr.position.x*MapManager.rowW, btr.position.y*MapManager.rowH);
            mBombList.remove(btr);
        }
    }

}
