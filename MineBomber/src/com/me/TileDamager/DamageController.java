package com.me.TileDamager;

import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.Map.TilesInfo;
import com.me.minebomber.DrawManager;

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

    static private boolean goDestroy(int index,int damage)
    {
        MapInfo info = MapManager.mapInfo[index];
        TilesInfo tile;
        int live;
        int dmg=damage;
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

    static public void damageOnTiles(Integer []indexes,int dmg)
    {
        //synchronized (syncObject)

          for(int i=0;i<indexes.length;i++)
          {
                if(goDestroy(indexes[i],dmg))
                    DrawManager.Append(indexes[i]);

                //MapManager.Redraw(indexes[i]);
          }

    }

    static public void damageOnTile(int index,int dmg)
    {
        //synchronized (syncObject)

            if(goDestroy(index,dmg))
                DrawManager.Append(index);

               //MapManager.Redraw(index);

    }


}
