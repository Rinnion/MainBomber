package com.me.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;

/**
 * Created by alekseev on 21.03.2014.
 */
public class PixmapHelper {

    static Pixmap mapHelper;
    static Pixmap miniObject;

    public static void Initialize() {
        mapHelper = AssetLoader.GetPixmap(Settings.TEX_MAP_OBJECTS);
        miniObject = new Pixmap(MapManager.rowW, MapManager.rowH, Pixmap.Format.RGBA8888);
        miniObject.setColor(Color.CLEAR);
        miniObject.setBlending(Pixmap.Blending.None);
        mapHelper.setBlending(Pixmap.Blending.None);
    }

    public static void DrawMiniObject(Pixmap dstPixmap, int srcX, int srcY) {
        dstPixmap.drawPixmap(mapHelper, 0, 0, srcX, srcY, dstPixmap.getWidth(), dstPixmap.getHeight());
    }

    static public void Bind(Texture dstTexture) {
        //dstTexture.bind();
        Gdx.gl.glBindTexture(Gdx.gl20.GL_TEXTURE_2D, dstTexture.getTextureObjectHandle());

    }

    public static void DrawPixmap(Pixmap pixmap, int x, int y) {
        Gdx.gl.glTexSubImage2D(Gdx.gl20.GL_TEXTURE_2D, 0, x, y, pixmap.getWidth(), pixmap.getHeight(), pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
    }

}
