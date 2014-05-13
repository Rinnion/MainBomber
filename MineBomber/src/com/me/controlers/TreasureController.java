package com.me.controlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.Map.AbstractGameObject;
import com.me.controlers.treasure.AbstractTreasure;

import java.util.ArrayList;

public class TreasureController {

    public static void Remove(AbstractTreasure abstractTreasure) {
        GameObjectController.Remove(abstractTreasure);
    }

    public static void Add(AbstractTreasure abstractTreasure) {
        GameObjectController.Add(abstractTreasure);
    }

}
