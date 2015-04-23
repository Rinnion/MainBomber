package com.me.fsm;

public interface FSM<T extends State> {
    /**
     * Get the current state of this FSM.
     */
    public T getState();

    /**
     * Perform the action and transition to the next state based
     * on the current state of the FSM and the input.
     */
    public void doIt(Input in);
}
