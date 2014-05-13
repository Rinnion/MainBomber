package com.me.minebomber;

import com.me.Map.MapManager;

/**
 * Created by alekseev on 03.04.2014.
 */
public class DrawManager {

    //public static ArrayList<Integer> refreshFields=new ArrayList<Integer>(200);

    private final static int FIELDS_COUNT = 20000;

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
        synchronized (syncObject) {
            MapManager.BindForeground();
            for(int i=0;i<objCount;i++)
            {
                MapManager.RedrawPixmap(refreshFields[i]);
            }
            objCount=0;
        }
    }



}
