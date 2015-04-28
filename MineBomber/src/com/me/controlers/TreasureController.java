package com.me.controlers;

import com.me.controlers.treasure.AbstractTreasure;
import com.me.minebomber.MineBomber;

public class TreasureController {

    public static void Add(AbstractTreasure abstractTreasure) {
        MineBomber.GameObjectController.Add(abstractTreasure);
    }

}
