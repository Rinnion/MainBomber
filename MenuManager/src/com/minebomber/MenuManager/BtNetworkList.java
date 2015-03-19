package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.menuengine.GdxList;
import com.menuengine.JsonMenu;
import com.menuengine.MenuElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by alekseev on 08.10.2014.
 */
public class BtNetworkList implements IMenu {

    private Skin skin;
    private Map<String,MenuElement> elements;
    private Stage stage;
    private Table table;


    //private HashMap<String,Label> labelList;

    GdxList btViewList;

    public static final String MENU_NAME="mainmenu";


    public final String name;





    public BtNetworkList(String menuName,Map<String,MenuElement> list, Skin skin)
    {
        name=menuName;
        stage=new Stage();
        table = new Table();
        table.setFillParent(true);
        elements=list;
        this.skin=skin;
        //btViewList=new GdxList(skin);
        initialize();
        btViewList=(GdxList)list.get("btDevicesInfo").getObject();
    }

    @Override
    public String getName() {
        return name;
    }

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
        return btViewList.getSelectedItem();
    }

    public void AddItemToList(String item)
    {
        GdxList list= ((GdxList)elements.get("btDevicesInfo").getObject());


        list.Add(item);

        //table.va
    }

    public void ClearList()
    {
        GdxList list= ((GdxList)elements.get("btDevicesInfo").getObject());
        list.Clear();

    }


    private void initialize()
    {

                                         //table.debug();

        boolean inNewTable=false;
        Cell<Actor> curCell=null;
        Table tmpTable=table;

        for(MenuElement tmpElement:elements.values())
        {
            Actor tmpActor= JsonMenu.createElement(tmpElement, skin, MenuActions.Actions);




            if(tmpElement.newline==0) {
                tmpTable = new Table();
                table.add(tmpTable).row();
            }





            curCell=tmpTable.add(tmpActor);




            if(tmpElement.type== com.menuengine.MenuList.EN_TYPE_CAPTION) {

                    curCell.padBottom(tmpElement.padBottom);
                }
            else {
                if(tmpElement.type== com.menuengine.MenuList.EN_TYPE_LIST) {



                    curCell.height(300).pad(0, 0, 20, 0);


                    //curCell.right();
                }
                else {
                    curCell.size(tmpElement.width, tmpElement.height).padBottom(tmpElement.padBottom);
                }
            }

            //curCell.center();
                 //   curCell.expand();
            if(tmpElement.newline>0) {
                curCell.row();
            }





        }


        //table.add(btViewList.getActor()).height(150).pad(0,0,0,0);

        table.invalidateHierarchy();
        table.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight() );



        stage.addActor(table);






    }


}
