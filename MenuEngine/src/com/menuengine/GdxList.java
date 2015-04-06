package com.menuengine;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

/**
 * Created by alekseev on 29.12.2014.
 */
public class GdxList {

      List list;
    ScrollPane scrollPane2;
    //String[] listEntries = {"This is a list entry1", "And another one1", "The meaning of life1", "Is hard to come by1",
      //      "This is a list entry2", "And another one2", "The meaning of life2", "Is hard to come by2", "This is a list entry3",
        //    "And another one3", "The meaning of life3", "Is hard to come by3", "This is a list entry4", "And another one4",
          //  "The meaning of life4", "Is hard to come by4", "This is a list entry5", "And another one5", "The meaning of life5",
            //"Is hard to come by5"};

    Array<String> arrayList=new Array<String>();

    public GdxList(Skin skin)
     {
         Gdx.app.log("GdxList","skin ");

         //List list = new List(new String[] {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"}, skin);
         list=new List(skin);
         list.setItems(arrayList);
         //list.setBounds(0, 0, 0, 0);
         //list.getSelection().setMultiple(true);
         //list.getSelection().setRequired(false);


        /* list.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 Gdx.app.log("ChangeListener","changed " + list.getSelection().toString());
             }
         });
         */


         //list.setStyle();
         scrollPane2 = new ScrollPane(list, skin);
         scrollPane2.setFlickScroll(false);

         //SplitPane splitPane = new SplitPane(scrollPane, scrollPane2, false, skin, "default-horizontal");





     }

     public void Add(String item)
     {
        //list.getItems().add(item);
         arrayList.add(item);
         list.setItems(arrayList);
     }


     public void Clear()
     {
       //list.getItems().clear();
         arrayList.clear();
         //list.clearItems();
     }

     public void UpdateList(String [] item)
     {
          //  list.setItems(item);
         //list.invalidate();
         //arrayList.ac

         //arrayList.addAll(item);
         //list.clearItems();
         Gdx.app.log("UpdateList","cleared");
         list.setItems(item);
             //list.getItems().add("hello");
        list.invalidate();

         scrollPane2.invalidate();

        //list.setFillParent(true);
                //setItem(item);
     }

    public String getSelectedItem()
    {
        return arrayList.get(list.getSelectedIndex());
    }

    public Actor getActor()
    {
          return scrollPane2;
    }




}
