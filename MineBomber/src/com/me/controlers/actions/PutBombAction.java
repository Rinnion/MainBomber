package com.me.controlers.actions;

import com.me.Bombs.AbstractBomb;
import com.me.Players.IPlayer;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.logger.Log;

/**
 * Created by tretyakov on 25.09.2014.
 */
public class PutBombAction extends RecyclableObject
        implements ActionController.IGameAction {
    private IPlayer owner;
    private long time;
    private AbstractBomb obj;

    public void update(IPlayer owner, long time, AbstractBomb randomBomb) {
        this.owner = owner;
        this.time = time;
        this.obj = randomBomb;
    }

    @Override
    public void Calculate(long time) {
        Log.i("PutBombAction: " + obj.toString());
        GameObjectController.Add(obj);
        obj.activator.Register(time);
        recycle();
    }

    public static class Factory implements RecyclableArray.Factory<PutBombAction> {
        @Override
        public PutBombAction newItem() {
            return new PutBombAction();
        }
    }
}
