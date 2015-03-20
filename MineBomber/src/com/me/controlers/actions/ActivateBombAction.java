package com.me.controlers.actions;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.controlers.ActionController;

public class ActivateBombAction extends RecyclableAction
        implements ActionController.IGameAction {
    private IPlayer owner;
    private long time;

    public ActivateBombAction(RecyclableArray array) {
        super(array);
    }

    public ActivateBombAction update(IPlayer owner, long time) {
        this.owner = owner;
        this.time = time;
        return this;
    }

    @Override
    public void Calculate(long time) {
        owner.activateBombs(time);
    }

}
