package com.me.fsm;

public interface StateEngine<T extends State> {
    /**
     * Add a new transition (old,in,guard,act,new) to the state engine.
     * Multiple calls to add with the same old and in are permitted,
     * in which case only a transition in which the guard evaluates to
     * true will be taken.  If no such transition is enabled, a default
     * will be taken.  If more than one transition is enabled, one will
     * be chosen arbitrarily.
     * This method can only be called before done().  An attempt to
     * call it after done() results in an IllegalStateException.
     */
    public StateEngine add(T oldState, Input input, Guard guard,
                           Action action, T newState) throws IllegalStateException;

    /**
     * Add a transition with a guard that always evaluates to true.
     */
    public StateEngine add(T oldState, Input input,
                           Action action, T newState) throws IllegalStateException;

    /**
     * Set the default transition and action for a state.
     * This transition will be used if no more specific transition was
     * defined for the actual input.  Repeated calls to this method
     * simply change the default.
     * This method can only be called before done().  An attempt to
     * call it after done() results in an IllegalStateException.
     */
    public StateEngine setDefault(T oldState, Action action, T newState)
            throws IllegalStateException;

    /**
     * Equivalent to setDefault( oldState, act, newState ) where act is an
     * action that does nothing.
     */
    public StateEngine setDefault(T oldState, T newState)
            throws IllegalStateException;

    /**
     * Euaivalent to setDefault( oldState, oldState )
     */
    public StateEngine setDefault(T oldState)
            throws IllegalStateException;

    /**
     * Set the default action used in this state engine.  This is the
     * action that is called whenever there is no applicable transition.
     * Normally this would simply flag an error.  This method can only
     * be called before done().  An attempt to
     * call it after done() results in an IllegalStateException.
     */
    public void setDefaultAction(Action act) throws IllegalStateException;

    /**
     * Called after all transitions have been added to the state engine.
     * This provides an opportunity for the implementation to optimize
     * its representation before the state engine is used.  This method
     * may only be called once.  An attempt to call it more than once
     * results in an IllegalStateException.
     */
    public void done() throws IllegalStateException;

    /**
     * Create an instance of a FSM that uses this state engine.
     * The initial state of the FSM will be the stateState specified
     * here.  This method can only be called after done().  An attempt
     * to call it before done results in an IllegalStateException.
     */
    public FSM<T> makeFSM(T startState) throws IllegalStateException;
}

