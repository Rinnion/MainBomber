package com.me.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.me.Map.MapManager;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;

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
        miniObject.setColor(Color.CLEAR);
        miniObject.setBlending(Pixmap.Blending.None);
        mapHelper.setBlending(Pixmap.Blending.None);
    }


    static public void Draw(TextureRegion textureRegion, com.badlogic.gdx.math.Rectangle texStep, Texture dstTexture,int dstX,int dstY)
    {
           miniObject.drawPixmap(mapHelper, 0, 0, textureRegion.getRegionX()+(int)texStep.getX(), textureRegion.getRegionY()+(int)texStep.getY(),(int)texStep.getWidth(),(int)texStep.getHeight());
           dstTexture.draw(miniObject, dstX, dstY);
    }






}
