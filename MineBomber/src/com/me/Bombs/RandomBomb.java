package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Bombs.Behavior.RandomCircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public final class RandomBomb extends AbstractBomb {

    public RandomBomb update(IPlayer player, Vector2 pos, int jumps, int radius) {
        update(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("bomb"));

        RandomCircleExplosion randomExplosion = MemoryManager.take(RandomCircleExplosion.class).update();
        behavior = MemoryManager.take(JumpBehavior.class).update(jumps, radius, randomExplosion);
        activator = MemoryManager.take(RandomTimeActivator.class).update(this);
        return this;
    }

    @Override
    public void digdamage(long time) {
    }

    @Override
    public boolean calculate(long time) {
        if (time < ActivationTime) return false;
        super.calculate(time);
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
