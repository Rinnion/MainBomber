package com.me.minebomber;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.assetloader.AssetLoader;

/**
 * Created by alekseev on 24.03.2014.
 */
public class Assets {

    public static void LoadAll()
    {
        AssetLoader.load(Settings.TEX_MAP_OBJECTS, Pixmap.class);
        AssetLoader.load(Settings.BOMB_DYNAMITE, TextureAtlas.class);
        AssetLoader.load(Settings.PLAYER_SKIN , TextureAtlas.class);
        AssetLoader.DoCompliteLoading();
    }

    public static void UnloadAll() {
        AssetLoader.unload(Settings.TEX_MAP_OBJECTS);
        AssetLoader.unload(Settings.BOMB_DYNAMITE);
        AssetLoader.unload(Settings.PLAYER_SKIN);
    }

}
