package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.me.assetloader.AssetLoader;
import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion;

import java.awt.*;
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

        miniObject=new Pixmap(4,4, Pixmap.Format.RGBA8888);
        miniObject.setBlending(Pixmap.Blending.None);
        miniObject.setColor(Color.BLACK);

        miniObject.fill();
        miniObject.setBlending(Pixmap.Blending.SourceOver);

    }

    static void Draw(TextureRegion textureRegion,Texture dstTexture,int dstX,int dstY)
    {

        miniObject.setBlending(Pixmap.Blending.None);
        mapHelper.setBlending(Pixmap.Blending.None);

       // dstTexture.
       miniObject.drawPixmap(mapHelper,0,0, textureRegion.getRegionX(),textureRegion.getRegionY(),textureRegion.getRegionWidth()-4,textureRegion.getRegionHeight()-4);


       // Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, dstTexture.getTextureObjectHandle());
      //  Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, dstX, dstY, miniObject.getWidth(), miniObject.getHeight(), miniObject.getGLFormat(), miniObject.getGLType(), miniObject.getPixels());

        dstTexture.draw(miniObject,dstX,dstY);
        miniObject.setBlending(Pixmap.Blending.SourceOver);
        mapHelper.setBlending(Pixmap.Blending.SourceOver);
    }






}
