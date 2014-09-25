package com.me.Players;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alekseev on 24.09.2014.
 */
public class Arsenal {
    HashMap<Integer,Integer> mArsenal=null;

    public Arsenal()
    {
        mArsenal=new HashMap<Integer, Integer>();

        mArsenal.put(0,20);
        mArsenal.put(2,20);
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
                bomb=new Dynamite(player,pos);
                break;
            case BombType.DESTANATION:
                bomb=new DestBomb(player,pos);
                break;
            case BombType.RANDOM:
                bomb=new RandomBomb(player,pos);
                break;
            case BombType.PunchTera :
                bomb=new PunchTeraStone(player,pos);
                break;
            case BombType.FasFilled:
                bomb=new FastFilledBomb(player,pos);
                break;
            case BombType.Filled:
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
