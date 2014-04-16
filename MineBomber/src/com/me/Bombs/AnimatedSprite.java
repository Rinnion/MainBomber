package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;

/**
* Created by tretyakov on 14.04.2014.
*/
public class AnimatedSprite extends Sprite {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final float FRAME_DURATION = 0.24f;

    private com.badlogic.gdx.graphics.g2d.Animation animSprite;
    private float elapsedTime;

    public AnimatedSprite(Texture texture, int width, int height, Animation animSprite) {
        super(texture, width, height);
        this.animSprite = animSprite;
    }

    @Override
    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        setRegion(animSprite.getKeyFrame(elapsedTime, true));
        super.draw(batch);
    }

    public static class Factory{
        public static AnimatedSprite Create(String bombName) {
            TextureAtlas dynamiteTex= AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);
            Array<TextureAtlas.AtlasRegion> region = dynamiteTex.findRegions (bombName);
            for(TextureAtlas.AtlasRegion tmpRegion : region)
            {
                tmpRegion.flip(false,true);
            }
            Animation animSprite = new Animation(FRAME_DURATION, region);
            return new AnimatedSprite(region.get(0).getTexture(), WIDTH, HEIGHT, animSprite);
        }
    }
}
