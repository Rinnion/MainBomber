package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.minebomber.AbstractGameObject;
import com.me.Map.MapManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class GameObjectController {

    static private ArrayList<AbstractGameObject> objects = new ArrayList<AbstractGameObject>();

    public static void Render(SpriteBatch batch) {
        AbstractGameObject obj;
        for (int i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            obj.Render(batch);
        }
    }

    public static void Remove(AbstractGameObject abstractTreasure) {
        objects.remove(abstractTreasure);
        Integer index = abstractTreasure.index;
        MapManager.fieldObjects[index].remove(abstractTreasure);
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

    public static void Add(AbstractGameObject abstractGameObject) {
        int index = abstractGameObject.index;
        MapManager.fieldObjects[index].add(abstractGameObject);
        objects.add(abstractGameObject);
    }
}

