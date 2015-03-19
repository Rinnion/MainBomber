package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class Dynamite extends AbstractBomb {
    AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dyn");

    public Dynamite(RecyclableArray array) {
        super(array);
    }

    public Dynamite update(IPlayer player, Vector2 pos, long activationTime) {
        super.update(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), animatedSprite);

        behavior = MemoryManager.take(CircleExplosion.class).update(100, 200, 24);
        activator = MemoryManager.take(TimeActivator.class).update(this, activationTime);

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

}
