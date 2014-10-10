package com.minebomber.MenuManager;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuCamera extends OrthographicCamera{

	public MenuCamera(int width, int height){
		super(width, height);
		this.position.x=width/2;
		this.position.y=height/2;
        //this.setToOrtho(true,width,height);
	}
}
