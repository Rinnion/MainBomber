package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.menuengine.JsonMenu;
import com.menuengine.MenuElement;
import com.menuengine.MenuList;

import javax.swing.*;
import java.util.List;
import java.util.Map;


/**
 * Created by alekseev on 08.10.2014.
 */
public class MainMenu {

    private Skin skin;
    private List<MenuElement> elements;
    private Stage stage;
    private Table table;


    public static final String MENU_NAME="mainmenu";


    public final String name;


    public MainMenu(MenuList list,Skin skin)
    {
        name=list.name;
        stage=new Stage();
        table = new Table();
        elements=list.elements;
        this.skin=skin;
        initialize();
    }

    public Stage getStage()
    {
        return stage;
    }

    public void applyShowActions()
    {
        table.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(1, Interpolation.exp5)
        ));

    }

    private void initialize()
    {
        for(MenuElement tmpElement:elements)
        {
            if(tmpElement.type==MenuList.EN_TYPE_CAPTION)
                table.add(JsonMenu.createElement(tmpElement,skin,MenuActions.Actions)).padBottom(tmpElement.padBottom).row();
            else
                table.add(JsonMenu.createElement(tmpElement,skin,MenuActions.Actions)).size(tmpElement.width,tmpElement.height).padBottom(tmpElement.padBottom).row();
        }


        table.setFillParent(true);

        table.invalidateHierarchy();
        table.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );

        stage.addActor(table);

    }


}
