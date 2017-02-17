package com.me.controlers;

import com.me.Utility.IRecyclable;
import com.me.Utility.RollingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tretyakov on 12.05.2014.
 */
public class ActionController {

    static Logger logger = LoggerFactory.getLogger(ActionController.class);

    private final RollingQueue<IGameAction> actionsQuery = new RollingQueue<>(IGameAction.class);

    public void logic(long frame) {
        IGameAction action = actionsQuery.pop();
        while (action != null) {
            action.logic(frame);
            ((IRecyclable) action).recycle();
            action = actionsQuery.pop();
        }
    }

    public void Add(IGameAction action) {
        actionsQuery.push(action);
    }

    public interface IGameAction {
        void logic(long time);
    }
}

