package com.me.Bombs.Behavior;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.RandomBomb;
import com.me.controlers.ActionController;
import com.me.controlers.actions.PutBombAction;

/**
 * Created by alekseev on 18.09.2014.
 */
public class JumpBehavior implements IBehavior {

    public static final int DEFAULT_JUMPS = 25;
    public static final int DEFAULT_RADIUS = 10;
    private final int jumps;
    private final int radius;
    private final CircleExplosion explosion;

    public JumpBehavior(int jumps, int radius, CircleExplosion circleExplosion) {
        this.jumps = jumps;
        this.radius = radius;
        this.explosion = circleExplosion;
    }

    @Override
    public void detonate(AbstractBomb bomb, long time) {
        explosion.detonate(bomb, time);

        int dXInt = ((int) (Math.random() * radius * 2)) - radius;
        int dYInt = ((int) (Math.random() * radius * 2)) - radius;
        int newX = bomb.position.x + dXInt;
        int newY = bomb.position.y + dYInt;

        if (newX < radius) newX = radius;
        if (newY < radius) newY = radius;

        if (jumps == 1) return;

        ActionController.Add(
                new PutBombAction(
                        bomb.owner,
                        time,
                        new RandomBomb(
                                bomb.owner,
                                new Vector2(2 * newX, 2 * newY),
                                jumps - 1,
                                radius,
                                TimeActivator.DEFAULT_TIME)
                )
        );
    }
}
