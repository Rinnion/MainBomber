package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;
import com.me.Bombs.DestBomb;

import java.util.Calendar;

/**
* Created by tretyakov on 26.09.2014.
*/
public class TimeActivator implements IActivator{
    private AbstractBomb bomb;
    private long time;

    public TimeActivator(AbstractBomb bomb, long time){
        this.bomb = bomb;
        this.time = time;
    }

    @Override
    public void Register(long time) {
        bomb.ActivationTime = Calendar.getInstance().getTimeInMillis() + time;
    }

    @Override
    public void Calculate(long time) {
        bomb.activate(time);
    }
}
