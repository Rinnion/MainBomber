package com.me.ObjectMaskHelper;

import com.me.Map.MapManager;

import java.util.ArrayList;

/**
 * Created by alekseev on 03.04.2014.
 */
public class CircleMask implements IMask {

    private float mMaskRadius;

    private Vector2I[] mMask;

    public CircleMask(float radius)
    {
        mMaskRadius=radius;
        createMask(0,0,mMaskRadius);
    }



    public void createMask(int startX, int startY, float circleRadius)
    {
        int sx=startX / MapManager.rowW;
        int sy=startY / MapManager.rowH;

        int left = (int)Math.floor(sx - circleRadius);
        int top = (int)Math.floor(sy - circleRadius);
        int right = (int)Math.ceil(sx + circleRadius);
        int bottom = (int)Math.ceil(sy + circleRadius);

        float radDig= circleRadius*circleRadius*MapManager.rowW*MapManager.rowH;

        ArrayList<Vector2I> vectors=new ArrayList<Vector2I>();

        for(int x=left; x<right ;x++)
            for(int y=top; y<bottom;y++){
                int fx = (x*MapManager.rowW + MapManager.rowW/2);
                int fy = (y*MapManager.rowH + MapManager.rowH/2);

                int sum=(fx*fx)+(fy*fy);
                if (sum <radDig ){
                    vectors.add(new Vector2I(x,y));
                }
            }
            mMask = vectors.toArray(new Vector2I[vectors.size()]);

    }


    @Override
    public Vector2I[] GetMask() {
        return mMask;
    }

    @Override
    public float GetRadius() {
        return mMaskRadius;
    }
}
