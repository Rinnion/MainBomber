package com.me.Map;


/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo
{
    public int mX;
    public int mY;
    public int life;
    private int mId;
    private boolean mFree;
    final public int index;
    private int mPixmapIndex;

    public boolean redraw;


    public int GetX()
    {
        return mX;
    }

    public int GetY()
    {
        return mY;
    }

    public int GetLife()
    {
        return life;
    }

    public int GetId()
    {
        return  mId;
    }

    public int GetPixmapIndex()
    {
        return  mPixmapIndex;
    }

    public boolean isFree()
    {
        return mFree;
    }

    public void SetInfo(int id)
    {
        SetInfo(id, life,mFree);
    }

    public void SetInfo(int id,int life)
    {
        SetInfo(id,life,mFree);
    }

    public void SetInfo(int id, int life, boolean free) {
        mId = id;
        if (life < 0) this.life = 0;
        else
            this.life = life;
        mFree = free;
    }



    public MapInfo(int index,int id,int x,int y,int pixmapIndex,int life,boolean free)
    {
        mX=x;
        mY=y;
        mId=id;
        this.life = life;
        mFree=free;
        mPixmapIndex=pixmapIndex;
        this.index=index;
    }

    public MapInfo(MapInfo mapInfo)
    {

       mX=mapInfo.mX;
       mY=mapInfo.mY;
       mId=mapInfo.mId;
       life =mapInfo.life;
       mFree=mapInfo.mFree;
        mPixmapIndex=mapInfo.mPixmapIndex;
        index=mapInfo.index;
    }
}
