package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.DestinationActivator;
import com.me.Bombs.Behavior.CircleExplosion;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class DestBomb extends AbstractBomb {

    private static AnimatedSprite animatedSprite=null;
    private static Sprite sprite = null;

    static
    {
        if(animatedSprite==null) {
            animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dst_bomb");
        }
    }

    public DestBomb(RecyclableArray array) {
        super(array);
    }

    public DestBomb update(IPlayer player, Vector2 pos) {
        super.update(player, new Vector2I((int) pos.x / MapManager.rowW, (int) pos.y / MapManager.rowH), animatedSprite);

        behavior = MemoryManager.take(CircleExplosion.class).update(100, 200, 20);
        activator = MemoryManager.take(DestinationActivator.class).update(this);

        return this;
    }


    @Override
    public boolean activate(long time) {
        ActivationTime = time;
        return true;
    }

    @Override
    public void digdamage(long time) {

    }

    @Override
    public boolean calculate(long time) {
        if (!(ActivationTime < time)) return false;
        super.calculate(time);
        return true;
    }

    @Override
    public void detonate(long time) {
        activate(time);
    }

}
