package com.me.controlers.actions;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.controlers.ActionController;
import com.me.controlers.RecyclableAction;

/**
 * Created by tretyakov on 25.09.2014.
 */
public class ActivateBombAction extends RecyclableAction
        implements ActionController.IGameAction {
    private IPlayer owner;
    private long time;

    public void update(IPlayer owner, long time) {
        this.owner = owner;
        this.time = time;
    }

    @Override
    public void Calculate(long time) {
        owner.activateBombs(time);
    }

    public static class Factory implements RecyclableArray.Factory<ActivateBombAction> {
        @Override
        public ActivateBombAction newItem() {
            return new ActivateBombAction();
        }
    }

}
