package com.me.Utility;

import com.me.logger.Log;
import com.me.minebomber.MemoryManager;

/**
 * Created by tretyakov on 06.10.2014.
 */
public abstract class RecyclableObject implements IRecyclable {

    public RecyclableObject() {

        //Log.d(String.format("ctor " + getClass().getCanonicalName()));
    }

    @Override
    public void recycle() {
        MemoryManager.recycle(this);
    }

}
