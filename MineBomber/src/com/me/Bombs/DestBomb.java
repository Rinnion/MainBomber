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
public class DestBomb implements IBomb {

    float dmgMax;
    float dmgMin;
    int detonateTime;
    boolean detonate;
    boolean active;
    boolean visible;
    long activateTime;
    //Sprite sprite;
    BombProperty property;


    Animation animSprite;

    Sprite sprite;


    float pX;
    float pY;
    private float elapsedTime = 0;

    private boolean destroyed=false;

    IBombCallback callBack;


    private DelayTimer mDetonateDelay=new DelayTimer(100,false);

    public DestBomb(BombProperty property, Vector2 position, IBombCallback callback)
    {

        this.property=new BombProperty(property);
        property.active=false;
        detonate=false;
        detonateTime=property.activationTime;
        this.active=property.active;
        dmgMax=property.dmgMax;
        dmgMin=property.dmgMin;
        this.callBack=callback;
        //lockObj=new ReentrantLock();

        TextureAtlas dynamiteTex= AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);
        //dynamiteTex.createSprites("dyn");

        Array<TextureAtlas.AtlasRegion> region= dynamiteTex.findRegions ("dst_bomb");

        for(TextureAtlas.AtlasRegion tmpRegion : region)
        {
            tmpRegion.flip(false,true);

        }

        animSprite=new Animation(0.24f,region);

        sprite=new Sprite(region.get(0).getTexture());

        sprite.setSize(8,8);


        visible=true;
        pX=position.x;
        pY=position.y;
        sprite.setPosition(pX,pY);
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
    public void Render(Batch batch) {
        if(destroyed)
            return;



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
        //Gdx.graphics.get
    }

    @Override
    public void ImmediatelyDetonate(long activationTime) {
        this.activateTime =activationTime;
        ImmediatelyDetonate();
    }

    @Override
    public IPlayer GetOwner() {
        return property.owner;
    }

    @Override
    public long GetActivationTime() {
        return activateTime;
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
