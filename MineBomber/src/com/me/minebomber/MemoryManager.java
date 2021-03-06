package com.me.minebomber;

import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.bomb.*;
import com.me.bomb.activator.DestinationActivator;
import com.me.bomb.activator.RandomTimeActivator;
import com.me.bomb.activator.TimeActivator;
import com.me.bomb.behavior.*;
import com.me.controlers.actions.ActivateBombAction;
import com.me.controlers.actions.PutBombAction;
import com.me.controlers.treasure.BigChestTreasure;
import com.me.controlers.treasure.SmallChestTreasure;

import java.util.HashMap;

/**
 * Created by tretyakov on 06.10.2014.
 */
public class MemoryManager {
    private static final int DEFAULT_COUNT = 300;
    private static HashMap<Class<?>, RecyclableArray<?>> hm;

    public static <T extends RecyclableObject> void RegisterClass(Class<T> cls) {
        RegisterClass(cls, DEFAULT_COUNT);
    }

    public static <T extends RecyclableObject> void RegisterClass(Class<T> cls, int count) {
        hm.put(cls, new RecyclableArray(cls, count));
    }

    public static void Done() {
        hm.clear();
        hm = null;
    }

    public static void Initialize()
    {
        hm = new HashMap<>();
        RegisterClass(ActivateBombAction.class, 100);
        RegisterClass(PutBombAction.class, 100);

        RegisterClass(RandomBomb.class, 100);
        RegisterClass(RemoteBomb.class, 100);
        RegisterClass(Dynamite.class, 100);
        RegisterClass(FastFilledBomb.class, 100);
        RegisterClass(FilledBomb.class, 100);
        RegisterClass(PunchTeraStone.class, 100);

        RegisterClass(BigChestTreasure.class, 100);
        RegisterClass(SmallChestTreasure.class, 100);

        RegisterClass(DestinationActivator.class, 100);
        RegisterClass(RandomTimeActivator.class, 100);
        RegisterClass(TimeActivator.class, 100);

        RegisterClass(CircleExplosion.class, 100);
        RegisterClass(RandomCircleExplosion.class, 100);
        RegisterClass(FastTeramorf.class, 10);
        RegisterClass(JumpBehavior.class, 100);
        RegisterClass(PunchTera.class, 10);
        RegisterClass(Teramorf.class, 10);

        RegisterClass(AnimatedSpriteAnimator.class, 1000);

    }

    public static <T> T take(Class<T> cls){
        RecyclableArray<?> ra = hm.get(cls);
        return (T)ra.take();
    }

    public static <T extends RecyclableObject> void recycle(RecyclableObject object) {
        Class<?> aClass = object.getClass();
        RecyclableArray ra = hm.get(aClass);
        ra.recycle(object);
        //Log.d("recycle " + object + ". ra.length = " + ra.getSize());
    }
}

//public class MemoryManager  {
//
//    private static final int DEFAULT_COUNT = 300;
//    private static HashMap<Class<?>, RecyclableArray<?>> hm;
//    private static FactoryContainer factories;
//
//    public static <T extends RecyclableObject> void RegisterClass(Class<T> cls) {
//        RegisterClass(cls, DEFAULT_COUNT);
//    }
//
//    public static <T extends RecyclableObject> void RegisterClass(Class<T> cls, int count) {
//        boolean add = false;
//        Class<?>[] classes = cls.getClasses();
//        for (int i = 0; i < classes.length && !add; i++) {
//            Class<?>[] interfaces = classes[i].getInterfaces();
//            for (Class<?> anInterface : interfaces) {
//                if (anInterface.equals(RecyclableArray.Factory.class)) {
//                    try {
//                        if (!Modifier.isStatic(classes[i].getModifiers())) {
//                            throw new IllegalArgumentException(String.format("Class '%s' must have nested static class implementing of '%s'", cls.getCanonicalName(), RecyclableArray.Factory.class));
//                        }
//                        hm.put(cls, new RecyclableArray((RecyclableArray.Factory) classes[i].newInstance(), count));
//                        add = true;
//                        break;
//                    } catch (InstantiationException | IllegalAccessException e) {
//                        throw new IllegalArgumentException(String.format("Couldn't instantiate '%s'", classes[i]), e);
//                    }
//                }
//            }
//        }
//        if (!add)
//            throw new IllegalArgumentException(String.format("Class '%s' must have nested staticclass implementing of '%s'", cls.getCanonicalName(), RecyclableArray.Factory.class));
//    }
//
//    public static void Load()
//    {
//        hm = new HashMap<>();
//        RegisterClass(ActivateBombAction.class, 100);
//        RegisterClass(PutBombAction.class, 100);
//
//        RegisterClass(RandomBomb.class, 100);
//        RegisterClass(DestBomb.class, 100);
//        RegisterClass(Dynamite.class, 100);
//        RegisterClass(FastFilledBomb.class, 100);
//        RegisterClass(FilledBomb.class, 100);
//        RegisterClass(PunchTeraStone.class, 100);
//
//        RegisterClass(BigChestTreasure.class, 100);
//        RegisterClass(SmallChestTreasure.class, 100);
//
//        RegisterClass(DestinationActivator.class, 100);
//        RegisterClass(RandomTimeActivator.class, 100);
//        RegisterClass(TimeActivator.class, 100);
//
//        RegisterClass(CircleExplosion.class, 100);
//        RegisterClass(RandomCircleExplosion.class, 100);
//        RegisterClass(FastTeramorf.class, 10);
//        RegisterClass(JumpBehavior.class, 100);
//        RegisterClass(PunchTera.class, 10);
//        RegisterClass(Teramorf.class, 10);
//
//        RegisterClass(AnimatedSpriteAnimator.class, 1000);
//
//        //factories = new FactoryContainer();
//        //factories.put(ActivateBombAction.Factory.class, new ActivateBombAction.Factory(100));
//        //factories.put(PutBombAction.Factory.class, new PutBombAction.Factory(100));
//    }
//
//    public static <T> T take(Class<T> cls){
//        RecyclableArray<?> ra = hm.get(cls);
//        Log.d("take " + cls.getCanonicalName() + ". ra.length = " + (ra.getSize() - 1));
//        return (T)ra.take();
//    }
//
//    public static <T extends RecyclableObject> void recycle(RecyclableObject object) {
//        Class<?> aClass = object.getClass();
//        RecyclableArray ra = hm.get(aClass);
//        ra.recycle(object);
//        Log.d("recycle " + object + ". ra.length = " + ra.getSize());
//    }
//
//    public static <T> T getFactory(Class<T> cls){
//        return factories.getFactory(cls);
//    }
//}
