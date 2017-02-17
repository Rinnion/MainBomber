package com.me.bomb;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.bomb.activator.RandomTimeActivator;
import com.me.bomb.activator.RecyclableActivator;
import com.me.bomb.behavior.RecyclableBehavior;
import com.me.minebomber.AbstractGameObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tretyakov on 09.04.2014.
 *
 */
public abstract class AbstractBomb extends AbstractGameObject {
    private static Logger logger = LoggerFactory.getLogger(AbstractBomb.class);
    public RecyclableBehavior behavior = null;
    public RecyclableActivator activator = null;
    public boolean activated = false;

    public AbstractBomb(RecyclableArray array) {
        super(array);
    }

    public void update(IPlayer player, int x, int y, AnimatedSprite animatedSprite) {
        super.update(player, x, y, 1, animatedSprite);
        activated = false;
    }

    @Override
    public void damage(IPlayer who, int dmg, long time) {
        logger.debug("{} damaged [who:{}] [dmg:{}] [time:{}]", this, who, dmg, time);
        activated = true;
        life -= dmg;
    }

    @Override
    public boolean applyTake(IPlayer who, long time) {
        return false;
    }

    @Override
    public void dig(IPlayer who, long time) {
    }

    @Override
    public boolean logic(long frame) {
        if (activated | activator.logic(frame)) {
            activated = false;
            return behavior.detonate(this, frame);
        }
        return false;
    }

    @Override
    public void recycle() {
        behavior.recycle();
        activator.recycle();
        super.recycle();
    }

    @Override
    public String toString() {
        return getClassName() + ":" + getIdentifier() + ":[a:" + String.valueOf(activator) + "][b:" + String.valueOf(behavior) + "]";
    }

    public void setActivator(RandomTimeActivator update) {
        activator.recycle();
        activator = update;
    }
}
