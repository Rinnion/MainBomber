package com.me.bomb.behavior;

import com.me.Map.MapManager;
import com.me.Utility.RecyclableArray;
import com.me.bomb.AbstractBomb;
import com.me.bomb.activator.RandomTimeActivator;
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
    private RandomCircleExplosion explosion;

    public JumpBehavior(RecyclableArray array) {
        super(array);
    }

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
    public boolean detonate(AbstractBomb bomb, long time) {
        explosion.detonate(bomb, time);

        //Exit if this is last jump;
        if (--jumps == 0) return true;

        int dXInt = ((int) (Math.random() * radius * 2)) - radius;
        int dYInt = ((int) (Math.random() * radius * 2)) - radius;
        int newX = bomb.position.x + dXInt;
        int newY = bomb.position.y + dYInt;

        if (newX < radius) newX = radius;
        if (newY < radius) newY = radius;
        if (newX > MapManager.maxCel - radius - 1) newX = MapManager.maxCel - radius - 1;
        if (newY > MapManager.maxCel - radius - 1) newY = MapManager.maxCel - radius - 1;

        bomb.position.x = newX;
        bomb.position.y = newY;

        bomb.setActivator(MemoryManager.take(RandomTimeActivator.class).update(bomb));
        //add putbomb action
        MapManager.fieldObjects[bomb.getIndex()].remove(bomb);
        MemoryManager.take(PutBombAction.class).update(bomb.getOwner(), time, bomb);
        explosion.update();

        //Do not remove bomb from field
        return false;
    }

    @Override
    public String toString() {
        return String.format("%s: [j: %s][r: %s][e: %s]", getClass().getSimpleName(), jumps, radius, explosion);
    }
}
