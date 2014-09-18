package com.me.Utility;

import com.badlogic.gdx.utils.Array;

/**
 * Created by alekseev on 07.04.2014.
 */
public class MyArray<T> {

    T []arrayList;//=new T[2000];
    int itemCount;
    public  MyArray(int capacity)
    {
        arrayList= (T[])new Object[capacity];
        itemCount=0;
    }

    public void Add(T item)
    {
        arrayList[itemCount]=item;
        itemCount++;
    }

    public void clear(){
        itemCount = 0;
    }

    public int size()
    {
        return itemCount;
    }

    public T Get(int index)
    {
        return arrayList[index];
    }

    public T Get(T object)
    {
        //return arrayList[index];
        for(T item: arrayList)
        {
            if(item.equals(object))
                return item;
        }

        return null;
    }

}
