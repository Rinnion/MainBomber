package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.controlers.BombController;
import com.me.Bombs.BombProperty;
import com.me.Bombs.BombType;
import com.me.Map.MapManager;
import com.me.Utility.DelayTimer;
import com.me.minebomber.Settings;

/**
 * Created by alekseev on 20.03.2014.
 */
public class AiPlayer implements IPlayer, IPlayerControls {

   // @Override
   // public void onFix(Vector2 v) {
    //    this.v = v;
    //}

    float radiusDig=4.5f;
    float radiusGo=1.7f;
    int digDmg=2;
    float playerSpd=40;
      private int playerIndex=0;

    float maxLife=200;
    float curLife=200;


    Vector2 v = new Vector2(0.7f,0.7f);
    DelayTimer delayTimer=null;

     LifeProgressBar mLifeProgressBar;

     boolean mDie=false;
    @Override
    public void Render(Batch batch) {


        mLifeProgressBar.Draw();
        if(mDie) {

            return;
        }


        handleInput();

        sprite.draw(batch);
        //double random=(Math.random()*100);
        //if(random<1)



    }

    @Override
    public void DealDamage(int dmg) {

        if(curLife-dmg<0) {
            curLife = 0;
            mDie=true;
        }
        else
            curLife-=dmg;
        mLifeProgressBar.DoItVisible();
    }

    @Override
    public float getH() {
        return sprite.getHeight();
    }



    @Override
    public float getW() {
        return sprite.getWidth();
    }

    @Override
    public void addMoney(long value) {

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
        return null;
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
    public float getX() {
        return sprite.getX();
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    public void ChangeMoveDirection(Vector2 vec) {
        v=vec;
    }

    public void PlaceBomb() {}

    public void DetonateBomb() {}

    @Override
    public void onDoubleTap() {

    }

    @Override
    public void onDoubleSwipe(Vector2 v) {

    }

    @Override
    public void onTap() {

    }

    @Override
    public void onSwipe(Vector2 v) {

    }

    @Override
    public void onPan(Vector2 v) {

    }

    @Override
    public void onDoublePan(Vector2 v) {

    }

    public static class PlayerDirection
    {
        final public static int RIGHT=1;
        final public  static int LEFT=2;
        final public  static int TOP=3;
        final public static int DOWN=4;
    }

    public Texture mTexture;


    TextureRegion tRegion;
    Sprite sprite;



    private void handleInput() {
       AbstractPlayer pl= (AbstractPlayer)PlayerController.GetPlayer(playerIndex);

        float mX=sprite.getX();
        float mY=sprite.getY();
        float pX=pl.getX();
        float pY=pl.getY();
            if(mX<pX)
                v.x=1;
            else
            v.x=-1;

        if(mY<pY)
            v.y=1;
        else
        v.y=-1;

        //pl.getPlayerSprite().getX();
        //v.y=//pl.getPlayerSprite().getY();
        float realMoveX=playerSpd*Gdx.graphics.getDeltaTime() * v.x;
        float realMoveY=playerSpd*Gdx.graphics.getDeltaTime() * v.y;

        int newX = (int) (sprite.getX() + sprite.getOriginX() + realMoveX);
        int newY = (int) (sprite.getY() + sprite.getOriginY() + realMoveY);


        //Integer []indexes=MapManager.createBoundList(newX,newY,radiusDig);
        //Integer []indexesGo=MapManager.createBoundList(newX,newY,radiusGo);
        if(MapManager.doCircleDamage (newX, newY, radiusDig, radiusGo,digDmg))
            sprite.translate(realMoveX,realMoveY);
        else {

           if((delayTimer==null)||(delayTimer.CheckTimeOut())) {
               BombController.Add(this,
                       new BombProperty(this, BombType.DYNAMITE, 2000, 40, 100, 10),
                       new Vector2((sprite.getX()), (sprite.getY())));
               delayTimer=new DelayTimer((int)((Math.random()*5000)+5000));
           }
        }


    }




    public AiPlayer(int indexPlayer, Vector2 position)
    {
        playerIndex=indexPlayer;
        mTexture=new Texture(Settings.TEX_AI);
        tRegion=new TextureRegion(mTexture);
        InitRegion(PlayerDirection.DOWN );

        sprite=new Sprite(tRegion);
        sprite.flip(false,true);
        sprite.setSize(25,25);
        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);

        sprite.setPosition(-sprite.getOriginX()+(sprite.getWidth()/2)+position.x,-sprite.getOriginY()+(sprite.getHeight()/2)+position.y);
        radiusDig=sprite.getWidth()/4;
        radiusGo=sprite.getWidth()/6;
        digDmg=1;
        playerSpd=20f;
        mLifeProgressBar=new LifeProgressBar(this);
    }

    public  void  InitRegion(int i)
    {
        switch (i)
        {
            case PlayerDirection.DOWN:
                tRegion.setRegion(0,0,37,37);
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
