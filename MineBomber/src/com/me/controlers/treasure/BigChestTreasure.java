package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Utility.RecyclableArray;

public class BigChestTreasure extends ChestTreasure {

    public BigChestTreasure update(Vector2I pos) {
        super.update(pos, 1000, AnimatedSprite.TREASURE_BIG_CHEST);
        return this;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new BigChestTreasure();
        }
    }
}
