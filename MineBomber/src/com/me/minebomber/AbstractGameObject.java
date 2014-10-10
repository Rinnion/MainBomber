package com.me.minebomber;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.me.Bombs.AnimatedSprite;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableObject;

/**
 * Created by tretyakov on 16.04.2014.
 */
public abstract class AbstractGameObject extends RecyclableObject {
    public int life;
    public Vector2I position;
    public boolean visible;
    protected IPlayer owner;
    protected int index;
    protected AnimatedSprite sprite;

    public AbstractGameObject() {
        position=new Vector2I(0,0);
    }

    public int getIndex() {
        return index;
    }

    public IPlayer getOwner() {
        return owner;
    }

    public void update(Vector2I pos, int life, AnimatedSprite sprite) {
        update(null, pos, life, sprite);
    }

    public void update(IPlayer player, Vector2I pos, int life, AnimatedSprite sprite) {
        this.owner = player;
        position.x =pos.x;// = new Vector2I(pos.x, pos.y);
        position.y=pos.y;


        index = pos.getMapIndex();
        this.life = life;
        this.sprite = sprite;
        this.visible = true;

        sprite.setPosition(
                pos.x * MapManager.rowW - (sprite.getWidth() / 2),
                pos.y * MapManager.rowH - (sprite.getHeight() / 2));
    }

    public void Render(Batch batch) {
        if (visible) sprite.draw(batch);
    }

    public abstract void applyDamage(IPlayer who, int dmg, long time);

    public abstract boolean applyTake(IPlayer who, long time);

    public abstract void applyDig(IPlayer who, long time);

    //True - удалить обьект, False - обьект не удалится
    public abstract boolean calculate(long time);

}
