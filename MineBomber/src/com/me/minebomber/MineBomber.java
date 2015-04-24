package com.me.minebomber;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.me.Map.MapManager;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;
import com.me.TextManager.IText;
import com.me.TextManager.TextManager;
import com.me.controlers.GameObjectController;
import com.me.fsm.*;
import com.minebomber.MenuManager.MenuAction;
import com.minebomber.MenuManager.MenuActions;
import com.minebomber.MenuManager.MenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MineBomber implements ApplicationListener {

    static final FSM<MineBomberState> fsm;
    static Logger logger = LoggerFactory.getLogger(MineBomber.class);
    static IText textZoom;
    static long BeginDrawTime;
    static float scrW;
    static float scrH;
    static boolean fullScreen = true;
    static OrthographicCamera camera;
    static Rectangle viewPort;
    static SpriteBatch batch;
    FPSLogger fpsLogger;

    public MineBomber(){

    }

    public static FSM getFSM() {
        if (fsm == null) throw new IllegalStateException("not initialized");
        return fsm;
    }

    @Override
    public void create() {
        logger.info("Creating game...");

        fpsLogger = new FPSLogger();
        batch = new SpriteBatch();
        viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glViewport((int) viewPort.getX(), (int) viewPort.getY(), (int) viewPort.getWidth(), (int) viewPort.getHeight());
        fsm.getState().render();
    }

    @Override
    public void resize(int width, int height) {
        viewPort = new Rectangle(0, 0, width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public static class Inputs {
        public static Input close = new InputImpl("close");
        public static Input start = new InputImpl("start");
    }

    static {
        Action skip = new Action() {
            @Override
            public void doIt(final FSM fsm, Input in) {
                Timer timer = new Timer("skip");
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Gdx.app.postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                fsm.doIt(Inputs.close);
                            }
                        });
                    }
                }, 2000);
            }
        };

        MineBomberState stateLoad = new MineBomberState("load");
        MineBomberState stateLogo = new MineBomberState("logo");
        MineBomberState stateMenu = new MenuState();
        MineBomberState stateGame = new GameState();
        MineBomberState stateExit = new MineBomberState("exit");


        StateEngine<MineBomberState> stateEngine = StateEngineFactory.create();
        stateEngine.add(stateLoad, Inputs.close, skip, stateLogo);
        stateEngine.add(stateLogo, Inputs.close, null, stateMenu);
        stateEngine.add(stateMenu, Inputs.start, null, stateGame);
        stateEngine.add(stateGame, Inputs.close, null, stateMenu);
        stateEngine.add(stateMenu, Inputs.close, null, stateExit);
        stateEngine.done();

        fsm = stateEngine.makeFSM(stateLoad);
        fsm.doIt(Inputs.close);
    }

    static class MenuState extends MineBomberState {

        public MenuState() {
            super("menu");
        }

        @Override
        public void preAction(FSM fsm) {
            MenuActions.SetCallback(new MenuAction() {
                @Override
                public void buttonDown(String action) {
                    if (action.equals("start.2")) {
                        PlayerController.hotSeatPlayers = 2;
                        MineBomber.getFSM().doIt(Inputs.start);
                    }
                    if (action.equals("start.3")) {
                        PlayerController.hotSeatPlayers = 3;
                        MineBomber.getFSM().doIt(Inputs.start);
                    }
                    if (action.equals("start.4")) {
                        PlayerController.hotSeatPlayers = 4;
                        MineBomber.getFSM().doIt(Inputs.start);
                    }
                }

                @Override
                public void listSelected(String action, Object value) {

                }
            });
            MenuManager.Initialize();
        }

        @Override
        public void postAction(FSM fsm) {
            MenuActions.SetCallback(null);
            MenuManager.Done();
        }

        @Override
        public void render() {
            MenuManager.Draw();
        }
    }

    static class GameState extends MineBomberState {

        public GameState() {
            super("game");
        }

        @Override
        public void preAction(FSM fsm) {
            Initializer.Initialize();

            scrW = MapManager.mapProperty.width;
            scrH = MapManager.mapProperty.height;

            camera = new OrthographicCamera(scrW, scrH);
            camera.setToOrtho(true, scrW, scrH);

            if (!fullScreen)
                viewPort = new Rectangle((int) ((Gdx.graphics.getWidth() - scrW) / 2f), (int) ((Gdx.graphics.getHeight() - scrH) / 2f), (int) scrW, (int) scrH);
            else
                viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            MapManager.Refresh(camera);
        }

        @Override
        public void postAction(FSM fsm) {
            Initializer.Done();
        }

        @Override
        public void render() {
            camera.update();
            batch.setProjectionMatrix(camera.combined);

            long dtStart = Calendar.getInstance().getTimeInMillis(); //new Date().getTime();
            BeginDrawTime = dtStart;

            batch.begin();
            batch.disableBlending();
            MapManager.Render(batch);
            batch.enableBlending();

            GameObjectController.Render(batch);
            PlayerController.Render(batch);
            ParticleManager.Draw(batch, Gdx.graphics.getDeltaTime());
            textZoom.Draw(batch);
            TextManager.Draw(batch);
            batch.end();

            long dtEnd = Calendar.getInstance().getTimeInMillis();
            long dtResMap = Initializer.sheduleDtMap - Initializer.sheduleDtStart;
            if (dtResMap < 0) dtResMap = 0;
            textZoom.SetText(Long.toString(dtEnd - dtStart) + " " + Long.toString(Initializer.sheduleDtBomb - Initializer.sheduleDtStart) + " " + Long.toString(Initializer.sheduleDtPlayer - Initializer.sheduleDtStart) + " " + Long.toString(dtResMap));

            PlayerController.AfterBatch(camera.combined);
        }
    }
}

