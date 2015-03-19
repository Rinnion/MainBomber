package com.me.Utility;

public abstract class RecyclableObject implements IRecyclable {

    private RecyclableArray mArray;

    public RecyclableObject(RecyclableArray array) {
        //Log.d(String.format("ctor " + getClass().getCanonicalName()));
        mArray = array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void recycle() {
        mArray.recycle(this);
    }

}
