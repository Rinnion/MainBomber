package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.Bombs.AnimatedSpriteAnimator;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.minebomber.AbstractGameObject;

/**
 * Created by tretyakov on 12.05.2014.
 */
public abstract class AbstractTreasure extends AbstractGameObject {

    boolean collected = false;

    public void update(IPlayer player, Vector2I pos, int life, AnimatedSprite sprite) {
        super.update(player, pos, life, sprite);
    }

    public void update(Vector2I pos, int life, AnimatedSprite sprite) {
        super.update(pos, life, sprite);
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
