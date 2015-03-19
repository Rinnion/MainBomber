package com.me.Bombs.Behavior;

import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;

public abstract class RecyclableBehavior extends RecyclableObject implements IBehavior {
    public RecyclableBehavior(RecyclableArray array) {
        super(array);
    }
}
