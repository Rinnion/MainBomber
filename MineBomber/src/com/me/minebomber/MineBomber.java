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
import com.me.logger.Log;
import com.minebomber.MenuManager.MenuAction;
import com.minebomber.MenuManager.MenuActions;

import com.minebomber.MenuManager.MenuManager;

import java.util.Calendar;


public class MineBomber implements ApplicationListener {
    public static IText textZoom;
    public static long BeginDrawTime;
    //private Texture texture;
    //private Sprite sprite;
    //private Sprite sprite2;
    float sX=0.008f;
    float sY=0.008f;
    float mX;
    float mY;
    FPSLogger loger;
    static float scrW;
    static float scrH;
    static boolean fullScreen=true;
    private static OrthographicCamera camera;
    private SpriteBatch batch;
    private static Rectangle viewPort;


    public static void startgame()
    {
        Initializer.Initialize();

        scrW = MapManager.mapProperty.width; //Gdx.graphics.getWidth();
        scrH = MapManager.mapProperty.height; //Gdx.graphics.getHeight();


        camera = new OrthographicCamera(scrW, scrH);
        camera.setToOrtho(true, scrW, scrH);

        if (fullScreen == false)
            viewPort = new Rectangle((int) ((Gdx.graphics.getWidth() - scrW) / 2f), (int) ((Gdx.graphics.getHeight() - scrH) / 2f), (int) scrW, (int) scrH);
        else
            viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //camera.zoom=0.5f;
        //MapManager.SetView(camera);


        MapManager.Refresh(camera);
    }





    @Override
	public void create() {

        Log.d("create");



        //camera.position.set( 1/2,(h/w)/2,0);
        //camera.position.set( 1.0f/2f,(h/w)/2,0);      // camera.setToOrtho(true);

        loger = new FPSLogger();
        batch = new SpriteBatch();

        MenuActions.SetCallback(new MenuAction() {
            @Override
            public void buttonDown(String action) {
                if(action.equals("start.2"))
                {
                    PlayerController.hotSeatPlayers=2;
                    startgame();
                }
                if(action.equals("start.3"))
                {
                    PlayerController.hotSeatPlayers=3;
                    startgame();
                }
                if(action.equals("start.4"))
                {
                    PlayerController.hotSeatPlayers=4;
                    startgame();
                }

            }
        });

        MenuManager.Initialize();
        viewPort = new Rectangle(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());




    }

	@Override
	public void dispose() {
		batch.dispose();

	}

	@Override
	public void render() {

        //Log.d("render");

		//Gdx.gl.glClearColor(1, 0, 1, 1);
        //Gdx.gl.glClear(0);
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);


        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT| (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));



        Gdx.gl.glViewport((int)viewPort.getX() ,(int)viewPort.getY(),(int)viewPort.getWidth(),(int)viewPort.getHeight());

        switch (Initializer.stage) {
            case Initializer.STAGE_GAME:
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
                if (dtResMap < 0)
                    dtResMap = 0;
                textZoom.SetText(Long.toString(dtEnd - dtStart) + " " + Long.toString(Initializer.sheduleDtBomb - Initializer.sheduleDtStart) + " " + Long.toString(Initializer.sheduleDtPlayer - Initializer.sheduleDtStart) + " " + Long.toString(dtResMap));

                PlayerController.AfterBatch(camera.combined);
                break;
                case  Initializer.STAGE_MENU:
                    MenuManager.Draw();
                break;
            }



       // ShapeCircle.Draw(camera.combined);

        //batch.getProjectionMatrix().setToOrtho2D(0, 0,Gdx.graphics.getWidth(),  Gdx.graphics.getHeight());
	/*	batch.setProjectionMatrix(camera.combined);
		batch.begin();
        for(float x=0;x<mX;x++)
        for(float y=0;y<mY;y++)
        {
             sprite.setPosition(x*sX,y*sY);
            sprite.draw(batch);


        }
          sprite2.draw(batch);
      */
        //}
		//batch.end();
        //loger.log();
	}

	@Override
	public void resize(int width, int height) {

        viewPort=new Rectangle(0,0,width,height);

                //.setRect(0,0,width,height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
