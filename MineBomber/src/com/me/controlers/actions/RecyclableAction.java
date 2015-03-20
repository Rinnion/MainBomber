package com.me.controlers.actions;

import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.controlers.ActionController;

public abstract class RecyclableAction extends RecyclableObject implements ActionController.IGameAction {
    public RecyclableAction(RecyclableArray array) {
        super(array);
    }
}
