package com.me.controlers.treasure;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;

public class SmallChestTreasure extends ChestTreasure {
    public SmallChestTreasure(Vector2 pos){
        super(pos, 100, AnimatedSprite.TREASURE_BIG_CHEST);
    }
}

