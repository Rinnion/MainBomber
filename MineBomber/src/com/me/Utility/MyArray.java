package com.me.Utility;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

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

    public boolean remove(T object)
    {
        int i=-1;
        for(i=0;i<itemCount;i++)
        {
            if(arrayList[i].equals(object))
               break;
        }

        if(i==-1)
            return false;

        arrayList[i]=null;

        int newCount=itemCount-1;
        if(i+1==newCount)
        {
            itemCount=newCount;
            return true;

        }



        System.arraycopy(arrayList,i+1,arrayList,i,newCount);
        itemCount=newCount;
        return true;


    }

    public void clear()
    {
        for(int i=0;i<itemCount;i++)
        {
            arrayList[i]=null;
        }

        itemCount=0;
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
