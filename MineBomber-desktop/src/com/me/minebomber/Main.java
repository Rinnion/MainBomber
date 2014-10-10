package com.me.minebomber;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.me.Utility.RecyclableArray;
import com.me.controlers.actions.PutBombAction;

public class Main {
	public static void main(String[] args) {

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MineBomber";
		//cfg.useGL20 = true;
         // cfg.useGL30=true;

		cfg.width = 800;
		cfg.height = 600;
        cfg.vSyncEnabled=true;
         //cfg.samples=8;
		
		new LwjglApplication(new MineBomber(), cfg);
	}
}
