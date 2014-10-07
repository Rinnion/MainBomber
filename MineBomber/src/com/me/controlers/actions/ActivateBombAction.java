package com.me.controlers.actions;

import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.controlers.ActionController;

/**
 * Created by tretyakov on 25.09.2014.
 */
public class ActivateBombAction extends RecyclableObject
        implements ActionController.IGameAction {
    private IPlayer owner;
    private long time;

    @Override
    public void recycle() {
        super.recycle();
    }

    public void update(IPlayer owner, long time) {
        this.owner = owner;
        this.time = time;
    }

    @Override
    public void Calculate(long time) {
        owner.activateBombs(time);
        recycle();
    }

    public static class Factory implements RecyclableArray.Factory<ActivateBombAction> {
        @Override
        public ActivateBombAction newItem() {
            return new ActivateBombAction();
        }
    }

}
