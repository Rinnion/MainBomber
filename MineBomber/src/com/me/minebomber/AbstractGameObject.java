package com.me.minebomber;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AnimatedSprite;
import com.me.Map.MapManager;
import com.me.Map.MapProperty;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableObject;

/**
 * Created by tretyakov on 16.04.2014.
 */
public abstract class AbstractGameObject extends RecyclableObject {
    public final IPlayer owner;
    public final int index;
    public int life;
    public Vector2I position;
    public boolean visible;

    protected AnimatedSprite sprite;



    public long calculateTime;


    public AbstractGameObject(IPlayer player, Vector2I pos, int life, AnimatedSprite sprite) {
        this.owner = player;
        position = new Vector2I(pos.x, pos.y);
        index = pos.getMapIndex();
        this.life = life;
        this.sprite = sprite;
        this.visible=true;
        //canremove=false;

        sprite.setPosition(
                pos.x * MapManager.rowW - (sprite.getWidth() / 2),
                pos.y * MapManager.rowH - (sprite.getHeight() / 2));
    }

    public void SetPosition(int x,int y)
    {
        position = new Vector2I(x, y);
        sprite.setPosition(
                x * MapManager.rowW - (sprite.getWidth() / 2),
                y * MapManager.rowH - (sprite.getHeight() / 2));
    }

    public AbstractGameObject(Vector2I pos, int life, AnimatedSprite sprite) {
        this(null, pos, life, sprite);
    }

    public void Render(Batch batch) {
        if(visible)sprite.draw(batch);
    }

    public abstract void applyDamage(IPlayer who, int dmg, long time) ;

    public abstract boolean applyTake(IPlayer who, long time) ;

    public abstract void applyDig(IPlayer who, long time) ;

    //True - удалить обьект, False - обьект не удалится
    public abstract boolean calculate(long time);

}
