package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.menuengine.JsonMenu;
import com.menuengine.MenuElement;
import com.menuengine.MenuList;

import java.util.Map;


/**
 * Created by alekseev on 08.10.2014.
 */
public class MainMenu implements IMenu {

    public final String name;
    private Skin skin;
    private Map<String,MenuElement> elements;
    private Stage stage;
    private Table table;


    public MainMenu(String menuName,Map<String,MenuElement> list,Skin skin)
    {
        name=menuName;
        stage=new Stage();
        table = new Table();
        table.setFillParent(true);
        elements=list;
        this.skin=skin;

        initialize();
    }


    @Override
    public String getName(){return name;}


    @Override
    public Stage getStage()
    {
        return stage;
    }

    @Override
    public void applyShowActions()
    {
        table.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.fadeIn(1, Interpolation.exp5)
        ));
    }

    @Override
    public void SetText(String name, String text) {
        if(!elements.containsKey(name))
            return;
        Object obj=elements.get(name).getObject();

        if(obj instanceof Label)
        ((Label)elements.get(name).getObject()).setText(text);
    }

    @Override
    public String GetTag() {
        return null;
    }

    private void initialize() {


        for (MenuElement tmpElement : elements.values()) {
            Actor tmpActor = JsonMenu.createElement(tmpElement, skin, MenuActions.Actions);
            if (tmpElement.type == MenuList.EN_TYPE_CAPTION) {
                table.add(tmpActor).padBottom(tmpElement.padBottom).row();

            } else
                table.add(tmpActor).size(tmpElement.width, tmpElement.height).padBottom(tmpElement.padBottom).row();
        }

        table.invalidateHierarchy();
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }
}
