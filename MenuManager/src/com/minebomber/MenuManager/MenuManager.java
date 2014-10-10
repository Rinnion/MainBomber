package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
            playMenu.applyShowActions();
            changeStage(playMenu.getStage());
        }
        if(mainMenu.name.equals(menuName))
        {
            mainMenu.applyShowActions();
            changeStage(mainMenu.getStage());
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
        mainMenu.applyShowActions();
        playMenu=new MainMenu(jsonMenu.menu.get("play") ,mSkin);
        playMenu.applyShowActions();


        changeStage(mainMenu.getStage());



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
    }

}
