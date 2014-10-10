package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static AssetManager manager = new AssetManager();
	public static Skin menuSkin;

	public static void queueLoading() {
		manager.load("data/ui/menuSkin.pack", TextureAtlas.class);
	}
	
	/** Initiate menu Skin **/
	public static void setMenuSkin() {
		if (menuSkin == null)
			menuSkin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"),
					manager.get("data/ui/menuSkin.pack",
							TextureAtlas.class));
	}

	public static boolean update() {
		return manager.update();
	}

}
