package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.menuengine.JsonMenu;
import com.menuengine.MenuElement;
import com.menuengine.MenuList;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by alekseev on 09.10.2014.
 */
public class MenuManager {
    public static Stage stage;

    private static Skin mSkin;

    private static JsonMenu jsonMenu;

    private static IMenu mainMenu;
    private static IMenu playMenu;
    private static IMenu btMenu;
    private static IMenu hotSeat;


    private static OrthographicCamera camera;

    private static AnimatedSprite animatedSprite;

    private static IMenu menu;

    private static void changeStage(Stage newStage)
    {
        stage=newStage;
        applyCamera();
        Gdx.input.setInputProcessor(stage);
    }

    public static String GetTag()
    {
        return menu.GetTag();
    }

    public static void SetText(String name,String text)
    {
        menu.SetText(name,text);
    }

    public static String getPrevMenuName()
    {
          return jsonMenu.menu.get(menu.getName()).prevName;
    }


    public static void changeMenu(String menuName)
    {
        if(hotSeat.getName().equals(menuName))
        {


            changeStage(hotSeat.getStage());
            hotSeat.applyShowActions();
            menu=hotSeat;
        }


        if(playMenu.getName().equals(menuName))
        {


            changeStage(playMenu.getStage());
            playMenu.applyShowActions();
            menu=playMenu;
        }
        if(mainMenu.getName().equals(menuName))
        {

            changeStage(mainMenu.getStage());
            mainMenu.applyShowActions();
            menu=mainMenu;
        }

        if(btMenu.getName().equals(menuName))
        {
            changeStage(btMenu.getStage());
            btMenu.applyShowActions();
            menu=btMenu;
        }

    }

    private static Map<String,MenuElement> getElementNameList(String menuName)
    {
        MenuList menuList= jsonMenu.menu.get(menuName);

        Map<String,MenuElement> retMap=new LinkedHashMap<String, MenuElement>();

        for(MenuElement tmpElement: menuList.elements)
        {
            retMap.put(tmpElement.name,tmpElement);

        }

        return  retMap;
    }


    public static void ClearList()
    {
        ((BtNetworkList)btMenu).ClearList();
    }

    public static void AddTotList(String item)

    {
        ((BtNetworkList)btMenu).AddItemToList(item);


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

        mainMenu=new MainMenu("mainmenu",getElementNameList("mainmenu") ,mSkin);

        playMenu=new MainMenu("play",getElementNameList("play") ,mSkin);

        btMenu=new BtNetworkList("cmdJoin",getElementNameList("cmdJoin"),mSkin);

        hotSeat=new MainMenu("cmdHotSeat",getElementNameList("cmdHotSeat") ,mSkin);


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
        //btMenu.UpdateList(new String[]{"hello"});
        stage.act(Gdx.graphics.getDeltaTime());


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
