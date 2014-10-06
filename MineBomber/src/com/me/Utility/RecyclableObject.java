package com.me.Utility;

/**
* Created by tretyakov on 06.10.2014.
*/
public abstract class RecyclableObject {
    private boolean updated = false;
    public RecyclableArray rl;

    public void update(){
        updated = true;
    }

    public void recycle(){
        rl.recycle(this);
        updated = false;
    }
}
