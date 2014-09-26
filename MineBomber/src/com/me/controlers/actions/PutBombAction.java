package com.me.controlers.actions;

import com.me.Bombs.AbstractBomb;
import com.me.Players.IPlayer;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.logger.Log;

/**
 * Created by tretyakov on 25.09.2014.
 */
public class PutBombAction implements ActionController.IGameAction {
    private final IPlayer owner;
    private final long time;
    private final AbstractBomb obj;

    public PutBombAction(IPlayer owner, long time, AbstractBomb randomBomb) {
        this.owner = owner;
        this.time = time;
        this.obj = randomBomb;
    }

    @Override
    public void Calculate(long time) {
        Log.i("PutBombAction: " + obj.toString());
        GameObjectController.Add(obj);
        obj.activator.Register(time);
    }
}
