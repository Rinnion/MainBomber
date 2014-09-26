package com.me.controlers.treasure;

import com.me.Bombs.AnimatedSprite;
import com.me.TextManager.TextManager;
import com.me.TextManager.TextPool;
import com.me.TextManager.TextZoom;
import com.me.minebomber.AbstractGameObject;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.controlers.GameObjectController;

/**
 * Created by tretyakov on 12.05.2014.
 */
public abstract class AbstractTreasure extends AbstractGameObject {

    boolean collected=false;

    public AbstractTreasure(IPlayer player, Vector2I pos, int life, AnimatedSprite sprite) {
        super(player, pos, life, sprite);
    }

    public AbstractTreasure(Vector2I pos, int life, AnimatedSprite sprite) {
        super(pos, life, sprite);
    }

    @Override
    public void applyDamage(IPlayer who, int dmg, long time) {
        vanish();
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        collected=collected||collect(who, time);
        return collected;
    }

    @Override
    public void applyDig(IPlayer who, long time) {
    }

    @Override
    public boolean calculate(long time)
    {
       return collected;
    }

    protected abstract boolean collect(IPlayer who, long time);

    protected void vanish() {
        life = 0;
        //GameObjectController.Remove(this);
        collected=true;
    }

}
