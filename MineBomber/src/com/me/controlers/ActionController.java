package com.me.controlers;

import com.me.Utility.IRecyclable;

import java.util.ArrayList;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class ActionController {

    public static final int DEF_MAX_ACTIONS_BUFFER = 10000;
    private static final ArrayList<IGameAction> actionsBuffer = new ArrayList<IGameAction>(DEF_MAX_ACTIONS_BUFFER);
    private static final ArrayList<IGameAction> actionsQuery = new ArrayList<IGameAction>(DEF_MAX_ACTIONS_BUFFER);
    private static final String MOD_SRC = "ActionController.";

    public static void Calculate(long time) {
        for (IGameAction action : actionsBuffer) {
            action.Calculate(time);
            ((IRecyclable) action).recycle();
        }
        actionsBuffer.clear();
        synchronized (actionsQuery) {
            for (IGameAction action : actionsQuery) {
                actionsBuffer.add(action);
            }
            actionsQuery.clear();
        }
    }

    public static void Add(IGameAction action) {
        synchronized (actionsQuery) {
            actionsQuery.add(action);
        }
    }

    public interface IGameAction {
        void Calculate(long time);
    }
}

