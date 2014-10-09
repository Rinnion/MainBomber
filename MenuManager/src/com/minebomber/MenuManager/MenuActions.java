package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.menuengine.MenuCallback;
import com.menuengine.MenuElement;

/**
 * Created by alekseev on 09.10.2014.
 */
public class MenuActions implements MenuCallback {
    public static MenuActions Actions=new MenuActions();
    private static MenuAction callback=null;

    public static void SetCallback(MenuAction action)
    {
        callback=action;
    }

    @Override
    public void checked(MenuElement name, boolean value) {

    }

    @Override
    public void buttonDown(MenuElement element) {
       if(element.name.equals("play"))
       {
           MenuManager.changeMenu(element.name);
           return;
       }

        if(element.name.equals("mainmenu"))
        {
            MenuManager.changeMenu(element.name);
            return;
        }

        if(element.name.equals("exit"))
        {
            Gdx.app.exit();
            return;
        }

        if(callback!=null)
            callback.buttonDown(element.name);

    }
}
