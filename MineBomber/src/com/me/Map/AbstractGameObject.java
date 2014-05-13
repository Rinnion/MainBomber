package com.me.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.Players.IPlayer;

/**
 * Created by tretyakov on 16.04.2014.
 */
public abstract class AbstractGameObject {
    public final IPlayer owner;
    public int life;
    public Vector2 position;

    protected AnimatedSprite sprite;

    public AbstractGameObject(IPlayer player, Vector2 pos, int life, AnimatedSprite sprite) {
        this.owner = player;
        position = new Vector2(pos.x, pos.y);
        this.life = life;
        this.sprite = sprite;
        sprite.setPosition((pos.x-(sprite.getWidth()/2))*MapManager.rowW, (pos.y-(sprite.getHeight ()/2))*MapManager.rowH);
    }

    public AbstractGameObject(Vector2 pos, int life, AnimatedSprite sprite) {
        this(null, pos, life, sprite);
    }

    public void Render(Batch batch) {
        sprite.draw(batch);
    }

    public abstract void applyDamage(IPlayer who, int dmg, long time) ;

    public abstract boolean applyTake(IPlayer who, long time) ;

    public abstract void applyDig(IPlayer who, long time) ;
}
