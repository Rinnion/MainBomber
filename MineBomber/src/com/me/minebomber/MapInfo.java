package com.me.minebomber;

/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo
{
    int mX;
    int mY;

    int mLife;

    int mId;

    public MapInfo(int id,int x,int y,int life)
    {
        mX=x;
        mY=y;
        mId=id;
        mLife=life;

    }

    public MapInfo(MapInfo mapInfo)
    {
       mX=mapInfo.mX;
       mY=mapInfo.mY;
       mId=mapInfo.mId;
       mLife=mapInfo.mLife;
    }


}
