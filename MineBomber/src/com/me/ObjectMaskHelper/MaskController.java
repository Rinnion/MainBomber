package com.me.ObjectMaskHelper;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alekseev on 03.04.2014.
 */
public class MaskController {
    public static final HashMap<Float,IMask> maskList=new HashMap<Float, IMask>();

    public static Vector2[] GetMask(float radius)
    {
        if(maskList.containsKey(radius))
        {
           return maskList.get(radius).GetMask();
        }
        else
        {
            IMask mask=new CircleMask(radius);
            maskList.put(radius,mask);
            return mask.GetMask();
        }
    }
}
