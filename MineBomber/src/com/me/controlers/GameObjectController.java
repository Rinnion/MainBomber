package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.Map.AbstractGameObject;
import com.me.Map.MapManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class GameObjectController {

    static private ArrayList<AbstractGameObject> objects = new ArrayList<AbstractGameObject>();
    static private HashMap<AbstractGameObject, Integer> objectsPlace = new HashMap<AbstractGameObject, Integer>();

    public static void Render(SpriteBatch batch) {
        AbstractGameObject obj;
        for (int i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            obj.Render(batch);
        }
    }

    public static void Remove(AbstractGameObject abstractTreasure) {
        objects.remove(abstractTreasure);
        Integer index = objectsPlace.get(abstractTreasure);
        MapManager.fieldObjects[index].remove(abstractTreasure);
    }

    public static void Add(AbstractGameObject abstractTreasure) {
        int px = (int)abstractTreasure.position.x;
        int py = (int)abstractTreasure.position.y;
        int index = py * MapManager.maxCel + px;
        MapManager.fieldObjects[index].add(abstractTreasure);
        objectsPlace.put(abstractTreasure, index);
        objects.add(abstractTreasure);
    }
}

