package com.me.Bombs.Activator;

import com.me.Bombs.AbstractBomb;

import java.util.Calendar;

/**
* Created by tretyakov on 26.09.2014.
*/
public class TimeActivator implements IActivator{
    public static final long DEFAULT_TIME = 3000;
    public static final long MAX_TIME = 3600000; //one hour
    public static final long MIN_TIME = 100;

    private AbstractBomb bomb;
    private long time;

    public TimeActivator(AbstractBomb bomb, long time){
        this.bomb = bomb;
        this.time = time;
    }

    @Override
    public void Register(long time) {
        bomb.ActivationTime = Calendar.getInstance().getTimeInMillis() + this.time;
    }

    @Override
    public void Calculate(long time) {
        bomb.activate(time);
    }

    @Override
    public String toString() {
        return (String.format("%s: [time: %s]", getClass().getSimpleName(), time));
    }
}
