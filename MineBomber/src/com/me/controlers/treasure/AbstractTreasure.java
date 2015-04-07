package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.AbstractGameObject;

public abstract class AbstractTreasure extends AbstractGameObject {

    boolean collected = false;

    public AbstractTreasure(RecyclableArray array) {
        super(array);
    }

    public void update(IPlayer player, int x, int y, int life, AnimatedSprite sprite) {
        super.update(player, x, y, life, sprite);
    }

    public void update(int x, int y, int life, AnimatedSprite sprite) {
        super.update(x, y, life, sprite);
    }

    @Override
    public void applyDamage(IPlayer who, int dmg, long time) {
        vanish();
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        collected = collected || collect(who, time);
        return collected;
    }

    @Override
    public void applyDig(IPlayer who, long time) {
    }

    @Override
    public boolean calculate(long time) {
        return collected;
    }

    protected abstract boolean collect(IPlayer who, long time);

    protected void vanish() {
        life = 0;
        //GameObjectController.Remove(this);
        collected = true;
    }

}
