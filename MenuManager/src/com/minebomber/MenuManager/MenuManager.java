package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.menuengine.JsonMenu;


/**
 * Created by alekseev on 09.10.2014.
 */
public class MenuManager {
    public static Stage stage;

    private static Skin mSkin;

    private static JsonMenu jsonMenu;

    private static MainMenu mainMenu;
    private static MainMenu playMenu;
    private static OrthographicCamera camera;

    private static AnimatedSprite animatedSprite;

    private static void changeStage(Stage newStage)
    {
        stage=newStage;
        applyCamera();
        Gdx.input.setInputProcessor(stage);
    }

    public static void changeMenu(String menuName)
    {
        if(playMenu.name.equals(menuName))
        {

            changeStage(playMenu.getStage());
            playMenu.applyShowActions();
        }
        if(mainMenu.name.equals(menuName))
        {

            changeStage(mainMenu.getStage());
            mainMenu.applyShowActions();
        }

    }

    public static void Initialize()
    {

        Assets.queueLoading();
        //Assets.update();
        Assets.manager.finishLoading();
        Assets.setMenuSkin();

        mSkin=Assets.menuSkin;


        setCamera(new MenuCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
        jsonMenu=jsonMenu.createMenu();

        mainMenu=new MainMenu(jsonMenu.menu.get(MainMenu.MENU_NAME) ,mSkin);

        playMenu=new MainMenu(jsonMenu.menu.get("play") ,mSkin);



        changeStage(mainMenu.getStage());
        animatedSprite=AnimatedSprite.Factory.CreateSnowmanSprite("");
        animatedSprite.setSize(animatedSprite.getWidth()*0.6f,animatedSprite.getHeight()*0.4f);
        //animatedSprite.setPosition(0,Gdx.graphics.getHeight()-animatedSprite.getHeight());
    }

    public static void applyCamera()
    {
        stage.getViewport().setCamera(camera);
    }

    public static void setCamera(OrthographicCamera curCamera)
    {
        camera=curCamera;
    }


    public static void Draw()
    {
        stage.act();

        stage.draw();
        Batch batch=stage.getBatch();
        batch.enableBlending();
        batch.begin();

        animatedSprite.setPosition(0,0);
        animatedSprite.draw(batch);
        for(int i=0;i<10;i++) {

            animatedSprite.setPosition(i*animatedSprite.getWidth(),20);
            animatedSprite.DrawSprite(batch);

            animatedSprite.setPosition(i*animatedSprite.getWidth()-10,0);

            animatedSprite.DrawSprite(batch);

        }
        batch.end();
    }

}
