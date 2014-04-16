package com.me.Utility;

/**
 * Created by alekseev on 07.04.2014.
 */
public class FloatArray {
    float []arrayList;//=new T[2000];
    int itemCount;
    public  FloatArray(int capacity)
    {
        arrayList= new float[capacity];
        itemCount=0;
    }

    public void add(float item)
    {
        arrayList[itemCount]=item;
        itemCount++;
    }

    public int size()
    {
        return itemCount;
    }

    public float get(int index)
    {
        return arrayList[index];
    }

}
