package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.minebomber.MemoryManager;


/**
 * Created by alekseev on 27.03.2014.
 */
public final class RandomBomb extends AbstractBomb {


    private static AnimatedSprite animatedSprite=null;
    private Vector2I mapPosition;
    private Vector2 pos;



    static
    {
        if(animatedSprite==null)
            animatedSprite= AnimatedSprite.FactoryMethos.CreateBomb("bomb");
    }
    public RandomBomb()
    {
        mapPosition=new Vector2I();
        pos=new Vector2();
    }

    public RandomBomb update(IPlayer player, Vector2 pos, int jumps, int radius) {
        mapPosition.x= (int) (pos.x / MapManager.rowW);
        mapPosition.y= (int) (pos.y / MapManager.rowH);
        this.pos.x=pos.x;
        this.pos.y=pos.y;

        update(player, mapPosition, animatedSprite);

        behavior = MemoryManager.take(JumpBehavior.class).update(jumps, radius);
        activator = MemoryManager.take(RandomTimeActivator.class).update(this);

        return this;
    }


    @Override
    public void digdamage(long time) {
    }

    @Override
    public boolean calculate(long time) {
        if (time < ActivationTime) return false;
        super.calculate(time);
        return true;
    }

    @Override
    public void detonate(long time) {
        ActivationTime = time;
    }

    public static class Factory implements RecyclableArray.Factory {

        @Override
        public Object newItem() {
            return new RandomBomb();
        }
    }

}
