package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
public class DestBomb extends AbsBomb {


    float dmgMax;
    float dmgMin;
    int detonateTime;
    boolean detonate;
    boolean active;
    BombProperty property;
    public final float pX;
    public final float pY;

    IBombCallback callBack;


    private DelayTimer mDetonateDelay=new DelayTimer(100,false);

    public DestBomb(BombProperty property, Vector2 pos, IBombCallback callback)
    {
        super(property, new Vector2(pos.x/MapManager.rowW, pos.y/MapManager.rowH));

        this.property=new BombProperty(property);
        property.active=false;
        detonate=false;
        detonateTime=property.activationTime;
        this.active=property.active;
        dmgMax=property.dmgMax;
        dmgMin=property.dmgMin;
        this.callBack=callback;
        //lockObj=new ReentrantLock();
        //dynamiteTex.createSprites("dyn");

        pX=pos.x;
        pY=pos.y;
        sprite.setPosition(pX,pY);

        //new set
    }

    private void   doDetonate()
    {
        if (BombPlaser.CanDetonate(this)) {

            BombPlaser.canDetonate = false;
            ParticleManager.Fire(pX, pY);

            //MapManager.doCircleDamage((int) pX, (int) pY, 20f, 10f, 60, this);
            MapManager.doBombDamage((int)pX,(int) pY,this);

            visible = false;
            destroyed = true;

            raiseEvent_CanBeRemove();
            //lockObj.unlock();
        }
    }

    private void raiseEvent_CanBeRemove()
    {
        if(callBack!=null)
            callBack.CanBeRemove(this);
    }


    @Override
    public void Activate() {
         active=true;
    }

    @Override
    public void ImmediatelyDetonate() {
        detonate=true;
        active=true;
        //Gdx.graphics.get
    }

    @Override
    public void ImmediatelyDetonate(long activationTime) {
        this.ActivationTime = activationTime;
        ImmediatelyDetonate();
    }

    @Override
    public IPlayer GetOwner() {
        return property.owner;
    }

    @Override
    public long GetActivationTime() {
        return ActivationTime;
    }

    @Override
    public void Refresh() {
        if(destroyed)
            return;

        if(active)
            doDetonate();

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
