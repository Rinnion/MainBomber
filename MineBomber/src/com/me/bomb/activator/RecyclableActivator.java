package com.me.bomb.activator;

import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;

public abstract class RecyclableActivator extends RecyclableObject implements IActivator {
    public RecyclableActivator(RecyclableArray array) {
        super(array);
    }
}