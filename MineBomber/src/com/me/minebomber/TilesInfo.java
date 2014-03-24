package com.me.minebomber;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by alekseev on 21.03.2014.
 */
public class TilesInfo {
    int mLife;
    int mId;
    int mNextid;
    TextureRegion mTexRegion;


     public TilesInfo(int life,int id,int nextid,TextureRegion textureRegion)
     {
         mLife=life;
         mId=id;
         mNextid=nextid;
         mTexRegion=new TextureRegion(textureRegion);

     }

}
