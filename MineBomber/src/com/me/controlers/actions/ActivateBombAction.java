package com.me.controlers.actions;

import com.me.Bombs.AbstractBomb;
import com.me.Bombs.DestBomb;
import com.me.Players.IPlayer;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;

import java.util.ArrayList;

/**
 * Created by tretyakov on 25.09.2014.
 */
public class ActivateBombAction implements ActionController.IGameAction {
    private final IPlayer owner;
    private final long time;

    public ActivateBombAction(IPlayer owner, long time) {
        this.owner = owner;
        this.time = time;
    }

    @Override
    public void Calculate(long time) {
        owner.activateBombs(time);
    }

}
