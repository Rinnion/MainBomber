package com.me.controlers.actions;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.bomb.AbstractBomb;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.minebomber.MineBomber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tretyakov on 25.09.2014.
 *
 */
public class PutBombAction extends RecyclableObject
        implements ActionController.IGameAction {

    Logger logger = LoggerFactory.getLogger(PutBombAction.class);

    private IPlayer owner;
    private long time;
    private AbstractBomb obj;

    public PutBombAction(RecyclableArray array) {
        super(array);
    }

    @Override
    public void Calculate(long time) {
        logger.trace("PutBombAction: {}", obj.toString());
        MineBomber.GameObjectController.Add(obj);
        obj.activator.Calculate(time);
    }

    public PutBombAction update(IPlayer player, long time, AbstractBomb bomb) {
        this.owner = player;
        this.time = time;
        this.obj = bomb;
        return this;
    }
}
