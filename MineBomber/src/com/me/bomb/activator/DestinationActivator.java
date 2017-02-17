package com.me.bomb.activator;

import com.me.Utility.RecyclableArray;
import com.me.bomb.RemoteBomb;

public class DestinationActivator extends RecyclableActivator implements IActivator {
    private RemoteBomb bomb;
    private boolean activated;

    public DestinationActivator(RecyclableArray array) {
        super(array);
    }

    public DestinationActivator update(RemoteBomb bomb) {
        this.bomb = bomb;
        activated = false;
        return this;
    }

    @Override
    public boolean logic(long time) {
        return activated;
    }

    public void Activate() {
        activated = true;
    }

}

