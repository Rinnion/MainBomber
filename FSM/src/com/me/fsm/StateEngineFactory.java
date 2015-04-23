package com.me.fsm;

public class StateEngineFactory {
    private StateEngineFactory() {
    }

    public static <T extends StateImpl> StateEngine<T> create() {
        return new StateEngineImpl<T>();
    }
}
