package com.me.bomb.activator;

import com.me.Utility.RecyclableArray;
import com.me.bomb.DestBomb;

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
    public void Calculate(long time) {
        bomb.getOwner().addActivator(this);
    }

    public boolean Activate(long time) {
        bomb.ActivationTime = time;
        return true;
    }

    @Override
    public String toString() {
        return (String.format("%s", getClass().getSimpleName()));
    }

}

