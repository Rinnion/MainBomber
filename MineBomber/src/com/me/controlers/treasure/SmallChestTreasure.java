package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Utility.RecyclableArray;

public class SmallChestTreasure extends ChestTreasure {

    public SmallChestTreasure update(Vector2I pos) {
        super.update(pos, 100, AnimatedSprite.TREASURE_BIG_CHEST);

        return this;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new SmallChestTreasure();
        }
    }

}

