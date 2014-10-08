package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.Teramorf;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class FilledBomb extends AbstractBomb {

    private int delay = 2000;

    public FilledBomb update(IPlayer player, Vector2 pos) {
        super.update(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("dyn"));

        behavior = MemoryManager.take(Teramorf.class).update((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH, 81);
        activator = MemoryManager.take(TimeActivator.class).update(this, 3000);

        return this;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {

        if (time < ActivationTime) return false;
        super.calculate(time);
        ActivationTime = time + delay;
        //iteration--;
        return false;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new FilledBomb();
        }
    }
}
