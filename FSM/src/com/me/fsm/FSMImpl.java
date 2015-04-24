package com.me.fsm;

public class FSMImpl implements FSM {
    private boolean debug;
    private State state;
    private StateEngineImpl stateEngine;

    /**
     * Create an instance of an FSM using the StateEngine
     * in a particular start state.
     */
    public FSMImpl(StateEngine se, State startState) {
        this(se, startState, false);
    }

    public FSMImpl(StateEngine se, State startState, boolean debug) {
        state = startState;
        stateEngine = (StateEngineImpl) se;
        this.debug = debug;
    }

    /**
     * Return the current state.
     */
    public State getState() {
        return state;
    }

    /**
     * Perform the transition for the given input in the current state.  This proceeds as follows:
     * <p>Let S be the current state of the FSM.
     * If there are guarded actions for S with input in, evaluate their guards successively until
     * all have been evaluted, or one returns a non-DISABLED Result.
     * <ol>
     * <li>If a DEFERED result is returned, retry the input
     * <li>If a ENABLED result is returned, the action for the guarded action
     * is the current action
     * <li>Otherwise there is no enabled action.  If S has a default action and next state, use them; otherwise
     * use the state engine default action (the next state is always the current state).
     * </ol>
     * After the action is available, the transition proceeds as follows:
     * <ol>
     * <li>If the next state is not the current state, execute the current state postAction method.
     * <li>Execute the action.
     * <li>If the next state is not the current state, execute the next state preAction method.
     * <li>Set the current state to the next state.
     * </ol>
     */
    public void doIt(Input in) {
        stateEngine.doIt(this, in, debug);
    }

    // Methods for use only by StateEngineImpl

    public void internalSetState(State nextState) {
        if (debug) {
            ORBUtility.dprint(this, "Calling internalSetState with nextState = " +
                    nextState);
        }

        state = nextState;

        if (debug) {
            ORBUtility.dprint(this, "Exiting internalSetState with state = " +
                    state);
        }
    }
}