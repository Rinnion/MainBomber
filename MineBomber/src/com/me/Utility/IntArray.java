package com.me.Utility;

/**
 * Created by alekseev on 07.04.2014.
 */
public class IntArray {

    int []arrayList;//=new T[2000];
    int itemCount;
    public  IntArray(int capacity)
    {
        arrayList= new int[capacity];
        itemCount=0;
    }

    public void add(int item)
    {
        arrayList[itemCount]=item;
        itemCount++;
    }

    public int size()
    {
        return itemCount;
    }

    public int get(int index)
    {
        return arrayList[index];
    }

}
