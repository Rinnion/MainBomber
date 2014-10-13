package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.Bombs.AnimatedSpriteAnimator;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;

public class BigChestTreasure extends ChestTreasure {

    private AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateTreasure(AnimatedSprite.TREASURE_BIG_CHEST);

    public BigChestTreasure update(Vector2I pos) {
        super.update(pos, 1000, animatedSprite);
        return this;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new BigChestTreasure();
        }
    }
}
