package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player  implements IFightInputListener {

    @Override
    public void onFix(Vector2 v) {
        this.v = v;
    }

    Vector2 v = new Vector2(0.7f,0.7f);

    public static class PlayerDeriction
    {
        final public static int RIGHT=1;
        final public  static int LEFT=2;
        final public  static int TOP=3;
        final public static int DOWN=4;
    }

    public Texture mTexture;
    public Pixmap pixMap;

    TextureRegion tRegion;
    Sprite sprite;



    private void handleInput() {
/*
        float x=0;
        float y=0;
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x=-1;
           sprite.translateX(-1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x=1;
            sprite.translateX(1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y=1;
            sprite.translateY(1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y=-1;
            sprite.translateY(-1);

        }

        //x=5;
        //sprite.translateX(x*Gdx.graphics.getDeltaTime());
        //sprite.translateX(realMoveX);

        float realMoveX=10*Gdx.graphics.getDeltaTime();
        float realMoveY=10*Gdx.graphics.getDeltaTime();

        if(MapManager.DrawPixMap(pixMap,(int)(sprite.getX()+sprite.getOriginX()+realMoveX),((int)(sprite.getY()+sprite.getOriginY()+realMoveY))))
        {
            sprite.translateX(realMoveX);
            sprite.translateY(realMoveY);
        }
*/
        float realMoveX=10*Gdx.graphics.getDeltaTime() * v.x;
        float realMoveY=10*Gdx.graphics.getDeltaTime() * v.y;

        if(MapManager.DrawPixMap(pixMap,(int)(sprite.getX()+sprite.getOriginX()+realMoveX),((int)(sprite.getY()+sprite.getOriginY()+realMoveY))))
        {
            sprite.translate(realMoveX,realMoveY);
        }

    }




    public Player()
    {
        mTexture=new Texture("data/mans.png");
        tRegion=new TextureRegion(mTexture);
        InitRegion(PlayerDeriction.DOWN );

        sprite=new Sprite(tRegion);
        sprite.flip(false,true);
        sprite.setSize(8,8);
        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);

        //sprite.rotate(90);
        sprite.setPosition(-sprite.getOriginX()+10,-sprite.getOriginY()+10 );

        pixMap=new Pixmap(4,4, Pixmap.Format.RGBA8888);
        pixMap.setBlending(Pixmap.Blending.None);
         pixMap.setColor(0,0,0,0.0f);
        pixMap.drawCircle(0, 0, 10);


        //pixMap.setBlending(Pixmap.Blending.SourceOver);

        //pixMap.
        //pixMap.();


    }

    public void Render(SpriteBatch sb)
    {
        handleInput();

        sprite.draw(sb);
        //sb.draw(sprite.getTexture(),sprite.getX(),sprite.getY());
        //sb.flush();
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
