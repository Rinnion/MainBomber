package com.me.Map;


import com.me.TilesManager.Tile;

/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo
{
    final public int mX;
    final public int mY;
    public int life;
    public Tile mTile;

    final public int index;
    public int mPixmapIndex;
    public  boolean canmove;

    public boolean view;


    public void SetInfo(Tile tile, int life,boolean canmove) {
        mTile = tile;
        if (life < 0) this.life = 0;
        else
            this.life = life;
        this.canmove=canmove;
    }



    public MapInfo(int index,Tile tile,int x,int y,int pixmapIndex,int life,boolean canmove)
    {
        mX=x;
        mY=y;
        mTile=tile;
        this.life = life;
       // mFree=free;
        mPixmapIndex=pixmapIndex;
        this.index=index;
        this.canmove=canmove;
        view=false;
    }

}
