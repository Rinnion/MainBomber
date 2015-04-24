package com.me.bomb.activator;

import com.me.Utility.RecyclableArray;
import com.me.bomb.AbstractBomb;

import java.util.Calendar;

public class TimeActivator extends RecyclableActivator implements IActivator {
    public static final long DEFAULT_TIME = 3000;
    public static final long CASUAL_MIN_TIME = 500;
    public static final long CASUAL_MAX_TIME = 5000;
    public static final long MAX_TIME = 3600000; //one hour
    public static final long MIN_TIME = 100;

    private AbstractBomb bomb;
    private long time;

    public TimeActivator(RecyclableArray array) {
        super(array);
    }

    public TimeActivator update(AbstractBomb bomb, long time) {
        this.bomb = bomb;
        if (this.time < MIN_TIME) this.time = MIN_TIME;
        if (this.time > MAX_TIME) this.time = MAX_TIME;
        this.time = time;
        return this;
    }

    @Override
    public void Calculate(long time) {
        bomb.ActivationTime = Calendar.getInstance().getTimeInMillis() + this.time;
    }

    @Override
    public String toString() {
        return (String.format("%s: [time: %s]", getClass().getSimpleName(), time));
    }

}
