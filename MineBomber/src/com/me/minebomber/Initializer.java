package com.me.minebomber;

import com.me.Map.MapManager;
import com.me.Map.PixmapHelper;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;
import com.me.TextManager.TextFont;
import com.me.TextManager.TextManager;
import com.me.TextManager.TextOut;
import com.me.TilesManager.TilesLoader;
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.logger.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by alekseev on 24.03.2014.
 */



public class Initializer {

    public static final int STAGE_MENU=0;
    public static final int STAGE_GAME=1;
    public static int stage=STAGE_MENU;
    public static long sheduleDtStart;
    public static long sheduleDtBomb;
    public static long sheduleDtPlayer;
    public static long sheduleDtMap;
    private static boolean isInitialized = false;
    private static Timer timer;

    private static void startgametimer() {
        timer = new Timer("logic timer");

        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          Log.d("logic in");
                                          sheduleDtStart = Calendar.getInstance().getTimeInMillis();
                                          GameObjectController.calculate(sheduleDtStart);

                                          sheduleDtBomb = Calendar.getInstance().getTimeInMillis();
                                          PlayerController.Calculate(sheduleDtStart);

                                          sheduleDtPlayer = Calendar.getInstance().getTimeInMillis();
                                          MapManager.Calculate(sheduleDtStart);

                                          sheduleDtMap = Calendar.getInstance().getTimeInMillis();
                                          ActionController.Calculate(sheduleDtStart);

                                          long diff = sheduleDtMap - sheduleDtStart;
                                          if (diff > 20)
                                              Log.i(String.format("########## LOGIC TIME > 20 (%s) !!! ##########", diff));
                                          Log.d("logic out");
                                      }
                                  }, 0, 50
        );

        stage = STAGE_GAME;
    }

    public static  void Initialize()
    {
        if(!isInitialized) {
            stage=STAGE_GAME;
            Log.Initialize("MineBomber");
            Log.i("MineBomber Initialize");
            Log.d("Prepare textures and assets files");
            PrepareAssetsFiles.Prepare();
            Log.d("Initialize tiles from XML");
            TilesLoader.Initialize();

            Log.d("Initialize TextManager");
            TextManager.Initialize();
            MineBomber.textZoom = new TextOut();
            TextFont.Initialize();

            Log.d("Initialize PixmapHelper");
            PixmapHelper.Initialize();

            Log.d("Initialize Memory");
            MemoryManager.Initialize();
            Log.d("Initialize MapManager");
            MapManager.Initialize();

            Log.d("Initialize ParticleManager");
            ParticleManager.Initialize();
            Log.d("Initialize Players");
            PlayerController.Initialize();
        }
        startgametimer();
        System.gc();

    }

}
