package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.Bombs.AnimatedSpriteAnimator;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.minebomber.MemoryManager;

/**
 * Created by tretyakov on 12.05.2014.
 */
public abstract class ChestTreasure extends AbstractTreasure {

    private long value;

    public ChestTreasure update(Vector2I pos, long value, AnimatedSprite animator) {
        super.update(null, pos, 1, animator);
        this.value = value;
        return this;
    }

    @Override
    protected boolean collect(IPlayer who, long time) {
        PlayerController.addMoney(who, value);
        PlayerController.addRandomWeapon(who);
        return true;
    }
}

