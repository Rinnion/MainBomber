package com.me.minebomber;


import com.badlogic.gdx.math.Rectangle;

/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo
{
    int mX;
    int mY;

    int mLife;

    int mId;
    boolean mFree;
    Rectangle mTextureSteps;
    public MapInfo(int id,int x,int y,Rectangle texSteps,int life,boolean free)
    {
        mX=x;
        mY=y;
        mId=id;
        mLife=life;
        mFree=free;
        mTextureSteps=texSteps;

    }

    public MapInfo(MapInfo mapInfo)
    {
       mX=mapInfo.mX;
       mY=mapInfo.mY;
       mId=mapInfo.mId;
       mLife=mapInfo.mLife;
       mFree=mapInfo.mFree;
        mTextureSteps=mapInfo.mTextureSteps;
    }


}
