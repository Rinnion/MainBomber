package com.me.minebomber;


import com.me.Map.MapManager;
import com.me.Utility.IntegerQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by alekseev on 03.04.2014.
 */
public class DrawManager {

    public final static int FIELDS_COUNT = 20000;
    public static final IntegerQueue redrawArray2 = new IntegerQueue();
    static Logger logger = LoggerFactory.getLogger(DrawManager.class);

    public static void RedrawAll()
    {
        MapManager.BindForeground();
        int pop;
        do {
            pop = redrawArray2.pop();
            MapManager.RedrawPixmap(pop);
        } while (pop != 0);
    }

}
