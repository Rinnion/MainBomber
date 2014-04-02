package com.me.Map;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by alekseev on 21.03.2014.
 */
public class TilesInfo {
    public float mLife;
    public int mId;
    public int mNextid;
    public TextureRegion mTexRegion;
    public int mType;

    public final static int TYPE_GROUND=0;

     public TilesInfo(int id,int nextid,int type,float life,TextureRegion textureRegion)
     {
         mLife=life;
         mId=id;
         mNextid=nextid;
         mTexRegion=new TextureRegion(textureRegion);
         mType=type;
     }

}
