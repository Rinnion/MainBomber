package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.Players.IPlayer;

import java.util.ArrayList;

/**
 * Created by alekseev on 27.03.2014.
 */
public class BombPlaser {

    private static ArrayList<IBomb> mBombList=null;


    public static void Initialize()
    {
        mBombList=new ArrayList<IBomb>();

    }


    public static void DetonateBomb(IPlayer owner)
    {
        for(int i=0;i<mBombList.size();i++)
        {
            if(mBombList.get(i).GetOwner().equals(owner)) {
                mBombList.get(i).ImmediatelyDetonate();
                return;
            }
        }
    }

    private static void canBeRemove(IBomb bomb)
    {
         mBombList.remove(bomb);
         //bomb.GetOwner().GetLifeBar().DoItVisible();
    }

    public static void DealDamage(float x, float y,IBomb bomb)
    {

            //player.DealDamage(dmg);
            // if(player.GetLifeBar()!=null)
            // player.GetLifeBar().DoItVisible();
            for(IBomb tmpBomb : mBombList)
            {
                if(tmpBomb.equals(bomb))
                    continue;

                if((x>tmpBomb.getX () && x<tmpBomb.getX()+tmpBomb.getW()) && (y>tmpBomb.getY() && y<tmpBomb.getY()+tmpBomb.getH()))
                {
                    //player.DealDamage(bomb);
                    //playerBuffer.add(player);
                    tmpBomb.ImmediatelyDetonate();
                }
            }



    }

    public static void Place(BombProperty bombProperty,Vector2 position)
    {
        IBomb bomb=null;

        switch (bombProperty.type)
        {
            case BombType.DYNAMITE:

                bomb=new Dynamite(bombProperty,position,new IBombCallback() {


                    @Override
                    public void CanBeRemove(IBomb bomb) {
                        //mBombList.remove(bomb);
                        //bomb.GetOwner().GetLifeBar().DoItVisible();
                        //DealDamage(bomb);
                        canBeRemove(bomb);
                    }


                });
                break;
            case BombType.DSTBOMB:
                bomb=new DestBomb (bombProperty,position,new IBombCallback() {


                    @Override
                    public void CanBeRemove(IBomb bomb) {
                        //DealDamage(bomb);
                        canBeRemove(bomb);
                    }


                });
                break;
        }
        mBombList.add(bomb);

    }

    public static void Draw(Batch bt)
    {
        for (int i=0;i<mBombList.size();i++)
        {
            mBombList.get(i).Render(bt);
        }
    }



}
