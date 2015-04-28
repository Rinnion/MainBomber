package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static AssetManager manager = new AssetManager();
	public static Skin menuSkin;

	public static TextureAtlas menuTexture;

	public static void queueLoading() {
        manager.load("data/ui/menuSkin.pack", TextureAtlas.class);
        manager.load("data/img/snow_texture.txt", TextureAtlas.class);
		Assets.manager.finishLoading();

		if (menuSkin == null)
			menuSkin = new Skin(Gdx.files.internal("data/ui/menuSkin.json"), manager.get("data/ui/menuSkin.pack", TextureAtlas.class));

		if (menuTexture == null) menuTexture = manager.get("data/img/snow_texture.txt", TextureAtlas.class);
	}

	public static void queueUnloading() {
		menuSkin.dispose();
		menuTexture.dispose();
		menuSkin = null;
		menuTexture = null;
		manager.unload("data/ui/menuSkin.pack");
		manager.unload("data/img/snow_texture.txt");
		manager.finishLoading();
	}

}
