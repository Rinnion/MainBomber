package com.me.Bombs.Activator;

import com.me.Bombs.DestBomb;
import com.me.Utility.RecyclableArray;

public class DestinationActivator extends RecyclableActivator implements IActivator {
    private DestBomb bomb;

    public DestinationActivator(RecyclableArray array) {
        super(array);
    }

    public DestinationActivator update(DestBomb bomb) {
        this.bomb = bomb;
        return this;
    }

    @Override
    public void Register(long time) {
        bomb.getOwner().addActivator(this);
    }

    @Override
    public void Calculate(long time) {
        bomb.activate(time);
    }

    @Override
    public String toString() {
        return (String.format("%s", getClass().getSimpleName()));
    }

}

