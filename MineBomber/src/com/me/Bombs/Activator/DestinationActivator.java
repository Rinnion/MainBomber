package com.me.Bombs.Activator;

import com.me.Bombs.DestBomb;
import com.me.Players.AbstractPlayer;

/**
* Created by tretyakov on 26.09.2014.
*/
public class DestinationActivator implements IActivator{
    private DestBomb bomb;

    public DestinationActivator(DestBomb bomb){
        this.bomb = bomb;
    }

    @Override
    public void Register(long time) {
        bomb.owner.addActivator(this);
    }

    @Override
    public void Calculate(long time) {
        bomb.activate(time);
    }
}
