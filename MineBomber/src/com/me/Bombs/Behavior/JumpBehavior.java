package com.me.Bombs.Behavior;

import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Bombs.RandomBomb;
import com.me.Map.MapManager;
import com.me.Utility.RecyclableArray;
import com.me.controlers.ActionController;
import com.me.controlers.actions.PutBombAction;
import com.me.minebomber.MemoryManager;

/**
 * Created by alekseev on 18.09.2014.
 */
public class JumpBehavior extends RecyclableBehavior implements IBehavior {

    public static final int DEFAULT_JUMPS = 25;
    public static final int DEFAULT_RADIUS = 10;
    private int jumps;
    private int radius;
    private CircleExplosion explosion;

    public JumpBehavior update(int jumps, int radius) {
        this.jumps = jumps;
        this.radius = radius;
        this.explosion = MemoryManager.take(RandomCircleExplosion.class).update();
        return this;
    }

    @Override
    public void recycle() {
        explosion.recycle();
        super.recycle();
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
        if (newX > MapManager.maxCel - radius - 1) newX = MapManager.maxCel - radius - 1;
        if (newY > MapManager.maxCel - radius - 1) newY = MapManager.maxCel - radius - 1;

        if (jumps == 1) return;

        PutBombAction take = MemoryManager.take(PutBombAction.class);
        take.update(bomb.getOwner(),
                time,
                MemoryManager.take(RandomBomb.class).update(bomb.getOwner(),
                        new Vector2(2 * newX, 2 * newY),
                        jumps - 1,
                        radius)
        );

        ActionController.Add(take);
    }

    @Override
    public String toString() {
        return String.format("%s: [j: %s][r: %s][e: %s]", getClass().getSimpleName(), jumps, radius, explosion);
    }

    public static class Factory implements RecyclableArray.Factory<JumpBehavior> {

        @Override
        public JumpBehavior newItem() {
            return new JumpBehavior();
        }
    }
}
