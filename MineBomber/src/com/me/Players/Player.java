package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.MapManager;
import com.me.minebomber.Settings;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player  implements IPlayer,IPlayerControlls {

   // @Override
   // public void onFix(Vector2 v) {
    //    this.v = v;
    //}

    Vector2 v = new Vector2(0.7f,0.7f);
    @Override
    public void Render() {
           handleInput();
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public Sprite getPlayerSprite() {
        return sprite;
    }

    @Override
    public void ChangeMoveDeriction(Vector2 vec) {
        v=vec;
    }

    public static class PlayerDeriction
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

        float realMoveX=50*Gdx.graphics.getDeltaTime() * v.x;
        float realMoveY=50*Gdx.graphics.getDeltaTime() * v.y;

        int newX = (int) (sprite.getX() + sprite.getOriginX() + realMoveX);
        int newY = (int) (sprite.getY() + sprite.getOriginY() + realMoveY);

        if(MapManager.DrawPixMap(newX, newY))
        {
            sprite.translate(realMoveX,realMoveY);
        }

    }

    public Player(IListenerRegistrator registrator, Vector2 position)
    {
        mTexture= AssetLoader.GetTexture(Settings.TEX_MAN); //new Texture("data/mans.png");
        tRegion=new TextureRegion(mTexture);
        InitRegion(PlayerDeriction.DOWN );

        sprite=new Sprite(tRegion);
        sprite.flip(false,true);
        sprite.setSize(12,12);
        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);

        sprite.setPosition(-sprite.getOriginX()+(sprite.getWidth()/2)+position.x,-sprite.getOriginY()+(sprite.getHeight()/2)+position.y);

        registrator.setListener(this);
    }

    public  void  InitRegion(int i)
    {
        switch (i)
        {
            case PlayerDeriction.DOWN:
                tRegion.setRegion(0,0,15,15);
             break;
            case PlayerDeriction.TOP:
                tRegion.setRegion(0,16,15,15);
            break;

            case PlayerDeriction.LEFT:
                tRegion.setRegion(0,16+15,15,15);
                break;
            case PlayerDeriction.RIGHT:
                tRegion.setRegion(0,16+15+15,15,15);
            break;

        }


    }





}
