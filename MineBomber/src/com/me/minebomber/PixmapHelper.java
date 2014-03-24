package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.me.assetloader.AssetLoader;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alekseev on 21.03.2014.
 */
public class PixmapHelper {

    static Pixmap mapHelper;
    static Pixmap miniObject;

    public static void  Initialize()
    {

         mapHelper =AssetLoader.GetPixmap(Settings.TEX_MAP_OBJECTS);

        miniObject=new Pixmap(8,8, Pixmap.Format.RGB888);
    }

    static void Draw(TextureRegion textureRegion,Texture dstTexture,int dstX,int dstY)
    {


       miniObject.drawPixmap(mapHelper,0,0, textureRegion.getRegionX(),textureRegion.getRegionY(),textureRegion.getRegionWidth(),textureRegion.getRegionHeight());


        //Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, dstTexture.getTextureObjectHandle());
        //Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, dstX, dstY, miniObject.getWidth(), miniObject.getHeight(), miniObject.getGLFormat(), miniObject.getGLType(), miniObject.getPixels());
          dstTexture.draw(miniObject,dstX,dstY);
    }






}
