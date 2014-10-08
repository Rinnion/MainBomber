package com.me.Bombs.Activator;

import com.me.Bombs.DestBomb;
import com.me.Utility.RecyclableArray;

/**
 * Created by tretyakov on 26.09.2014.
 */
public class DestinationActivator extends RecyclableActivator implements IActivator {
    private DestBomb bomb;

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

    public static class Factory implements RecyclableArray.Factory<DestinationActivator> {

        @Override
        public DestinationActivator newItem() {
            return new DestinationActivator();
        }
    }
}

