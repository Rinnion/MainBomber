package com.me.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.Bombs.BombProperty;
import javafx.geometry.Pos;

/**
 * Created by tretyakov on 16.04.2014.
 */
public abstract class AbstractGameObject {
    public int life;
    public Vector2 Position;

    protected boolean destroyed=false;
    protected boolean visible;
    protected AnimatedSprite sprite;

    public AbstractGameObject(Vector2 pos, int life, AnimatedSprite sprite) {
        Position = pos;
        this.life = life;
        visible=true;
        this.sprite = sprite;
    }

    public void Render(Batch batch) {
        if(destroyed) return;

        if(visible)
        {
            sprite.draw(batch);
        }
    }

    public abstract void receiveDamage(int dmg, long time);
}
