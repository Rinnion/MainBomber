package com.me.controlers.treasure;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;

public class BigChestTreasure extends ChestTreasure {
    public BigChestTreasure(Vector2I pos){
        super(pos, 1000, AnimatedSprite.TREASURE_BIG_CHEST);
    }
}
