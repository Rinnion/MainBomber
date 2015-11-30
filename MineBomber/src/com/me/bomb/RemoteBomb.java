package com.me.bomb;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.DestinationActivator;
import com.me.bomb.behavior.CircleExplosion;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public class RemoteBomb extends AbstractBomb {

    private static AnimatedSprite animatedSprite=null;
    private static Sprite sprite = null;

    static
    {
        if(animatedSprite==null) {
            animatedSprite = AnimatedSprite.FactoryMethos.CreateBomb("dst_bomb");
        }
    }

    public RemoteBomb(RecyclableArray array) {
        super(array);
    }

    public RemoteBomb update(IPlayer player, int x, int y) {
        super.update(player, x, y, animatedSprite);

        behavior = MemoryManager.take(CircleExplosion.class).update(100, 200, 20);
        activator = MemoryManager.take(DestinationActivator.class).update(this);

        return this;
    }
}
