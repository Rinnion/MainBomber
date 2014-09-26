package com.me.controlers;

import java.util.ArrayList;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class ActionController {

    public static final int DEF_MAX_ACTIONS_BUFFER = 10000;
    private static final ArrayList<IGameAction> actionsBuffer = new ArrayList<IGameAction>(DEF_MAX_ACTIONS_BUFFER);
    private static final String MOD_SRC = "ActionController.";

    public static void Calculate(long time) {
        synchronized (actionsBuffer) {
            for (IGameAction action : actionsBuffer) {
                action.Calculate(time);
            }
            actionsBuffer.clear();
        }
    }

    public static void Add(IGameAction action) {
        synchronized (actionsBuffer) {
            actionsBuffer.add(action);
        }
    }

    public interface IGameAction {
        void Calculate(long time);
    }
}

