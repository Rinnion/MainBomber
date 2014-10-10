package com.menuengine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Json;


import java.util.HashMap;

/**
 * Created by alekseev on 01.10.2014.
 */
public class JsonMenu {

      public HashMap<String,MenuList> menu;


        public static JsonMenu createMenu()
        {
            Json json=new Json();
            JsonMenu menu=(JsonMenu)json.fromJson(Object.class, Gdx.files.internal("data/ui/menuJson.json"));
            return menu;
        }


        public static HashMap<String,MenuElement> getElementMap(MenuList menuList)
        {
            HashMap<String,MenuElement> menuElementMap=new HashMap<String, MenuElement>();

            for(MenuElement menuElement:menuList.elements)
            {
                menuElementMap.put(menuElement.name,menuElement);
            }

            return menuElementMap;
        }

        public static Actor createElement(MenuElement menuElement,Skin skin, MenuCallback menuCallback)
        {
            Actor retObj=null;

           switch (menuElement.type)
           {
               case MenuList.EN_TYPE_CAPTION:
                   Label label=new Label(menuElement.caption,skin);
                   retObj=label;
                   break;

               case MenuList.EN_TYPE_BUTTON:
                   TextButton  textButton=new TextButton(menuElement.caption,skin);
                   textButton.addListener(new ButtonListener(menuElement,menuCallback));
                    retObj=textButton;
                    break;
               case MenuList.EN_TYPE_TEXTBOX:
                   TextField textField=new TextField(menuElement.caption,skin);
                   textField.addListener(new TextListener(menuElement));
                   retObj=textField;
                    break;
               case MenuList.EN_TYPE_CHECK:

                   final CheckBox  checkBox=new CheckBox(menuElement.caption,skin);
                   checkBox.addListener(new CheckBoxListener(menuElement,checkBox,menuCallback));
                   retObj=checkBox;
                   break;
           }

            return retObj;
        }



}
