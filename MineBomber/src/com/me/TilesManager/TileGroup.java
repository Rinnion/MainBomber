package com.me.TilesManager;

/**
 * Created by alekseev on 09.09.2014.
 */
public class TileGroup {
    public int id;
    public int nextGroupId;
    public TileGroup next;

    public int life;
    public boolean canmove;
    public boolean candestroy;

    public Tile[] tileList;

    public  TileGroup(int id,int nextid,int life,boolean canmove,boolean candestroy)
    {
        this.id=id;
        this.nextGroupId=nextid;
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
        //Log.d(rndId + "");

        return tileList[rndId].id ;
    }

    public Tile GetRandomTile()
    {
        int rndId=(int)((Math.random()*tileList.length));
        //Log.d(rndId + "");

        return tileList[rndId];
    }
    //public tile GetRadomTile(){return tileList[GetRandomTileId()];}

}
