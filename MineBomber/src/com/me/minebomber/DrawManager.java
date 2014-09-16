package com.me.minebomber;


import com.me.Map.MapManager;
import com.me.Utility.IntArray;


/**
 * Created by alekseev on 03.04.2014.
 */
public class DrawManager {

    //public static ArrayList<Integer> refreshFields=new ArrayList<Integer>(200);

    public final static int FIELDS_COUNT = 20000;

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

    public static void AddArray(IntArray items)
    {

        synchronized (syncObject) {
            //refreshFields[objCount]=index;
            final int []arrayI=items.getFullArray();
            final int count=items.size();

              System.arraycopy(arrayI,0, refreshFields,objCount,count);

            objCount+=count ;
        }
    }


    public static void Redraw(int index)
    {
        MapManager.RedrawPixmap(index);
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
