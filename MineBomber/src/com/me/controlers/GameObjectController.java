package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Map.MapInfo;
import com.me.Players.PlayerController;
import com.me.Utility.MyArray;
import com.me.logger.Log;
import com.me.minebomber.AbstractGameObject;
import com.me.Map.MapManager;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class GameObjectController {

    private static final String MOD_SRC="GameObjectController.";
    public  static final int  DEF_MAX_OBJECT=10000;
    public  static final int  DEF_MAX_ACTIONS_BUFFER=10000;

    private static final ArrayList<AbstractGameObject> objects = new ArrayList<AbstractGameObject>(DEF_MAX_OBJECT);
    private static final ArrayList<AbstractGameObject> bombToRemove=new ArrayList<AbstractGameObject>(DEF_MAX_OBJECT);



    private static final ArrayList<AbstractGameObject> actionsBuffer=new ArrayList<AbstractGameObject>(DEF_MAX_ACTIONS_BUFFER);

    public static void Render(SpriteBatch batch) {

        AbstractGameObject obj;

        final MapInfo[] mapInfos=MapManager.mapInfo;
             try {
                 for (int i = 0; i < objects.size(); i++) {
                     obj = objects.get(i);
                     int index = obj.index;
                     if (mapInfos[index].view) obj.Render(batch);
                 }
             }
             catch (ConcurrentModificationException _ex)
             {
                 Log.d(MOD_SRC + "Render() -> ConcurrentModificationException");
             }
             catch(IndexOutOfBoundsException _ex)
             {
                Log.d(MOD_SRC + "Render() -> IndexOutOfBoundsException");
             }


    }



    public static boolean isRoom(Vector2 position){
        int x = (int) position.x/MapManager.rowW;
        int y = (int) position.y/MapManager.rowH;
        int index = y*MapManager.maxCel + x;

        if (MapManager.fieldObjects[index].size() == MapManager.FIELD_CAPACITY){
            return false;
        }

        return true;
    }

    public static void calculate(long time)
    {


        synchronized (objects) {
            Log.d(String.valueOf(objects.size()));
        for (AbstractGameObject obj: objects) {

            if(obj.calculate(time))
                bombToRemove.add(obj);

        }


            for (AbstractGameObject obj : bombToRemove) {
                //Log.d("PlayerController.RemoveObject");
                    PlayerController.RemoveObject(obj);
                //Log.d("objects.remove");
                objects.remove(obj);

                MapManager.fieldObjects[obj.index].remove(obj);

            }
            bombToRemove.clear();

        }




       /**/
        //BombController.calculate(time);

    }

    public static void applyActions()
    {
        synchronized (objects) {
            for (AbstractGameObject obj : actionsBuffer) {
                int index = obj.index;
                MapManager.fieldObjects[index].add(obj);
                objects.add(obj);
            }


            actionsBuffer.clear();
        }
    }


    public static void Add(AbstractGameObject abstractGameObject) {

        synchronized (objects) {


            actionsBuffer.add(abstractGameObject);

        }
    }
}

