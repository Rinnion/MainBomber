package com.me.Utility;


import java.util.NoSuchElementException;

/**
 * Created by tretyakov on 03.10.2014.
 */
public class RecyclableArray<E extends RecyclableObject> {

    private static int MAX_LENGTH = 1000;
    private final Class<? extends RecyclableObject> storageClass;

    Carrier header = new Carrier(null, null, null);
    int size = 0;

    public RecyclableArray(Factory<E> factory) {
        this(factory, MAX_LENGTH);
    }

    public RecyclableArray(Factory<E> factory, int count) {

        for (int i = 0; i < count; i++) {
            header = new Carrier(factory.newItem(), null, header);
            header.next.previous = header;
            size++;
        }

        this.storageClass = header.element.getClass();
    }

    public void recycle(E object){
        if (size == MAX_LENGTH) throw new ArrayStoreException("Size is greater then available");
        if (object == null) throw new IllegalArgumentException("Couldn't not recycle null to array");
        header = header.previous;
        header.element = object;
        size++;
    }

    public E take(){
        if (size == 0) throw new NoSuchElementException(storageClass.getCanonicalName());
        header = header.next;
        size--;
        return header.previous.element;
    }

    public interface Factory<E> {
        E newItem();
    }

    public class Carrier{
        public Carrier next;
        public Carrier previous;

        public E element;

        public Carrier(E element, Carrier previous, Carrier next){
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    /*
    public static boolean SmokeTest(){
        try {
            RecyclableArray<PutBombAction> rl = new RecyclableArray<PutBombAction>(new PutBombAction.Factory(), 3);



            PutBombAction take1 = rl.take();
            PutBombAction take2 = rl.take();
            take1.update(null, 1000, null);
            take2.update(null, 2000, null);
            rl.recycle(take1);
            rl.recycle(take2);

            try {
                rl.recycle(take1);
            } catch (ArrayStoreException ase) {

            }

            try {
                rl.recycle(null);
            } catch (IllegalArgumentException ase) {

            }

            try {
                rl.take();
                rl.take();
                rl.take();
            } catch (NoSuchElementException nsee) {

            }
        }catch(Exception ex){
            return false;
        }
        return true;
    }
*/
}
