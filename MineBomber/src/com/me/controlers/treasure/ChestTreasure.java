package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.Utility.RecyclableArray;

public abstract class ChestTreasure extends AbstractTreasure {

    private long value;

    public ChestTreasure(RecyclableArray array) {
        super(array);
    }

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

