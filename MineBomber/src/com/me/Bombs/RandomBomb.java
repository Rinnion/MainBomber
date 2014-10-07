package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Bombs.Behavior.RandomCircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;


/**
 * Created by alekseev on 27.03.2014.
 */
public final class RandomBomb extends AbstractBomb {

    public RandomBomb update(IPlayer player, Vector2 pos, int jumps, int radius) {
        update(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("bomb"));

        behavior = new JumpBehavior(jumps, radius, new RandomCircleExplosion());
        activator = new RandomTimeActivator(this);

        return this;
    }

    @Override
    public void digdamage(long time) {
    }

    @Override
    public boolean calculate(long time) {
        if (time < ActivationTime) return false;
        super.calculate(time);
        recycle();
        return true;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new RandomBomb();
        }
    }

}
