package com.me.minebomber;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.me.assetloader.AssetLoader;

/**
 * Created by alekseev on 24.03.2014.
 */
public class PrepareAssetsFiles {

    public static void Prepare()
    {
        AssetLoader.load(Settings.TEX_MAP_OBJECTS, Pixmap.class);
        AssetLoader.load(Settings.TEX_MAN, Texture.class);
        AssetLoader.DoCompliteLoading();
    }

}
