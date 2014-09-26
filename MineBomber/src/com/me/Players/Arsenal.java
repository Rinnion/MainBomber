package com.me.Players;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;

import java.util.HashMap;

/**
 * Created by alekseev on 24.09.2014.
 */
public class Arsenal {



    public int sindex;
    HashMap<Integer, Integer> mArsenal = null;
    public Arsenal()
    {
        mArsenal=new HashMap<Integer, Integer>();
        sindex=0;

        mArsenal.put(BombType.DYNAMITE, 50);
        mArsenal.put(BombType.RANDOM, 50);
        //mArsenal.put(2,5);
        //mArsenal.put(1,5);
    }

    public  void addIndex()
    {
        if(mArsenal.size()==0)
            return;
        sindex=(sindex+1)%mArsenal.size();
    }



    public int getFirstWeapon(int sindex)
    {
        if(mArsenal.size()==0)
            return -1;

        if(sindex<1)
            sindex=mArsenal.size();

        //if(sindex>mArsenal.size())
          //  return -1;

        Integer[] v=mArsenal.keySet().toArray(new Integer[mArsenal.size()]);



        sindex=sindex%mArsenal.size();

        return v[sindex];

    }

    public void Add(int type,int count)
    {
        if(!mArsenal.containsKey(type)) {
            mArsenal.put(type, count);
            return;
        }

        Integer tmpCount=mArsenal.get(type);

        tmpCount+=count;

    }
    public AbstractBomb PutBomb(IPlayer player,Vector2 pos)
    {
       int index= getFirstWeapon(sindex);

        if(index==-1)
            return null;

        return PutBomb(player,pos,index);


    }

    public int AddRandom()
    {
        int rCount=(int)(Math.random()*5)+1;

        AddRandom(rCount);
        return rCount;
    }
    public void AddRandom(int count)
    {
        int type=(int)(Math.random()*BombType.TYPE_COUNT);

        mArsenal.put(type,count);
    }


    public AbstractBomb PutBomb(IPlayer player,Vector2 pos,int type)
    {
        AbstractBomb bomb=null;

        if(!mArsenal.containsKey(type))
            return bomb;

        Integer count=mArsenal.get(type);



        if(count<1)
            return bomb;

        count--;
        switch (type)
        {
            case BombType.DYNAMITE:
                bomb = new Dynamite(player, pos, TimeActivator.DEFAULT_TIME);
                break;
            case BombType.DESTANATION:
                bomb=new DestBomb(player,pos);
                break;
            case BombType.RANDOM:
                bomb = new RandomBomb(player, pos, JumpBehavior.DEFAULT_JUMPS, JumpBehavior.DEFAULT_RADIUS, TimeActivator.DEFAULT_TIME);
                break;
            case BombType.PUNCH_TERA :
                bomb=new PunchTeraStone(player,pos);
                break;
            case BombType.FAST_FILLED:
                bomb=new FastFilledBomb(player,pos);
                break;
            case BombType.FILLED:
                bomb=new FilledBomb(player,pos);
                break;

        }

        if(count<1)
            mArsenal.remove(type);
        else
            mArsenal.put(type,count);

       return bomb;

    }



}
