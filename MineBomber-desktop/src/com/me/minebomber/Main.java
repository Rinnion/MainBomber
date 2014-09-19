package com.me.minebomber;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MineBomber";
		//cfg.useGL20 = true;
         // cfg.useGL30=true;

		cfg.width = 1280;
		cfg.height = 800;
        cfg.vSyncEnabled=true;
         //cfg.samples=8;
		
		new LwjglApplication(new MineBomber(), cfg);
	}
}
