package com.me.Utility;

import java.lang.reflect.Array;

/**
 * Created by tretyakov on 24.04.2015.
 */
public class RollingQueue<T> {

    static int BUFFER_SIZE = 10000;
    final int size;
    volatile int produced;
    volatile int consumed;
    T[] buffer;

    @SuppressWarnings("unchecked")
    public RollingQueue(Class<T> cls, int size) {
        buffer = (T[]) Array.newInstance(cls, BUFFER_SIZE);
        this.size = size;
    }

    @SuppressWarnings("unchecked")
    public RollingQueue(Class<T> cls) {
        this(cls, BUFFER_SIZE);
    }


    public boolean push(T obj) {
        if (produced - consumed == BUFFER_SIZE) return false;
        buffer[produced % BUFFER_SIZE] = obj;
        ++produced;
        return true;
    }

    public T pop() {
        if (produced - consumed == 0) return null;
        T obj = buffer[consumed % BUFFER_SIZE];
        ++consumed;
        return obj;
    }
}

