package com.me.Particles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.minebomber.Settings;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 27.12.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public  class ParticleManager {
    static final int pCount=200;
    static  ParticleManager[]pFire=new ParticleManager[pCount];

    ParticleEffect pEffect;
    boolean isFree=true;
    static Object syncObject=new Object();
    private IParticleCallback mCallBack;


    static public void Initialize()
    {



            pFire=new ParticleManager[pCount];
            for(int i=0;i<pCount;i++)
            {
                pFire[i]=new ParticleManager();
                pFire[i].pEffect=new ParticleEffect();

                pFire[i].pEffect.load(Gdx.files.internal(Settings.PARTICLE_001),Gdx.files.internal("data/particles"));
                pFire[i].mCallBack=null;
            }

    }

    private void  Start(float x,float y,IParticleCallback callback)
    {
        isFree=false;
        pEffect.setPosition(x,y);

        pEffect.start();


        mCallBack=callback;


    }
    static  public void Fire(float x,float y,float rad )
    {
        Fire(x,y,rad,null);
    }

    static  public void Fire(float x,float y,float rad,IParticleCallback callback)
    {

        synchronized (syncObject)
        {
            boolean found=false;
            for(int i=0;i<pCount;i++)
            {
                if(pFire[i].isFree)
                {
                    changeSize(i,rad);
                    pFire[i].Start(x,y,callback);

                    found=true;
                    break;
                }
            }
            if((!found)&&(callback!=null))
                callback.AnimationEnd();
        }
    }


    static public void changeSize(int index,float velocity)
    {

       for(ParticleEmitter tmpEmmite:pFire[index].pEffect.getEmitters())
       {
           //tmpEmmite.getScale().setLow(0,10);   //setL (10,200);
           //tmpEmmite.getScale().setHigh(10,10);
          tmpEmmite.getVelocity().setLow(velocity,velocity);
           tmpEmmite.getVelocity().setHigh(velocity,velocity);


       }

    }


    static public void Draw(SpriteBatch batch,float delta)
    {
        for(int i=0;i<pCount;i++)
        {
           if(!pFire[i].isFree)
           {



               pFire[i].pEffect.draw(batch, delta);


               //float percent=pFire[i].pEffect.getEmitters().get(0).getPercentComplete();

               if(pFire[i].pEffect.isComplete())
               {

                   if(pFire[i].mCallBack!=null) {


                       pFire[i].mCallBack.AnimationEnd();
                   }
                   pFire[i].isFree=true;
                   continue;
               }
            }
        }
    }


    public static void dispose() {
        for(int i=0;i<pCount;i++)
            pFire[i].pEffect.dispose();

    }
}
