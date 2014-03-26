package com.me.minebomber;

import com.badlogic.gdx.graphics.Pixmap;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;
import com.me.assetloader.AssetLoader;
import com.me.logger.Log;

/**
 * Created by alekseev on 24.03.2014.
 */
public class Initializer {
    public static  void Initialize()
    {
        Log.Initialize("MineBomber");
        Log.i("MineBomber Initialize");
        Log.d("Prepare textures and assets files");
        PrepareAssetsFiles.Prepare();
        Log.d("Initialize fonts");
        TextOut.Initialize();
        Log.d("Initialize MapManager");
        MapManager.Initialize();
        Log.d("Initialize PixmapHelper");
        PixmapHelper.Initialize();
        Log.d("Initialize ParticleManager");
        ParticleManager.Initialize();
        Log.d("Initialize Players") ;
        PlayerController.Initialize();

    }

}
