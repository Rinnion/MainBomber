package com.me.minebomber;


import com.me.Map.MapManager;
import com.me.Utility.IntArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by alekseev on 03.04.2014.
 */
public class DrawManager {

    public final static int FIELDS_COUNT = 20000;
    static Logger logger = LoggerFactory.getLogger(DrawManager.class);
    private static int[] refreshFields=new int[FIELDS_COUNT];

    private static Object syncObject=new Object();
    private static int objCount=0;
    public static void Append(int index)
    {
        synchronized (syncObject) {
            logger.trace("synchronized (syncObject) DrawManager.Append");
            refreshFields[objCount]=index;
            objCount++;
            //Log.d("count" + objCount);
        }
    }

    public static void AddArray(IntArray items)
    {

        synchronized (syncObject) {
            logger.trace("synchronized (syncObject) DrawManager.AddArray");
            final int []arrayI=items.getFullArray();
            final int count=items.size();

            System.arraycopy(arrayI, 0, refreshFields, objCount, count);

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
            logger.trace("synchronized (syncObject) DrawManager.RedrawAll");
            MapManager.BindForeground();
            for(int i=0;i<objCount;i++)
            {
                MapManager.RedrawPixmap(refreshFields[i]);
            }
            objCount=0;
        }
    }
}
