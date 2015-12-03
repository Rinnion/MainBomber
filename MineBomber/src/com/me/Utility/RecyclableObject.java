package com.me.Utility;

public abstract class RecyclableObject implements IRecyclable {

    private static long counter = 0;
    private long identity;
    private String className = "undefined";

    private RecyclableArray mArray;

    public RecyclableObject(RecyclableArray array) {
        mArray = array;
        identity = getNextIdentifier();
        className = getClass().getSimpleName();
    }

    private synchronized long getNextIdentifier() {
        return counter++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void recycle() {
        mArray.recycle(this);
    }

    @Override
    public String toString() {
        return String.format("%1$s:%2$s", className, identity);
    }

    public final long getIdentifier() {
        return identity;
    }

    public final String getClassName() {
        return className;
    }
}
