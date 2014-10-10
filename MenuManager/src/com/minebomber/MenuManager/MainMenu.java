package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
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
        table.setFillParent(true);
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

        //table.addAction(Actions.sequence(Actions.timeScale(100,Actions.delay(100))  ));

       /* table.addAction(Actions.forever(Actions.sequence(Actions.moveBy(50, 0, 2), Actions.moveBy(-50, 0, 2), Actions.run(new Runnable() {
            public void run () {
                table.setZIndex(0);
            }
        }))));
        */
        /*table.getStage().getViewport().setCamera(new MenuCamera(800,600));
        table.invalidateHierarchy();
        table.setFillParent(true);

        for(Cell<Actor> cactor : table.getCells())
        {
            Actor tmpActor= cactor.getActor();

            Vector2 tmpPos=new Vector2(cactor.getActorX(),cactor.getActorY());




                    tmpActor.getStage().stageToScreenCoordinates(tmpPos);

            tmpPos= tmpActor.stageToLocalCoordinates(tmpPos);

            tmpActor.addAction(Actions.sequence(
             Actions.hide(),
             Actions.moveTo(-tmpActor.getWidth(),tmpActor.getHeight(),0),
             Actions.show(),
             Actions.moveTo(tmpPos.x,tmpPos.y,5)
             ));

        }
       */
    }

    private void initialize()
    {


        for(MenuElement tmpElement:elements)
        {
            Actor tmpActor= JsonMenu.createElement(tmpElement, skin, MenuActions.Actions);





            if(tmpElement.type==MenuList.EN_TYPE_CAPTION)
                table.add(tmpActor).padBottom(tmpElement.padBottom).row();
            else
               table.add(tmpActor).size(tmpElement.width,tmpElement.height).padBottom(tmpElement.padBottom).row();



        }




        table.invalidateHierarchy();
        table.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );



        stage.addActor(table);






    }


}
