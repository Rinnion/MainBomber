package com.me.minebomber;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.assetloader.AssetLoader;

import java.io.File;

/**
 * Created by alekseev on 24.03.2014.
 */
public class PrepareAssetsFiles {

    public static void Prepare()
    {

        AssetLoader.load(Settings.TEX_MAP_OBJECTS, Pixmap.class);
        AssetLoader.load(Settings.TEX_MAN, Texture.class);
        AssetLoader.load(Settings.BOMB_DYNAMITE, TextureAtlas.class);
        AssetLoader.DoCompliteLoading();
    }

}
