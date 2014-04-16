package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Map.AbstractGameObject;
import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.Map.TilesInfo;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.logger.Log;
import com.me.minebomber.DrawManager;
import com.me.minebomber.MineBomber;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by alekseev on 27.03.2014.
 */
public class BombPlaser {

    private static ArrayList<AbstractBomb> mBombList=null;
    static boolean canDetonate=true;
    final static Object syncDetonator=new Object();
    static int curBombsDetonations=0;

    public static void Initialize()
    {
        mBombList=new ArrayList<AbstractBomb>();
    }

    public static void Reset()
    {
        synchronized (syncDetonator) {
            curBombsDetonations = 0;
            canDetonate = true;
        }
    }


    public static boolean CanDetonate(IBomb bomb)
    {

        //return true;

        boolean canDetonate=(bomb.GetActivationTime()< MineBomber.BeginDrawTime);
    /*    if(canDetonate)
            curBombsDetonations++;

        if(curBombsDetonations>5)
            canDetonate=false;
       */
        return canDetonate;


        /*synchronized (syncDetonator) {
          curBombsDetonations++;
          if (curBombsDetonations > 10)
              canDetonate = false;

          return canDetonate;
      }*/

    }


    public static void DetonateBomb(IPlayer owner)
    {
        for(int i=0;i<mBombList.size();i++)
        {
            if(mBombList.get(i).GetOwner().equals(owner)) {
                mBombList.get(i).ImmediatelyDetonate(Calendar.getInstance().getTimeInMillis());
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

    }

    public static void Place(BombProperty bombProperty, Vector2 position)
    {
        AbstractBomb bomb=null;
        int x = (int) position.x;
        int y = (int) position.y;
        int index = y*MapManager.maxCel + x;

        if (MapManager.fieldObjects[index].size() == MapManager.FIELD_CAPACITY){
            return;
        }

        switch (bombProperty.type)
        {
            case BombType.DSTBOMB:
                bomb = new DestBomb(bombProperty, position);
                break;
        }

        MapManager.fieldObjects[index].add(bomb);
        mBombList.add(bomb);
    }

    public static void Draw(Batch bt, long time)
    {
        AbstractBomb bomb;

        long lStart = Calendar.getInstance().getTimeInMillis();
        calculateDamage(mBombList, time);

        long calculateDamage = Calendar.getInstance().getTimeInMillis();
        applyDamage(time);

        long applyDamage = Calendar.getInstance().getTimeInMillis();

        for (int i=0;i<mBombList.size();i++)
        {
            bomb=mBombList.get(i);
            bomb.Render(bt);
        }
        long lDraw = Calendar.getInstance().getTimeInMillis();

        Log.d("BombPlace.Draw " + (calculateDamage - lStart) + "; " + (applyDamage - calculateDamage) + "; " + (lDraw - applyDamage));

    }

    private static void applyDamage(long time) {
        int[] fieldDamage = MapManager.fieldDamage;
        ArrayList<AbstractGameObject>[] fieldObjects = MapManager.fieldObjects;
        ArrayList<IPlayer> players = PlayerController.players;
        MapInfo[] mapInfos = MapManager.mapInfo;

        for (int i = 0; i < fieldDamage.length; i++) {
            if (fieldDamage[i] == 0) continue;
            MapInfo mapInfo = mapInfos[i];
            int life = mapInfo.life -= fieldDamage[i];
            int mNextId = 0;

            while (life < 0) {
                TilesInfo tilesInfo = MapManager.mapTiles.get(mapInfo.GetId());
                mNextId = tilesInfo.mNextid;
                if (mNextId == 0) break;
                life += MapManager.mapTiles.get(mNextId).mLife;
            }
            mapInfo.SetInfo(mNextId, life);

            for (AbstractGameObject ago: fieldObjects[i])  {
                ago.receiveDamage(fieldDamage[i], time);
            }

            for (IPlayer bm : players) {
                if (Math.abs(bm.getX() - mapInfo.mX) < 2f && Math.abs(bm.getY() - mapInfo.mY) < 2f)
                    bm.DealDamage(fieldDamage[i]);
            }

            fieldDamage[i] = 0;
            DrawManager.Append(i);
        }
    }

    private static void calculateDamage(ArrayList<AbstractBomb> mBombList, long time) {

        //Arrays.fill(MapManager.fieldDamage, 0);
        int[] fieldDamage = MapManager.fieldDamage;
        int maxX = MapManager.maxCel-1;
        int maxY = MapManager.maxRow-1;
        int cols = MapManager.maxCel;
        ArrayList<AbstractBomb> bombsToRemove = new ArrayList<AbstractBomb>(mBombList.size());
        for (AbstractBomb b: mBombList){
            if (b.ActivationTime>time) continue;
            bombsToRemove.add(b);
            Vector2 position = b.Position;
            BombProperty property = b.GetProperty();
            for (Vector2I vm: b.ExplodeMask) {
                int x = vm.x + (int) position.x;
                int y = vm.y + (int) position.y;
                //correct bounds
                if (x < 1) {
                    x = 1;
                } else if (x > maxX) {
                    x = maxX;
                }
                if (y < 1) {
                    y = 1;
                } else if (y > maxY) {
                    y = maxY;
                }
                //add damage
                fieldDamage[y*cols + x] += property.dmgMin + (int)Math.random()*(property.dmgMax - property.dmgMin);
            }
        }

        for(AbstractBomb btr: bombsToRemove){
            ParticleManager.Fire(btr.Position.x*MapManager.rowW, btr.Position.y*MapManager.rowH);
            mBombList.remove(btr);
        }

    }
}
