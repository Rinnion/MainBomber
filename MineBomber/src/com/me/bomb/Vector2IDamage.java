package com.me.bomb;

import com.me.ObjectMaskHelper.Vector2I;

/**
 * Created by tretyakov on 06.05.2014.
 */
public class Vector2IDamage extends Vector2I{

    public int damage;

    public Vector2IDamage(int x, int y, int damage) {
        super(x, y);
        this.damage = damage;
    }

    public Vector2IDamage(Vector2I vector2I, int damage) {
        this(vector2I.x, vector2I.y, damage);
    }
}
