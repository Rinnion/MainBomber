package com.me.controlers.treasure;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;

public class BigChestTreasure extends ChestTreasure {
    public BigChestTreasure(Vector2 pos){
        super(pos, 1000, AnimatedSprite.TREASURE_BIG_CHEST);
    }
}
