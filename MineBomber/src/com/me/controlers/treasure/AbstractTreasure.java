package com.me.controlers.treasure;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.Map.AbstractGameObject;
import com.me.Players.IPlayer;
import com.me.controlers.GameObjectController;

/**
 * Created by tretyakov on 12.05.2014.
 */
public abstract class AbstractTreasure extends AbstractGameObject {

    public AbstractTreasure(IPlayer player, Vector2 pos, int life, AnimatedSprite sprite) {
        super(player, pos, life, sprite);
    }

    public AbstractTreasure(Vector2 pos, int life, AnimatedSprite sprite) {
        super(pos, life, sprite);
    }

    @Override
    public void applyDamage(IPlayer who, int dmg, long time) {
        vanish();
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        collect(who, time);
        return true;
    }

    @Override
    public void applyDig(IPlayer who, long time) {
    }

    protected abstract void collect(IPlayer who, long time);

    protected void vanish() {
        GameObjectController.Remove(this);
    }

}
