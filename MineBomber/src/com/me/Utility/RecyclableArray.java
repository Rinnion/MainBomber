package com.me.Utility;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RecyclableArray<E extends RecyclableObject> {

    static Logger logger = LoggerFactory.getLogger(RecyclableArray.class);

    private static int MAX_LENGTH = 1000;
    private final int mCount;
    private final E[] mFreeStack;
    private final Constructor<E> ctor;
    private final Class<E> mClass;
    private int mCountFree;

    public RecyclableArray(Class<E> cls) {
        this(cls, MAX_LENGTH);
    }

    @SuppressWarnings("unchecked")
    public RecyclableArray(Class<E> cls, int count) {
        try {
            mClass = cls;
            mCount = count;
            mCountFree = 0;
            mFreeStack = (E[]) Array.newInstance(cls, count);
            ctor = cls.getDeclaredConstructor(RecyclableArray.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Couldn't create array", e);
        }
    }

    public void recycle(E object){
        if (object == null) throw new IllegalArgumentException("Couldn't not recycle null to array");
        if (!object.getClass().equals(mClass)) throw new IllegalArgumentException("Wrong class type");
        if (mCountFree < mCount) {
            mFreeStack[mCountFree++] = object;
            logger.debug("recycle " + mClass.getCanonicalName() + ". ra.length = " + mCountFree);
        }
    }

    public E take(){
        E result;
        if (mCountFree == 0) {
            try {
                result = ctor.newInstance(this);
                logger.debug("instantiate new " + mClass.getCanonicalName() + ". ra.length = " + mCountFree);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(String.format("Couldn't instantiate '%s'", mClass), e);
            }
        } else {
            result = mFreeStack[--mCountFree];
            logger.debug("take " + mClass.getCanonicalName() + ". ra.length = " + mCountFree);
        }
        return result;
    }
}

//public class RecyclableArray<E extends RecyclableObject> {
//
//    private static int MAX_LENGTH = 1000;
//    private final Class<? extends RecyclableObject> storageClass;
//
//    Carrier header = new Carrier(null, null, null);
//    int size = 0;
//
//    public RecyclableArray(Factory<E> factory) {
//        this(factory, MAX_LENGTH);
//    }
//
//    public RecyclableArray(Factory<E> factory, int count) {
//
//        for (int i = 0; i < count; i++) {
//            header = new Carrier(factory.newItem(), null, header);
//            header.next.previous = header;
//            size++;
//        }
//
//        this.storageClass = header.element.getClass();
//    }
//
//    public void recycle(E object){
//        if (size == MAX_LENGTH) throw new ArrayStoreException("Size is greater then available");
//        if (object == null) throw new IllegalArgumentException("Couldn't not recycle null to array");
//        header = header.previous;
//        header.element = object;
//        size++;
//    }
//
//    public int getSize(){
//        return size;
//    }
//
//    public E take(){
//        if (size == 0) throw new NoSuchElementException(storageClass.getCanonicalName());
//        //TODO: увеличение массива по необходимости
//        header = header.next;
//        size--;
//        return header.previous.element;
//    }
//
//    public interface Factory<E> {
//        E newItem();
//    }
//
//    public class Carrier{
//        public Carrier next;
//        public Carrier previous;
//
//        public E element;
//
//        public Carrier(E element, Carrier previous, Carrier next){
//            this.element = element;
//            this.next = next;
//            this.previous = previous;
//        }
//    }
//
//    /*
//    public static boolean SmokeTest(){
//        try {
//            RecyclableArray<PutBombAction> rl = new RecyclableArray<PutBombAction>(new PutBombAction.FactoryMethos(), 3);
//
//
//
//            PutBombAction take1 = rl.take();
//            PutBombAction take2 = rl.take();
//            take1.update(null, 1000, null);
//            take2.update(null, 2000, null);
//            rl.recycle(take1);
//            rl.recycle(take2);
//
//            try {
//                rl.recycle(take1);
//            } catch (ArrayStoreException ase) {
//
//            }
//
//            try {
//                rl.recycle(null);
//            } catch (IllegalArgumentException ase) {
//
//            }
//
//            try {
//                rl.take();
//                rl.take();
//                rl.take();
//            } catch (NoSuchElementException nsee) {
//
//            }
//        }catch(Exception ex){
//            return false;
//        }
//        return true;
//    }
//*/
//}
