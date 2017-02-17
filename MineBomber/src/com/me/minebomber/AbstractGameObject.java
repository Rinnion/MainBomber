package com.me.minebomber;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.bomb.AnimatedSprite;
import com.me.bomb.AnimatedSpriteAnimator;

/**
 * Created by tretyakov on 16.04.2014.
 */
public abstract class AbstractGameObject extends RecyclableObject {
    public int life;
    public Vector2I position;
    public boolean visible;
    public long time;
    protected IPlayer owner;
    protected int index;
    protected AnimatedSpriteAnimator sprite;

    public AbstractGameObject(RecyclableArray array) {
        super(array);
        position=new Vector2I(0,0);
    }

    public int getIndex() {
        return index;
    }

    public IPlayer getOwner() {
        return owner;
    }

    public void update(int x, int y, int life, AnimatedSprite sprite) {
        update(null, x, y, life, sprite);
    }

    public void update(IPlayer player, int x, int y, int life, AnimatedSprite sprite) {
        this.owner = player;
        position.x = x;
        position.y = y;
        index = position.getMapIndex();
        this.life = life;
        this.sprite = MemoryManager.take(AnimatedSpriteAnimator.class).update(sprite);
        this.visible = true;
    }

    @Override
    public void recycle() {
        sprite.recycle();
        super.recycle();
    }

    public void Render(Batch batch) {
        if (visible) sprite.draw(batch,
                position.x * MapManager.rowW,
                position.y * MapManager.rowH);
    }

    //object receive damage
    public abstract void damage(IPlayer who, int dmg, long time);

    //object receive take action from unit
    public abstract boolean applyTake(IPlayer who, long time);

    //object receive dig action from unit
    public abstract void dig(IPlayer who, long time);

    //object logic over time
    //True - удалить обьект, False - обьект не удалится
    public abstract boolean logic(long time);

}
