package com.me.ObjectMaskHelper;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.BombPlaser;
import com.me.Bombs.IBomb;
import com.me.Map.MapManager;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.TileDamager.DamageController;

import java.util.ArrayList;

/**
 * Created by alekseev on 03.04.2014.
 */
public class CircleMask implements IMask {

    private float mMaskRadius;

    private MaskVector[] mMask;

    public CircleMask(float radius)
    {
        mMaskRadius=radius;
        createMask(0,0,mMaskRadius);
    }



    public void createMask(int startX, int startY,float circleRadius)
    {
        int sx=startX/ MapManager.rowW;
        int sy=startY/MapManager.rowH;

        int radius = (int)Math.ceil(circleRadius);

        int left = sx-radius-1;
        int top = sy-radius-1;
        int right = sx+radius+1;
        int bottom = sy+radius+1;


        float radDig= radius*radius*MapManager.rowW*MapManager.rowH;


        ArrayList<MaskVector> vectors=new ArrayList<MaskVector>();
         //Vector2 retVectors=


        for(int x=left;x<right;x++)
            for(int y=top; y<bottom;y++){
                int fx = (x*MapManager.rowW+(MapManager.rowW/2))-startX;
                int fy = (y*MapManager.rowH+(MapManager.rowH/2))-startY;

                int sum=(fx*fx)+(fy*fy);
                //int index = (y*MapManager.maxCel)+x;
                if (sum <radDig ){

                    vectors.add(new MaskVector(x,y));
                }

            }

          mMask=(MaskVector[])vectors.toArray(new MaskVector[vectors.size()]);

    }


    @Override
    public MaskVector[] GetMask() {
        return mMask;
    }

    @Override
    public float GetRadius() {
        return mMaskRadius;
    }
}
