package com.menuengine;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by alekseev on 07.10.2014.
 */
public class MenuElement {
    public String caption;
    public String name;
    public int type;

    public float height;
    public float width;

    public float padBottom;
    public int newline;

    Object obj;


    public Object getObject()
    {
        return obj;
    }

    public void SetControlObject(Object obj)
    {
        this.obj=obj;
    }

    public MenuElement()
    {

    }
    public MenuElement(String name,String caption,int type)
    {
        height=20;
        width=40;
        padBottom=20;
        newline=0;
        this.caption=caption;
        this.name=name;
        this.type=type;
    }

}
