package com.me.bomb.behavior;

import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.MaskController;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Particles.ParticleManager;
import com.me.Utility.RecyclableArray;
import com.me.bomb.AbstractBomb;
import com.me.bomb.Vector2IDamage;

/**
 * Created by alekseev on 18.09.2014.
 */
public class CircleExplosion extends RecyclableBehavior implements IBehavior {

    public static final int MAX_DAMAGE = 1000;
    public static final int DEFAULT_DAMAGE = 1000;
    public static final int MIN_DAMAGE = 10;
    static final double MAX_RADIUS = 20;
    static int MIN_RADIUS = 5;

    public int dmgMin;
    public int dmgMax;
    public float range;

    public Vector2IDamage[] ExplodeMask;

    public CircleExplosion(RecyclableArray array) {
        super(array);
    }

    public CircleExplosion update(int dmgMin, int dmgMax, float range) {

        Vector2I[] vector2Is = MaskController.GetMask(range);
        ExplodeMask = new Vector2IDamage[vector2Is.length];
        for (int i = 0; i < vector2Is.length; i++) {
            int damage = (int) (dmgMin + (float) Math.random() * (dmgMax - dmgMin));
            ExplodeMask[i] = new Vector2IDamage(vector2Is[i], damage);
        }

        this.dmgMax = dmgMax;
        this.dmgMin = dmgMin;
        this.range = range;

        return this;
    }

    @Override
    public boolean detonate(AbstractBomb bomb, long time) {
        MapManager.addDamageToField(ExplodeMask, bomb.position.x, bomb.position.y);
        ParticleManager.Fire(bomb.position.x * MapManager.rowW, bomb.position.y * MapManager.rowH, range * 4);
        return true;
    }

    @Override
    public String toString() {
        return String.format("%s: [min: %s][max: %s][range: %s]", getClass().getSimpleName(), dmgMin, dmgMax, range);
    }

}
