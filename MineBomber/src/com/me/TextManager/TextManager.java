package com.me.TextManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.logger.Log;

/**
 * Created by alekseev on 15.04.2014.
 */
public class TextManager {

   private static final int PoolSize=200;
   private static int curSize=0;

   private static TextPool []pool;

   private static Object objSync=new Object();


    public static void Initialize()
    {
        pool=new TextPool[PoolSize];

        for(int i=0;i<PoolSize;i++)
        {
            pool[i]=new TextPool();
            pool[i].isFree=true;
        }

    }


    private static TextPool getFree()
    {
        synchronized (objSync)
        {
            for(int i=0;i<PoolSize;i++)
            {
               if(pool[i].isFree)
               {


                   if(pool[i].text==null)
                       pool[i].text=new TextZoom();
                   pool[i].isFree=false;
                   curSize++;
                   return pool[i];
               }
            }
            return null;

        }
    }

    public static  void free(TextPool item)
    {
        synchronized (objSync)
        {
          item.isFree=true;
            curSize--;
        }

    }

    public static void Add(String text,Color color,float x,float y)
    {
         TextPool textPool=getFree();
         if(textPool==null)
         {
             Log.e("Error TextManager can't get free TextOut");
             return;
         }

        IText outText=textPool.text;

        outText.SetColor(color);
        outText.SetText(text);
        outText.SetPosition(x,y);
    }


    public static void Draw(SpriteBatch sb)
    {
        int actual=0;
        synchronized (objSync)
        {
            int size=curSize;

            for(int i=0;i<PoolSize;i++)
            {
                if(!pool[i].isFree) {
                    if(!pool[i].text.Draw(sb))
                     free(pool[i]);

                    actual++;

                    //if(pool[i].text.isFree())
                      //  free(pool[i]);

                    if (actual == size)
                        return;
                }
            }


        }




    }




}
