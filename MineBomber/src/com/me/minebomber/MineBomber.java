package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.me.Map.MapManager;
import com.me.Map.PixmapHelper;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;
import com.me.TextManager.IText;
import com.me.TextManager.TextFont;
import com.me.TextManager.TextManager;
import com.me.TextManager.TextOut;
import com.me.TilesManager.TilesLoader;
import com.me.Utility.RollingQueue;
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

public class MineBomber {
    static OrthographicCamera camera;
    private static long scheduleDtStart;
    private static long scheduleDtBomb;
    private static long scheduleDtPlayer;
    private static long scheduleDtMap;
    private static Logger logger = LoggerFactory.getLogger(MineBomber.class);
    private static IText debug;
    private static SpriteBatch batch;
    private static boolean isInitialized = false;
    private static long logicFrame = 0;
    private static Timer timer;
    private static RollingQueue<Runnable> Jobs;

    private static void startgametimer() {
        Jobs = new RollingQueue<>(Runnable.class);

        timer = new Timer("logic timer");

        timer.scheduleAtFixedRate(
                new TimerTask() {
                    @Override
                    public void run() {
                        Calculate();
                    }
                }, 0, 50
        );
    }

    public static void postJob(Runnable runnable) {
        Jobs.push(runnable);
    }

    private static void Calculate() {
        Runnable pop = Jobs.pop();
        if (pop != null) pop.run();

        logger.trace("logic in");
        logger.info("Logic frame: {}", logicFrame++);
        scheduleDtStart = Calendar.getInstance().getTimeInMillis();
        GameObjectController.calculate(scheduleDtStart);

        scheduleDtBomb = Calendar.getInstance().getTimeInMillis();
        PlayerController.Calculate(scheduleDtStart);

        scheduleDtPlayer = Calendar.getInstance().getTimeInMillis();
        MapManager.Calculate(scheduleDtStart);

        scheduleDtMap = Calendar.getInstance().getTimeInMillis();
        ActionController.Calculate(scheduleDtStart);

        long diff = scheduleDtMap - scheduleDtStart;
        if (diff > 20)
            logger.warn("########## LOGIC TIME > {} ({}) !!! ##########", 20, diff);
        if (diff > 50)
            logger.error("########## LOGIC TIME > {} ({}) !!! ##########", 50, diff);
        logger.trace("logic out");
    }

    public static void Done() {
        if (timer != null) {
            timer.purge();
            timer.cancel();
            timer = null;
        }
        batch.dispose();
    }

    public static void Initialize() {

        if (isInitialized) throw new IllegalStateException("Mine bomber already initialized");

        logicFrame = 0;

        logger.info("MineBomber");
        logger.debug("Prepare textures and assets files");
        PrepareAssetsFiles.Prepare();
        logger.debug("Initialize tiles from XML");
        TilesLoader.Initialize();

        logger.debug("Initialize TextManager");
        TextManager.Initialize();
        debug = new TextOut();
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

        logger.debug("Initialize Players");
        initializeGraphic();

        System.gc();
        startgametimer();
        isInitialized = true;
    }

    private static void initializeGraphic() {
        batch = new SpriteBatch();
        float scrW = MapManager.mapProperty.width;
        float scrH = MapManager.mapProperty.height;
        camera = new OrthographicCamera(scrW, scrH);
        camera.setToOrtho(true, scrW, scrH);
        MapManager.Refresh(camera);
    }

    public static void render(Rectangle viewPort) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport((int) viewPort.getX(), (int) viewPort.getY(), (int) viewPort.getWidth(), (int) viewPort.getHeight());

        camera.update();
        MineBomber.batch.setProjectionMatrix(camera.combined);

        long dtStart = Calendar.getInstance().getTimeInMillis();

        MineBomber.batch.begin();
        MineBomber.batch.disableBlending();
        MapManager.Render(MineBomber.batch);
        MineBomber.batch.enableBlending();
        GameObjectController.Render(MineBomber.batch);
        PlayerController.Render(MineBomber.batch);
        ParticleManager.Draw(MineBomber.batch, Gdx.graphics.getDeltaTime());
        TextManager.Draw(MineBomber.batch);
        MineBomber.debug.Draw(MineBomber.batch);
        MineBomber.batch.end();

        long dtEnd = Calendar.getInstance().getTimeInMillis();
        long dtResMap = scheduleDtMap - scheduleDtStart;
        if (dtResMap < 0) dtResMap = 0;
        MineBomber.debug.SetText(Long.toString(dtEnd - dtStart) + " " + Long.toString(scheduleDtBomb - scheduleDtStart) + " " + Long.toString(scheduleDtPlayer - scheduleDtStart) + " " + Long.toString(dtResMap));

        PlayerController.AfterBatch(camera.combined);

    }
}
