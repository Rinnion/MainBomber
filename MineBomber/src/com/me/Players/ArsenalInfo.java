package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.Bombs.BombType;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;


/**
 * Created by alekseev on 23.09.2014.
 */
public class ArsenalInfo {
    int curIndex=0;
    TextureAtlas atlas=null;
    Pixmap pixmap;
    Texture texture;

    Sprite sprite;

    IPlayer owner;

    TextureRegion textureRegion;
    Sprite spriteRegion;


    final int pW=16;
    final int pH=16;

    final int spriteH=pH;
    final int spriteW=pW*3+4;


    final int stepX=1;


    public ArsenalInfo(IPlayer owner)
    {
        atlas=AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);

        textureRegion=atlas.findRegion(BombType.ATLAS[0]);

        spriteRegion=new Sprite(textureRegion);



        spriteRegion.setRegion(textureRegion);

        spriteRegion.setSize(pW,pH);

        spriteRegion.flip(false,true);





        pixmap=new Pixmap(pW*3+stepX,pH, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.rgba8888(0.158f,0.235f,0.255f,0.5f));
        pixmap.fill();




        pixmap.setColor(Color.rgba8888 (0.558f,0.935f,1,1));
        pixmap.fillRectangle(pW, 0, pW, pH);

        pixmap.setColor(Color.rgba8888 (0.5f,0.5f,0.5f,1));
        pixmap.drawRectangle(pW-1,0,pW+1,pH);
        pixmap.drawRectangle(0,0,pixmap.getWidth(),pixmap.getHeight());



        texture=new Texture(pixmap);

        sprite =new Sprite(texture);
        sprite.flip(false,true);

        this.owner=owner;
    }





    public void Render(Batch batch)
    {
        int x=(int)owner.getX();
        int y=(int)owner.getY();
        int h=(int)owner.getH();
        int w=(int)owner.getW();

        sprite.setPosition((x+w/2)-pixmap.getWidth()/2,y);

        sprite.draw(batch);


        textureRegion=atlas.findRegion(BombType.ATLAS[0]);
        spriteRegion.setRegion(textureRegion);
        spriteRegion.flip(false,true);
        spriteRegion.setSize(16,16);

        spriteRegion.setPosition((x+w/2)-pixmap.getWidth()/2+16,y);
        spriteRegion.draw(batch);

        textureRegion=atlas.findRegion(BombType.ATLAS[1]);
        spriteRegion.setRegion(textureRegion);

        spriteRegion.flip(false,true);
        spriteRegion.setSize(8,8);
        spriteRegion.setPosition((x+w/2)-pixmap.getWidth()/2+1,y);
        spriteRegion.draw(batch);

        textureRegion=atlas.findRegion(BombType.ATLAS[2]);
        spriteRegion.setRegion(textureRegion);
        spriteRegion.flip(false,true);
        spriteRegion.setSize(8,8);
        //findRegion.setSize(4,4);
        spriteRegion.setPosition((x+w/2)-pixmap.getWidth()/2+32,y);
        spriteRegion.draw(batch);


        //batch.draw(texture,x-texture.getWidth()/2,y-10,0.5f );

    }





}
