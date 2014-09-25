package com.me.controlers.treasure;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;

/**
 * Created by tretyakov on 12.05.2014.
 */
public abstract class ChestTreasure extends AbstractTreasure {

    private final long value;

    public ChestTreasure(Vector2I pos, long value, String spriteName){
        super(null, pos, 1, AnimatedSprite.Factory.CreateTreasure(spriteName));
        this.value = value;
    }

    @Override
    protected boolean collect(IPlayer who, long time) {
        PlayerController.addMoney(who, value);
        return true;
    }
}

