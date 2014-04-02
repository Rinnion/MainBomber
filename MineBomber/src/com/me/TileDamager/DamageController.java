package com.me.TileDamager;

import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.Map.TilesInfo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by alekseev on 27.03.2014.
 */
public class DamageController {
    static int []redrawIndexes;
    static int redrawCount;
    static Object syncObject=new Object();
    //static ReadWriteLock lock = new ReentrantReadWriteLock();

    /*public void damageOnTile(MapInfo info,TilesInfo tile)
    {

    }
    */

    static private boolean goDestroy(int index,float damage)
    {
        MapInfo info = MapManager.mapInfo[index];
        TilesInfo tile;
        float live;
        float dmg=damage;
        boolean destroy=false;
        while (true)
        {
            live=info.GetLife();
            if(live-dmg>0)
            {
               info.SetInfo(info.GetId(),live-dmg);
               return destroy;
            }
            else
            {
                tile=MapManager.mapTiles.get(info.GetId());
                dmg-=live;




                if(tile.mId==tile.mNextid) {
                    info.SetInfo(tile.mNextid,0,tile.mType==TilesInfo.TYPE_GROUND);
                    return destroy;
                }
                else
                {
                    destroy=true;
                    info.SetInfo(tile.mNextid,tile.mLife,tile.mType==TilesInfo.TYPE_GROUND);
                }

            }


        }

    }

    static public void damageOnTiles(Integer []indexes,float dmg)
    {
        //synchronized (syncObject)

          for(int i=0;i<indexes.length;i++)
          {
                if(goDestroy(indexes[i],dmg))
                MapManager.Redraw(indexes[i]);
          }

    }

    static public void damageOnTile(int index,float dmg)
    {
        //synchronized (syncObject)

            if(goDestroy(index,dmg))
               MapManager.Redraw(index);

    }


}
