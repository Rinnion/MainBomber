package com.me.bomb.activator;

import com.me.Utility.RecyclableArray;
import com.me.bomb.AbstractBomb;
import com.me.minebomber.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeActivator extends RecyclableActivator implements IActivator {
    public static final long DEFAULT_TIME = 3000 / Settings.LOGIC_FRAME_INTERVAL;
    public static final long CASUAL_MIN_TIME = 500 / Settings.LOGIC_FRAME_INTERVAL;
    public static final long CASUAL_MAX_TIME = 5000 / Settings.LOGIC_FRAME_INTERVAL;
    public static final long MAX_TIME = 3600000 / Settings.LOGIC_FRAME_INTERVAL; //one hour
    public static final long MIN_TIME = 100 / Settings.LOGIC_FRAME_INTERVAL;
    private static Logger logger = LoggerFactory.getLogger(TimeActivator.class);
    private AbstractBomb bomb;
    private long time;

    public TimeActivator(RecyclableArray array) {
        super(array);
    }

    public TimeActivator update(AbstractBomb bomb, long time) {
        logger.debug("update {} [bomb:{}][time:{}]", this, bomb, time);
        this.bomb = bomb;
        if (this.time < MIN_TIME) this.time = MIN_TIME;
        if (this.time > MAX_TIME) this.time = MAX_TIME;
        this.time = time;
        return this;
    }

    @Override
    public boolean logic(long time) {
        return (--this.time) == 0;
    }

    @Override
    public String toString() {
        return (String.format("%1$s:%2$s[time:%3$s]", getClassName(), getIdentifier(), time));
    }

}
