package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Utility.RecyclableArray;

public class SmallChestTreasure extends ChestTreasure {

    private AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateTreasure(AnimatedSprite.TREASURE_BIG_CHEST);

    public SmallChestTreasure(RecyclableArray array) {
        super(array);
    }

    @Deprecated
    public SmallChestTreasure update(Vector2I pos) {
        return update(pos.x, pos.y);
    }

    public SmallChestTreasure update(int x, int y) {
        super.update(x, y, 100, animatedSprite);
        return this;
    }

}

