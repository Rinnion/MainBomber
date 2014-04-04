package com.me.Map;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo
{
    private int mX;
    private int mY;
    private float mLife;
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

    public float GetLife()
    {
        return  mLife;
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
        SetInfo(id,mLife,mFree);
    }

    public void SetInfo(int id,float life)
    {
        SetInfo(id,life,mFree);
    }

    public void SetInfo(int id,float life,boolean free)
    {
        mId=id;
        mLife=life;
        mFree=free;
    }



    public MapInfo(int index,int id,int x,int y,int pixmapIndex,float life,boolean free)
    {
        mX=x;
        mY=y;
        mId=id;
        mLife=life;
        mFree=free;
        mPixmapIndex=pixmapIndex;
        this.index=index;
    }

    public MapInfo(MapInfo mapInfo)
    {

       mX=mapInfo.mX;
       mY=mapInfo.mY;
       mId=mapInfo.mId;
       mLife=mapInfo.mLife;
       mFree=mapInfo.mFree;
        mPixmapIndex=mapInfo.mPixmapIndex;
        index=mapInfo.index;
    }
}
