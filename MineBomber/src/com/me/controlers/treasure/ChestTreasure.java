package com.me.controlers.treasure;

import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.Utility.RecyclableArray;
import com.me.bomb.AnimatedSprite;

public abstract class ChestTreasure extends AbstractTreasure {

    private long value;

    public ChestTreasure(RecyclableArray array) {
        super(array);
    }

    public ChestTreasure update(int x, int y, long value, AnimatedSprite animator) {
        super.update(null, x, y, 1, animator);
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

