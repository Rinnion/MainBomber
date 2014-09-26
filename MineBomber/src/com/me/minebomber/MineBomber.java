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
import com.me.controlers.ActionController;
import com.me.controlers.GameObjectController;
import com.me.logger.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


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
    float scrW;
    float scrH;
    boolean fullScreen=true;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Rectangle viewPort;
    private Timer timer;
    private long sheduleDtStart;
    private long sheduleDtBomb;
    private long sheduleDtPlayer;
    private long sheduleDtMap;


    @Override
	public void create() {

        Log.d("create");
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


        //camera.position.set( 1/2,(h/w)/2,0);
        //camera.position.set( 1.0f/2f,(h/w)/2,0);      // camera.setToOrtho(true);

        loger = new FPSLogger();
        batch = new SpriteBatch();

        //texture = new Texture(Gdx.files.internal("data/FullGraphic.png"));
        //texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        //TextureRegion region = new TextureRegion(texture, 0, 0, 15, 15);
        // TextureRegion region2 = new TextureRegion(texture, 0, 16, 15, 15);

        //sprite = new Sprite(region);
        //sprite2 = new Sprite(region2);
        //sprite.setSize(8,8);
        //sprite2.setSize(8,8);


        // sY = (sY * sprite.getHeight()) / sprite.getWidth();
        //mX = 1f / sX;
        //mY = 1f / sY;


        //sprite.setSize(sX, sY);
        //sprite2.setSize(sX, sY);
        //sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        //sprite.setPosition(mX, mY);
        //sprite.setPosition(0, 0);
        //sprite2.setPosition(0, 0);


        timer = new Timer("logic timer");


        timer.scheduleAtFixedRate(new TimerTask() {
                                      @Override
                                      public void run() {
                                          //try {
                                          sheduleDtStart = Calendar.getInstance().getTimeInMillis();
                                          //BombController.Calculate(sheduleDtStart);
                                          GameObjectController.calculate(sheduleDtStart);

                                          sheduleDtBomb = Calendar.getInstance().getTimeInMillis();
                                          PlayerController.Calculate(sheduleDtStart);
                                          sheduleDtPlayer = Calendar.getInstance().getTimeInMillis();
                                          MapManager.Calculate(sheduleDtStart);
                                          sheduleDtMap = Calendar.getInstance().getTimeInMillis();
                                          ActionController.Calculate(sheduleDtStart);

                                          long diff = sheduleDtMap - sheduleDtStart;
                                          if (diff > 20)
                                              Log.w(String.format("########## LOGIC TIME > 20 (%s) !!! ##########", diff));
                                          // }
                                          //catch (Exception _ex)
                                          //{
                                          //   Log.e("Stack: " + _ex.getStackTrace() + "Message: " + _ex.getMessage() );


                                          //}
                                      }
                                  }, 0, 50
        );

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

        camera.update();

        Gdx.gl.glViewport((int)viewPort.getX() ,(int)viewPort.getY(),(int)viewPort.getWidth(),(int)viewPort.getHeight());

        //Gdx.gl.glViewport((int)viewPort.getX() ,(int)viewPort.getY(),(int)viewPort.getWidth(),(int)viewPort.getHeight());

        //Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //MapManager.mapRenderer.getSpriteBatch().getProjectionMatrix().setToOrtho2D(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        batch.setProjectionMatrix(camera.combined);

        long dtStart = Calendar.getInstance().getTimeInMillis(); //new Date().getTime();
        BeginDrawTime=dtStart;

        batch.begin();
        batch.disableBlending();
        MapManager.Render(batch);
        batch.enableBlending();

        //Gdx.gl.glEnable(Gdx.gl.GL_BLEND); Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        //Gdx.gl20.glColorMask(true, true, true, false);
        //batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA );



        //Gdx.gl.glBlendEquation(Gdx.gl20.GL_FUNC_SUBTRACT);

        GameObjectController.Render(batch);
        PlayerController.Render(batch);
        ParticleManager.Draw(batch,Gdx.graphics.getDeltaTime());
        textZoom.Draw(batch);
        TextManager.Draw(batch);
        batch.end();

        long dtEnd = Calendar.getInstance().getTimeInMillis();
        long dtResMap=sheduleDtMap-sheduleDtStart;
        if(dtResMap<0)
            dtResMap=0;
        textZoom.SetText(Long.toString(dtEnd-dtStart) + " " + Long.toString(sheduleDtBomb-sheduleDtStart)+ " " + Long.toString(sheduleDtPlayer -sheduleDtStart)+ " " + Long.toString(dtResMap));

        PlayerController.AfterBatch(camera.combined);

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
