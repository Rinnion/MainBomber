package com.me.Bombs;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Bombs.Behavior.RandomCircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.logger.Log;


/**
 * Created by alekseev on 27.03.2014.
 */
public class RandomBomb extends AbstractBomb {

    public RandomBomb(IPlayer player, Vector2 pos, int jumps, int radius) {
        super(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), AnimatedSprite.Factory.CreateBomb("bomb"));

        behavior = new JumpBehavior(jumps, radius, new RandomCircleExplosion());
        activator = new RandomTimeActivator(this);

        Log.d(String.format("ctor %s: [player:%s, pos:%s, jumps:%s, radius:%s]",
                getClass().getSimpleName(),
                player,
                pos,
                jumps,
                radius));

    }


    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {
        if (time < ActivationTime) return false;
        return super.calculate(time);
    }


    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }
}
