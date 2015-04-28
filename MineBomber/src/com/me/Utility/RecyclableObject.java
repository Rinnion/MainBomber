package com.me.Utility;

public abstract class RecyclableObject implements IRecyclable {

    private RecyclableArray mArray;

    public RecyclableObject(RecyclableArray array) {
        mArray = array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void recycle() {
        mArray.recycle(this);
    }

}
