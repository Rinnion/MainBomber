package com.me.Particles;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.minebomber.Settings;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 27.12.13
 * Time: 11:10
 * To change this template use File | Settings | File Templates.
 */
public  class ParticleManager {
    static final int pCount=100;
    static  ParticleManager[]pFire=new ParticleManager[pCount];

    ParticleEffect pEffect;
    boolean isFree=true;
    static Object syncObject=new Object();

    static public void Initialize()
    {


            pFire=new ParticleManager[pCount];
            for(int i=0;i<pCount;i++)
            {
                pFire[i]=new ParticleManager();
                pFire[i].pEffect=new ParticleEffect();
                pFire[i].pEffect.load(Gdx.files.internal(Settings.PARTICLE_001),Gdx.files.internal("data/particles"));

            }

    }

    private void  Start(float x,float y)
    {
        isFree=false;
        pEffect.setPosition(x,y);
        pEffect.start();



    }

    static  public void Fire(float x,float y)
    {
        synchronized (syncObject)
        {
            for(int i=0;i<pCount;i++)
            {
                if(pFire[i].isFree)
                {
                    pFire[i].Start(x,y);
                    break;
                }

            }
        }
    }

    static public void Draw(SpriteBatch batch,float delta)
    {
        for(int i=0;i<pCount;i++)
        {
           if(!pFire[i].isFree)
           {

              pFire[i].pEffect.draw(batch,delta);
               if(pFire[i].pEffect.isComplete() )
               {
                   //      pFire[i].pEffect.reset();
                   //    pFire[i].pEffect.
                   pFire[i].isFree=true;
                   continue;
               }

           }
        }

    }


    public static void dispose() {
        for(int i=0;i<pCount;i++)
        {
                pFire[i].pEffect.dispose();

        }


    }
}
