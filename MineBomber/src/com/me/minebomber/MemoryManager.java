package com.me.minebomber;

import com.me.Bombs.AbstractBomb;
import com.me.Bombs.Behavior.JumpBehavior;
import com.me.Bombs.DestBomb;
import com.me.Bombs.Dynamite;
import com.me.Bombs.RandomBomb;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.controlers.actions.ActivateBombAction;
import com.me.controlers.actions.PutBombAction;
import sun.org.mozilla.javascript.internal.ast.Jump;

import java.util.HashMap;

/**
 * Created by tretyakov on 06.10.2014.
 */
public class MemoryManager {

    private static RecyclableArray<PutBombAction> PutBombActionArray;
    private static RecyclableArray<ActivateBombAction> ActivateBombActionArray;
    private static HashMap<Class<?>, RecyclableArray<?>> hm;

    public static void Initialize()
    {
        ActivateBombActionArray = new RecyclableArray<>(new ActivateBombAction.Factory());
        PutBombActionArray = new RecyclableArray<>(new PutBombAction.Factory());

        hm = new HashMap<>();
        hm.put(ActivateBombAction.class, ActivateBombActionArray);
        hm.put(PutBombAction.class, PutBombActionArray);


    }

    public static <T> T take(Class<T> cls){
        RecyclableArray<?> ra = hm.get(cls);
        return (T)ra.take();
    }

    public static <T extends RecyclableObject> void recycle(Class<T> cls, T object){
        Class<?> aClass = object.getClass();
        RecyclableArray ra = hm.get(cls);
        ra.recycle(object);
    }

}
