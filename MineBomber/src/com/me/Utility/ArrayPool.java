package com.me.Utility;


/**
 * Created by alekseev on 08.04.2014.
 */
public class ArrayPool<T> {
    private class ArrayItem<T>
    {
        boolean free;
        T item;

        public ArrayItem()
        {
            free=true;
        }
    }

    private ArrayItem []items;
    private int count;

    public ArrayPool(int capacity)
    {
       items=new ArrayItem[capacity];
        count=0;

    }


    public boolean put(T item)
    {
        //int tmpCount=0;
        boolean successAdd=false;
        for(int i=0;i<items.length;i++)
        {
            if(items[i]==null)
                items[i]=new ArrayItem();


            if(items[i].free) {
                items[i].item = item;
                successAdd=true;
                count++;
            }

        }
     return successAdd;
    }

    public T get(int index) {
        if (count == 0)
            return null;

        int tmpCount = 0;
        Object retObj = null;

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null)
                break;


            if (!items[i].free)
                tmpCount++;


            if (tmpCount > count)
                return null;

            int minusCount = tmpCount - 1;

            if (minusCount == index)
                return (T) items[minusCount].item;

        }
        return null;
    }

    public void remove(T item,boolean useEqual)
    {
        ArrayItem tmpItem;
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null)
                break;

            //tmpItem=item[i].

            tmpItem=items[i];

            if(useEqual)
            {
                if(tmpItem.item.equals(item))
                {
                    tmpItem.free=true;
                    count--;
                    return;
                }
            }
            else
            {
                if(tmpItem.item==item)
                {
                    tmpItem.free=true;
                    count--;
                    return;
                }
            }

        }

    }

}
