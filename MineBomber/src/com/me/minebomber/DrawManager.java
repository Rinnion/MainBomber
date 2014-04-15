package com.me.minebomber;

import com.badlogic.gdx.graphics.Pixmap;
import com.me.Bombs.BombPlaser;
import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.Map.PixmapHelper;
import com.me.logger.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by alekseev on 03.04.2014.
 */
public class DrawManager {

    //public static ArrayList<Integer> refreshFields=new ArrayList<Integer>(200);

    private final static int FIELDS_COUNT=2000;

    private static int[] refreshFields=new int[FIELDS_COUNT];

    private static Object syncObject=new Object();
    private static int objCount=0;
    public static void Append(int index)
    {
        synchronized (syncObject) {
            refreshFields[objCount]=index;
            objCount++;
        }
    }

    public static void RedrawAll()
    {
        long lstart=Calendar.getInstance().getTimeInMillis();
        synchronized (syncObject) {
            //PixmapHelper.Bind(MapManager.);
            MapManager.BindForeground();
            for(int i=0;i<objCount;i++)
            {

                MapManager.RedrawPixmap(refreshFields[i]);
            }
            objCount=0;

        }
        long lend=Calendar.getInstance().getTimeInMillis();
        Log.d((lend-lstart)+"");

    }



}
