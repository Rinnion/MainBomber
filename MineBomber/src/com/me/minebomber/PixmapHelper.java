package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.*;
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

        miniObject=new Pixmap(MapManager.rowW,MapManager.rowH, Pixmap.Format.RGBA8888 );
        miniObject.setBlending(Pixmap.Blending.None);
        mapHelper.setBlending(Pixmap.Blending.None);

    }

    static void Draw(TextureRegion textureRegion, com.badlogic.gdx.math.Rectangle texStep, Texture dstTexture,int dstX,int dstY)
    {



       // dstTexture.
       synchronized (miniObject) {
           miniObject.drawPixmap(mapHelper, 0, 0, textureRegion.getRegionX()+(int)texStep.getX(), textureRegion.getRegionY()+(int)texStep.getY(),(int)texStep.getWidth(),(int)texStep.getHeight());
                   //textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
           dstTexture.draw(miniObject, dstX, dstY);
       }

    }






}
