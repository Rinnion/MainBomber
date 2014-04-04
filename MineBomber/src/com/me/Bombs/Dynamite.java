package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.Map.MapManager;
import com.me.Particles.ParticleManager;
import com.me.Players.IPlayer;
import com.me.Utility.DelayTimer;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;


/**
 * Created by alekseev on 27.03.2014.
 */
public class Dynamite implements IBomb {

    float dmgMax;
    float dmgMin;
    int detonateTime;
    boolean detonate;
    boolean active;
    boolean visible;

    //Sprite sprite;
    BombProperty property;
    DelayTimer timer;

    Animation animSprite;

    Sprite sprite;


    float pX;
    float pY;
    private float elapsedTime = 0;

    private boolean destroyed=false;

    IBombCallback callBack;

    public  Dynamite(BombProperty property,Vector2 position,IBombCallback callback)
    {
       this.property=new BombProperty(property);
        detonate=false;
        detonateTime=property.activationTime;
        active=property.active;
        dmgMax=property.dmgMax;
        dmgMin=property.dmgMin;
        this.callBack=callback;

        TextureAtlas dynamiteTex= AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);
        //dynamiteTex.createSprites("dyn");

        Array<TextureAtlas.AtlasRegion> region= dynamiteTex.findRegions ("dyn");

        for(TextureAtlas.AtlasRegion tmpRegion : region)
        {
            tmpRegion.flip(false,true);

        }

        animSprite=new Animation(0.12f,region);

        sprite=new Sprite(region.get(0).getTexture());
        //sprite.setSize(region.get(0).getRegionWidth(),region.get(0).getRegionHeight());
        sprite.setSize(8,8);
        //sprite.flip(false,true);

        //animSprite

      //  sprite=new Sprite(AssetLoader.GetTexture(Settings.TEX_MAN) );
        //sprite.flip(false,true);
        //sprite.setPosition(position.x,position.y);
        //animSprite.set
        timer=new DelayTimer(detonateTime,active);
        visible=true;
        pX=position.x;
        pY=position.y;
        sprite.setPosition(pX,pY);
    }

    private void   doDetonate()
    {
        ParticleManager.Fire(pX,pY);

        //MapManager.doCircleDamage((int)pX,(int)pY,20f,10f,60,this);
        MapManager.doBombDamage((int)pX,(int) pY,this);
        destroyed=true;
        raiseEvent_CanBeRemove();

    }

    private void raiseEvent_CanBeRemove()
    {
        if(callBack!=null)
            callBack.CanBeRemove(this);
    }




    @Override
    public void Render(Batch batch) {
        if(destroyed)
            return;

        if((timer.CheckTimeOut()) ||(detonate))
        {
              visible=false;
            doDetonate();
        }

        if(visible)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            sprite.setRegion(animSprite.getKeyFrame(elapsedTime, true));

            //batch.draw(animSprite.getKeyFrame(elapsedTime, true), pX, pY);
            sprite.draw(batch);

        }
          //sprite.draw(sb);
    }

    @Override
    public void Activate() {
         active=true;
    }

    @Override
    public void ImmediatelyDetonate() {
        detonate=true;
        active=true;
    }

    @Override
    public void ImmediatelyDetonate(long activationTime) {

    }

    @Override
    public IPlayer GetOwner() {
        return property.owner;
    }

    @Override
    public long GetActivationTime() {
        return 0;
    }

    @Override
    public BombProperty GetProperty() {
        return property;
    }
    @Override
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public float getW() {
        return sprite.getWidth();
    }

    @Override
    public float getH() {
        return sprite.getHeight();
    }

}
