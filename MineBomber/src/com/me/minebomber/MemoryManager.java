package com.me.minebomber;

import com.me.Bombs.Activator.DestinationActivator;
import com.me.Bombs.Activator.RandomTimeActivator;
import com.me.Bombs.Activator.TimeActivator;
import com.me.Bombs.Behavior.*;
import com.me.Bombs.*;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.controlers.actions.ActivateBombAction;
import com.me.controlers.actions.PutBombAction;
import com.me.controlers.treasure.BigChestTreasure;
import com.me.controlers.treasure.SmallChestTreasure;

import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by tretyakov on 06.10.2014.
 */
public class MemoryManager {

    private static HashMap<Class<?>, RecyclableArray<?>> hm;

    public static <T extends RecyclableObject> void RegisterClass(Class<T> cls, int count) {
        boolean add = false;
        Class<?>[] classes = cls.getClasses();
        for (int i = 0; i < classes.length && !add; i++) {
            Class<?>[] interfaces = classes[i].getInterfaces();
            for (Class<?> anInterface : interfaces) {
                if (anInterface.equals(RecyclableArray.Factory.class)) {
                    try {
                        if (!Modifier.isStatic(classes[i].getModifiers())) {
                            throw new IllegalArgumentException(String.format("Class '%s' must have nested static class implementing of '%s'", cls.getCanonicalName(), RecyclableArray.Factory.class));
                        }
                        hm.put(cls, new RecyclableArray((RecyclableArray.Factory) classes[i].newInstance(), count));
                        add = true;
                        break;
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new IllegalArgumentException(String.format("Couldn't instantiate '%s'", classes[i]), e);
                    }
                }
            }
        }
        if (!add)
            throw new IllegalArgumentException(String.format("Class '%s' must have nested staticclass implementing of '%s'", cls.getCanonicalName(), RecyclableArray.Factory.class));
    }

    public static void Initialize()
    {
        hm = new HashMap<>();
        RegisterClass(ActivateBombAction.class, 100);
        RegisterClass(PutBombAction.class, 100);

        RegisterClass(RandomBomb.class, 1000);
        RegisterClass(DestBomb.class, 1000);
        RegisterClass(Dynamite.class, 1000);
        RegisterClass(FastFilledBomb.class, 1000);
        RegisterClass(FilledBomb.class, 1000);
        RegisterClass(PunchTeraStone.class, 1000);

        RegisterClass(BigChestTreasure.class, 1000);
        RegisterClass(SmallChestTreasure.class, 1000);

        RegisterClass(DestinationActivator.class, 1000);
        RegisterClass(RandomTimeActivator.class, 1000);
        RegisterClass(TimeActivator.class, 1000);

        RegisterClass(CircleExplosion.class, 1000);
        RegisterClass(RandomCircleExplosion.class, 1000);
        RegisterClass(FastTeramorf.class, 1000);
        RegisterClass(JumpBehavior.class, 1000);
        RegisterClass(PunchTera.class, 1000);
        RegisterClass(Teramorf.class, 1000);

    }

    public static <T> T take(Class<T> cls){
        RecyclableArray<?> ra = hm.get(cls);
        return (T)ra.take();
    }

    public static <T extends RecyclableObject> void recycle(RecyclableObject object) {
        Class<?> aClass = object.getClass();
        RecyclableArray ra = hm.get(aClass);
        ra.recycle(object);
    }

}
