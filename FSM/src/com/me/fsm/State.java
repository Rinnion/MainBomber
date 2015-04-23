package com.me.fsm;

public interface State {
    /**
     * Method that defines action that occurs whenever this state is entered.
     * Any exceptions thrown by this method are ignored.
     */
    void preAction(FSM fsm);

    /**
     * Method that defines action that occurs whenever this state is exited.
     * Any exceptions thrown by this method are ignored.
     */
    void postAction(FSM fsm);
}
