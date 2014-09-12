package com.me.TilesManager;

import com.me.logger.Log;

import java.util.List;
import java.util.Random;




/**
 * Created by alekseev on 09.09.2014.
 */
public class TileGroup {
    public int id;
    public int next;

    public int life;
    public boolean canmove;
    public boolean candestroy;

    public Tile[] tileList;

    public  TileGroup(int id,int next,int life,boolean canmove,boolean candestroy)
    {
        this.id=id;
        this.next=next;
        this.life=life;
        this.canmove=canmove;
        this.candestroy=candestroy;
    //    this.tileList=tileList;

    }

    public void SetTiles(Tile[] tileList)
    {
        this.tileList=tileList;

    }

    public int GetRandomTileId()
    {
        int rndId=(int)((Math.random()*tileList.length));
        Log.d(rndId + "");

        return tileList[rndId].id ;
    }
   //public Tile GetRadomTile(){return tileList[GetRandomTileId()];}

}
