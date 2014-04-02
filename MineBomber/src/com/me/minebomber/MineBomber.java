package com.me.minebomber;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.me.Bombs.BombPlaser;
import com.me.Map.MapManager;
import com.me.Particles.ParticleManager;
import com.me.Players.PlayerController;

import javax.xml.crypto.Data;
import java.util.Date;


public class MineBomber implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
    private Sprite sprite2;

    private Rectangle viewPort;

    float sX=0.008f;
    float sY=0.008f;

    float mX;
    float mY;

    FPSLogger loger;

    float scrW;
    float scrH;

    boolean fullScreen=false;

    @Override
	public void create() {
        Initializer.Initialize();

        scrW = MapManager.scrW; //Gdx.graphics.getWidth();
        scrH =MapManager.scrH ; //Gdx.graphics.getHeight();


		camera = new OrthographicCamera(scrW, scrH);
        camera.setToOrtho(true,scrW,scrH);

        if(fullScreen==false)
            viewPort=new Rectangle( (int)((Gdx.graphics.getWidth() -scrW)/2f),(int)((Gdx.graphics.getHeight()-scrH)/2f) ,(int)scrW,(int)scrH);
        else
            viewPort=new Rectangle( 0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        MapManager.SetView(camera);
        MapManager.Refresh();


        //camera.position.set( 1/2,(h/w)/2,0);
        //camera.position.set( 1.0f/2f,(h/w)/2,0);      // camera.setToOrtho(true);

        loger=new FPSLogger();
        batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("data/FullGraphic.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		TextureRegion region = new TextureRegion(texture, 0, 0, 15, 15);
        TextureRegion region2 = new TextureRegion(texture, 0, 16, 15, 15);

        sprite = new Sprite(region);
        sprite2=new Sprite(region2);
        //sprite.setSize(8,8);
        //sprite2.setSize(8,8);




        sY= (sY*sprite.getHeight() )/ sprite.getWidth();
        mX=1f/sX;
        mY=1f/sY;


		sprite.setSize(sX, sY);
        sprite2.setSize(sX, sY);
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setPosition(mX, mY);
        sprite.setPosition(0,0);
        sprite2.setPosition(0,0);




	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render() {

		Gdx.gl.glClearColor(1, 0, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        camera.update();



        Gdx.gl.glViewport((int)viewPort.getX() ,(int)viewPort.getY(),(int)viewPort.getWidth(),(int)viewPort.getHeight());

        //Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

      //  MapManager.mapRenderer.getSpriteBatch().getProjectionMatrix().setToOrtho2D(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        batch.setProjectionMatrix(camera.combined);

        long dtStart = new Date().getTime();

        batch.begin();
        MapManager.Render(batch);
        TextOut.Draw(batch,0,0);
        BombPlaser.Draw(batch);
        ParticleManager.Draw(batch,Gdx.graphics.getDeltaTime());
        batch.end();

        long dtEnd = new Date().getTime();

        TextOut.SetText(Long.toString(dtEnd-dtStart));

        PlayerController.AfterBatch(camera.combined);




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
        loger.log();
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
