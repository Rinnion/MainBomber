package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.TextManager.TextManager;
import com.me.assetloader.AssetLoader;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.controlers.actions.PutBombAction;
import com.me.logger.Log;
import com.me.minebomber.Settings;

import java.util.Calendar;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player extends AbstractPlayer implements IPlayerControls {

   // @Override
   // public void onFix(Vector2 v) {
    //    this.v = v;
    //}






    public void ChangeMoveDirection(Vector2 vec) {
        v=vec;
    }

    public void PlaceBomb() {
        if(mDie) return;


        AbstractBomb bomb=arsenal.PutBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(),sprite.getY() + sprite.getOriginY()));
        arsenalInfo.DoItVisible();

        /*switch (mCurrentBomb){
            case 0:bomb=new DestBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
            case 1:bomb=new Dynamite(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
            case 2:bomb=new RandomBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
            case 3:bomb=new PunchTeraStone(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
            case 4:bomb=new FastFilledBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
            case 5:bomb=new FilledBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(), sprite.getY() + sprite.getOriginY())); break;
        }*/



        if (bomb != null) {
            bombList.add(bomb);
            ActionController.Add(new PutBombAction(this, Calendar.getInstance().getTime().getTime(), bomb));
        }
    }

    public void DetonateBomb() {
        if(mDie)
            return;
        //BombController.DetonateBomb(this);
        long time=Calendar.getInstance().getTimeInMillis();

        for(AbstractBomb bomb:bombList)
        {
            if(bomb.activate (time))
            break;
        }


    }

    @Override
    public void onDoubleTap() {
        Log.d(String.format("%s: onDoubleTap", this.getName()));
        DetonateBomb();
    }

    @Override
    public void onDoubleSwipe(Vector2 v) {
        //mCurrentBomb = (mCurrentBomb + 1) % 6;
        arsenal.addIndex();

        TextManager.Add("CurBomb: " + arsenal.sindex, Color.GRAY,sprite.getX() + sprite.getOriginX(),sprite.getY() + sprite.getOriginY() );
        arsenalInfo.DoItVisible();
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


    //TextureRegion tRegion;


    public Player(String mName, IListenerRegistration registration, Vector2 position)
    {
        super(mName);
        //mTexture=new Texture(Settings.TEX_MAN);


       sprite = AnimatedSprite.Factory.CreatePlayer(Settings.PLAYER_SKIN);

        //TextureAtlas atlas= AssetLoader.GetAtlas(Settings.PLAYER_SKIN);






         /*
        //InitRegion(PlayerDirection.DOWN);

        animationSprite=new Animation(0.23f,atlas.findRegion("player_go") );

        //animationSprite.getKeyFrame()

        sprite=new Sprite(atlas.getTextures().first());

        sprite.setRegion(animationSprite.getKeyFrame(0));
        sprite.flip(false,true);
        */
       // sprite.SetPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        sprite.setSize(9.5f,9.5f);

        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);



        sprite.setPosition( position.x - sprite.getOriginX(), position.y - sprite.getOriginY() );

        registration.setListener(this);
    }

    public  void  InitRegion(int i)
    {
        switch (i)
        {
            case PlayerDirection.DOWN:

             break;
            case PlayerDirection.TOP:

            break;

            case PlayerDirection.LEFT:

                break;
            case PlayerDirection.RIGHT:

            break;

        }


    }





}
