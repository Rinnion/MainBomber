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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    static Logger logger = LoggerFactory.getLogger(Initializer.class);
    private static boolean isInitialized = false;
    private static long logicFrame = 0;

    private static void startgametimer() {
        Timer timer = new Timer("logic timer");

        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          logger.trace("logic in");
                                          logger.info("Logic frame: {}", logicFrame);
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
                                              logger.warn("########## LOGIC TIME > {} ({}) !!! ##########", 20, diff);
                                          if (diff > 50)
                                              logger.error("########## LOGIC TIME > {} ({}) !!! ##########", 50, diff);
                                          logger.trace("logic out");
                                      }
                                  }, 0, 50
        );

        stage = STAGE_GAME;
    }

    public static  void Initialize()
    {
        if(!isInitialized) {
            logicFrame = 0;

            stage=STAGE_GAME;
            logger.info("MineBomber");
            logger.debug("Prepare textures and assets files");
            PrepareAssetsFiles.Prepare();
            logger.debug("Initialize tiles from XML");
            TilesLoader.Initialize();

            logger.debug("Initialize TextManager");
            TextManager.Initialize();
            MineBomber.textZoom = new TextOut();
            TextFont.Initialize();

            logger.debug("Initialize PixmapHelper");
            PixmapHelper.Initialize();

            logger.debug("Initialize Memory");
            MemoryManager.Initialize();
            logger.debug("Initialize MapManager");
            MapManager.Initialize();

            logger.debug("Initialize ParticleManager");
            ParticleManager.Initialize();
            logger.debug("Initialize Players");
            PlayerController.Initialize();
        }
        startgametimer();
        System.gc();
    }

}
