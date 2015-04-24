package com.me.controlers;

import com.me.Utility.IRecyclable;
import com.me.Utility.RollingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class ActionController {

    public static final int DEF_MAX_ACTIONS_BUFFER = 10000;
    //private static final ArrayList<IGameAction> actionsBuffer = new ArrayList<IGameAction>(DEF_MAX_ACTIONS_BUFFER);
    //private static final ArrayList<IGameAction> actionsQuery = new ArrayList<IGameAction>(DEF_MAX_ACTIONS_BUFFER);
    private static final RollingQueue<IGameAction> actionsQuery = new RollingQueue<>(IGameAction.class);
    private static final String MOD_SRC = "ActionController.";
    static Logger logger = LoggerFactory.getLogger(ActionController.class);

    public static void Calculate(long time) {
        IGameAction action = actionsQuery.pop();
        while (action != null) {
            action.Calculate(time);
            ((IRecyclable) action).recycle();
            action = actionsQuery.pop();
        }

/*
        synchronized (actionsQuery) {
            logger.trace("synchronized (actionsBuffer) ActionController.Calculate");
            for (IGameAction action : actionsQuery) {
                logger.debug("new action {}", action.toString());
                actionsBuffer.add(action);
            }
            actionsQuery.clear();
        }
*/
    }

    public static void Add(IGameAction action) {
        actionsQuery.push(action);
//        synchronized (actionsQuery) {
//            logger.trace("synchronized (actionsBuffer) ActionController.Add");
//            actionsQuery.add(action);
//        }
    }

    public interface IGameAction {
        void Calculate(long time);
    }
}

