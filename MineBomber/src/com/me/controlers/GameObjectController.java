package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Map.MapInfo;
import com.me.Players.PlayerController;
import com.me.logger.Log;
import com.me.minebomber.AbstractGameObject;
import com.me.Map.MapManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class GameObjectController {

    public  static final int  DEF_MAX_OBJECT=1000;
    private static final ArrayList<AbstractGameObject> objects = new ArrayList<AbstractGameObject>(DEF_MAX_OBJECT);
    private static final ArrayList<AbstractGameObject> bombToRemove=new ArrayList<AbstractGameObject>(DEF_MAX_OBJECT);


    public static void Render(SpriteBatch batch) {
        AbstractGameObject obj;

        final MapInfo[] mapInfos=MapManager.mapInfo;
         synchronized (objects) {
             for (int i = 0; i < objects.size(); i++) {
                 obj = objects.get(i);
                 int index = obj.index;
                 if (mapInfos[index].view) obj.Render(batch);
             }
         }
    }

    public static void Remove(AbstractGameObject abstractTreasure) {
        synchronized (objects) {
            objects.remove(abstractTreasure);
            Integer index = abstractTreasure.index;
            MapManager.fieldObjects[index].remove(abstractTreasure);
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


        for (AbstractGameObject obj: objects) {

            if(obj.calculate(time))
                bombToRemove.add(obj);

        }

        synchronized (objects) {
            for (AbstractGameObject obj : bombToRemove) {
                Log.d("PlayerController.RemoveObject");
                    PlayerController.RemoveObject(obj);
                Log.d("objects.remove");
                objects.remove(obj);
            }
        }


        bombToRemove.clear();
       /**/
        //BombController.calculate(time);

    }


    public static void Add(AbstractGameObject abstractGameObject) {
        int index = abstractGameObject.index;
        synchronized (objects) {

            MapManager.fieldObjects[index].add(abstractGameObject);
            objects.add(abstractGameObject);
        }
    }
}

