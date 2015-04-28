package com.me.Utility;

public class IntegerQueue {

    static int BUFFER_SIZE = 10000;
    final int size;
    volatile int produced;
    volatile int consumed;
    int[] buffer;

    @SuppressWarnings("unchecked")
    public IntegerQueue(int size) {
        buffer = new int[size];
        this.size = size;
    }

    @SuppressWarnings("unchecked")
    public IntegerQueue() {
        this(BUFFER_SIZE);
    }


    public boolean push(int obj) {
        if (produced - consumed == BUFFER_SIZE) return false;
        buffer[produced % BUFFER_SIZE] = obj;
        ++produced;
        return true;
    }

    public int pop() {
        if (produced - consumed == 0) return 0;
        int obj = buffer[consumed % BUFFER_SIZE];
        ++consumed;
        return obj;
    }

    public void purge() {
        consumed = produced;
    }

}
