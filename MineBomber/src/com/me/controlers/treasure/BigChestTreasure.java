package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.Utility.RecyclableArray;

public class BigChestTreasure extends ChestTreasure {

    private AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateTreasure(AnimatedSprite.TREASURE_BIG_CHEST);

    public BigChestTreasure(RecyclableArray array) {
        super(array);
    }

    public BigChestTreasure update(int x, int y) {
        super.update(x, y, 1000, animatedSprite);
        return this;
    }

}
