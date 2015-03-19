package com.me.controlers;

import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;

public abstract class RecyclableAction extends RecyclableObject implements ActionController.IGameAction {
    public RecyclableAction(RecyclableArray array) {
        super(array);
    }
}
