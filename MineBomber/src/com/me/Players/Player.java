package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.BombPlaser;
import com.me.Bombs.BombProperty;
import com.me.Bombs.BombType;
import com.me.Bombs.IBomb;
import com.me.Graphics.ILifeProgressBar;
import com.me.Map.MapInfo;
import com.me.TileDamager.DamageController;
import com.me.Utility.DelayTimer;
import com.me.assetloader.AssetLoader;
import com.me.Map.MapManager;
import com.me.logger.Log;
import com.me.minebomber.Settings;

import java.util.ArrayList;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player  implements IPlayer, IPlayerControls {

   // @Override
   // public void onFix(Vector2 v) {
    //    this.v = v;
    //}

    float radiusDig=4.5f;
    float radiusGo=1.7f ;
    int digDmg=1;
    float playerSpd=40;

    float maxLife=100;
    float curLife=100;


    Vector2 v = new Vector2(0.7f,0.7f);

    private LifeProgressBar mLifeProgressBar;

    boolean mDie=false;
    private String mName;

    @Override
    public void Render(Batch batch) {
        mLifeProgressBar.Draw();
        if(mDie) {

            return;
        }

        handleInput();

        sprite.draw(batch);

    }

    @Override
    public float GetDmgRadius() {
        return radiusDig;
    }

    @Override
    public float GetGoRadius() {
        return radiusGo;
    }

    @Override
    public int GetDigDmg() {
        return digDmg;
    }

    @Override
    public float GetMaxLife() {
        return maxLife;
    }

    @Override
    public float GetLife() {
        return curLife;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public LifeProgressBar GetLifeBar() {
        return mLifeProgressBar;
    }


    @Override
    public void DealDamage(int dmg) {

        //float dmg=bomb.GetProperty().dmgMin + (float)Math.random()*bomb.GetProperty().dmgMax;

        if(curLife-dmg<0) {
            curLife = 0;
            mDie=true;
        }
        else
            curLife-=dmg;
        mLifeProgressBar.DoItVisible();

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
    public float getH() {

        return sprite.getHeight();
    }

    @Override
    public float getW() {
        return sprite.getWidth() ;
    }

    public void ChangeMoveDirection(Vector2 vec) {
        v=vec;
    }

    public void PlaceBomb() {
        if(mDie) return;
        BombPlaser.Place(this, new BombProperty(this,BombType.DSTBOMB, 300000, 10, 80, 10), new Vector2(sprite.getX()+ sprite.getOriginX(),sprite.getY()+ sprite.getOriginY() ));
    }

    public void DetonateBomb() {
        if(mDie)
            return;
        BombPlaser.DetonateBomb(this);
    }

    @Override
    public void onDoubleTap() {
        Log.d(String.format("%s: onDoubleTap", this.getName()));
        DetonateBomb();
    }

    @Override
    public void onDoubleSwipe(Vector2 v) {
        Log.d(String.format("%s: onDoubleSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onTap() {
        Log.d(String.format("%s: onTap", this.getName()));
        PlaceBomb();
    }

    @Override
    public void onSwipe(Vector2 v) {
        Log.d(String.format("%s: onSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onPan(Vector2 v) {
        Log.d(String.format("%s: onPan(%s)", this.getName(), v.toString()));
        ChangeMoveDirection(v);
    }

    @Override
    public void onDoublePan(Vector2 v) {
        Log.d(String.format("%s: onDoublePan(%s)", this.getName(), v.toString()));
    }

    public static class PlayerDirection
    {
        final public static int RIGHT=1;
        final public static int LEFT=2;
        final public static int TOP=3;
        final public static int DOWN=4;
    }

    public Texture mTexture;


    TextureRegion tRegion;
    Sprite sprite;



    private void handleInput() {

        float realMoveX=playerSpd*Gdx.graphics.getDeltaTime() * v.x;
        float realMoveY=playerSpd*Gdx.graphics.getDeltaTime() * v.y;

        int newX = (int) (sprite.getX() + sprite.getOriginX() + realMoveX);
        int newY = (int) (sprite.getY() + sprite.getOriginY() + realMoveY);



        if(MapManager.doCircleDamage(newX,newY,radiusDig,radiusGo,digDmg))
        //if(MapManager.doPlayerDamage(newX,newY,this))
         sprite.translate(realMoveX,realMoveY);
    }






    public Player(String mName, IListenerRegistration registration, Vector2 position)
    {
        this.mName = mName;
        mTexture=new Texture(Settings.TEX_MAN);
        tRegion=new TextureRegion(mTexture);
        InitRegion(PlayerDirection.DOWN );

        sprite=new Sprite(tRegion);
        sprite.flip(false,true);
        sprite.setSize(12,12);
        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);

        sprite.setPosition(-sprite.getOriginX()+(sprite.getWidth()/2)+position.x,-sprite.getOriginY()+(sprite.getHeight()/2)+position.y);

        registration.setListener(this);
        mLifeProgressBar=new LifeProgressBar(this);
    }

    public  void  InitRegion(int i)
    {
        switch (i)
        {
            case PlayerDirection.DOWN:
                tRegion.setRegion(0,0,15,15);
             break;
            case PlayerDirection.TOP:
                tRegion.setRegion(0,16,15,15);
            break;

            case PlayerDirection.LEFT:
                tRegion.setRegion(0,16+15,15,15);
                break;
            case PlayerDirection.RIGHT:
                tRegion.setRegion(0,16+15+15,15,15);
            break;

        }


    }





}
