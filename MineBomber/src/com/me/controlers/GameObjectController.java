package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.minebomber.AbstractGameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class GameObjectController {

    static Logger logger = LoggerFactory.getLogger(GameObjectController.class);

    public final int DEF_MAX_OBJECT = 10000;
    private final ArrayList<AbstractGameObject> objects = new ArrayList<>(DEF_MAX_OBJECT);
    private final ArrayList<AbstractGameObject> bombToRemove = new ArrayList<>(DEF_MAX_OBJECT);

    public void Render(SpriteBatch batch) {

        AbstractGameObject obj;

        final MapInfo[] mapInfos = MapManager.mapInfo;
        try {
            for (int i = 0; i < objects.size(); i++) {
                obj = objects.get(i);
                int index = obj.getIndex();
                if (mapInfos[index].view) obj.Render(batch);
            }
        } catch (ConcurrentModificationException | IndexOutOfBoundsException _ex) {
            logger.debug("exception:", _ex);
        }
    }

    public boolean isRoom(Vector2 position) {
        int x = (int) position.x / MapManager.rowW;
        int y = (int) position.y / MapManager.rowH;
        int index = y * MapManager.maxCel + x;

        if (MapManager.fieldObjects[index].size() == MapManager.FIELD_CAPACITY) {
            return false;
        }

        return true;
    }

    public void calculate(long time) {

        for (AbstractGameObject obj : objects) {
            if (obj.calculate(time))
                bombToRemove.add(obj);
        }

        for (AbstractGameObject obj : bombToRemove) {
            objects.remove(obj);
            MapManager.fieldObjects[obj.getIndex()].remove(obj);
            obj.recycle();
        }
        bombToRemove.clear();
    }

    public void Add(AbstractGameObject ago) {
        objects.add(ago);
        MapManager.fieldObjects[ago.getIndex()].add(ago);
    }
}

