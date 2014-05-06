package com.me.minebomber;

import com.me.controlers.BombController;
import com.me.Map.MapManager;
import com.me.Map.PixmapHelper;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;
import com.me.TextManager.TextManager;
import com.me.TextManager.TextOut;
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

        Log.d("Initialize TextManager");
        TextManager.Initialize();
        MineBomber.textZoom=new TextOut();

        Log.d("Initialize PixmapHelper");
        PixmapHelper.Initialize();
        Log.d("Initialize MapManager");
        MapManager.Initialize();

        Log.d("Initialize BombController");
        BombController.Initialize();
        Log.d("Initialize ParticleManager");
        ParticleManager.Initialize();
        Log.d("Initialize Players") ;
        PlayerController.Initialize();

    }

}
