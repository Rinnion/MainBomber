package com.me.controlers;

import com.me.controlers.treasure.AbstractTreasure;

public class TreasureController {

    public static void Remove(AbstractTreasure abstractTreasure) {
        GameObjectController.Remove(abstractTreasure);
    }

    public static void Add(AbstractTreasure abstractTreasure) {
        GameObjectController.Add(abstractTreasure);
    }

}
