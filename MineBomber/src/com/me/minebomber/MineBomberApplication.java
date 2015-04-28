package com.me.minebomber;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.me.Map.MapManager;
import com.me.Players.PlayerController;
import com.me.fsm.*;
import com.minebomber.MenuManager.MenuAction;
import com.minebomber.MenuManager.MenuActions;
import com.minebomber.MenuManager.MenuManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;

public class MineBomberApplication implements ApplicationListener {

    static final FSM<MineBomberState> fsm;
    static Logger logger = LoggerFactory.getLogger(MineBomberApplication.class);


    static boolean fullScreen = true;
    static Rectangle viewPort;

    public MineBomberApplication() {

    }

    public static FSM getFSM() {
        if (fsm == null) throw new IllegalStateException("not initialized");
        return fsm;
    }

    @Override
    public void create() {
        logger.info("Creating game...");
        viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }


    @Override
    public void dispose() {

    }

    @Override
    public void render() {
        fsm.getState().render(viewPort);
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
                        MineBomberApplication.getFSM().doIt(Inputs.start);
                    }
                    if (action.equals("start.3")) {
                        PlayerController.hotSeatPlayers = 3;
                        MineBomberApplication.getFSM().doIt(Inputs.start);
                    }
                    if (action.equals("start.4")) {
                        PlayerController.hotSeatPlayers = 4;
                        MineBomberApplication.getFSM().doIt(Inputs.start);
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
        public void render(Rectangle viewPort) {
            MenuManager.Draw(viewPort);
        }
    }

    static class GameState extends MineBomberState {

        public GameState() {
            super("game");
        }

        @Override
        public void preAction(FSM fsm) {
            MineBomber.Initialize();

            float scrW = MapManager.mapProperty.width;
            float scrH = MapManager.mapProperty.height;

            if (!fullScreen)
                viewPort = new Rectangle((int) ((Gdx.graphics.getWidth() - scrW) / 2f), (int) ((Gdx.graphics.getHeight() - scrH) / 2f), (int) scrW, (int) scrH);
            else
                viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        }

        @Override
        public void postAction(FSM fsm) {
            MineBomber.Done();
        }

        @Override
        public void render(Rectangle viewPort) {
            MineBomber.render(viewPort);

        }

    }
}

