package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;

/**
* Created by tretyakov on 14.04.2014.
*/
public class AnimatedSprite extends RecyclableObject {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final float FRAME_DURATION = 0.24f;
    public static final float FRAME_DURATION_T = 1.0f;

    public static final String TREASURE_BIG_CHEST = "box";

    private com.badlogic.gdx.graphics.g2d.Animation animSprite;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;
    private float elapsedTime;

    public AnimatedSprite update(Sprite sprite, Animation animSprite) {
        this.animSprite = animSprite;
        this.sprite = sprite;
        return this;
    }

    public AnimatedSprite() {
        sprite = null;
        animSprite = null;
    }

    public AnimatedSprite(Texture texture, int width, int height, Animation animation){
        sprite = new Sprite(texture, width, height);
        animSprite = animation;
    }

    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        sprite.setRegion(animSprite.getKeyFrame(elapsedTime, true));
        sprite.draw(batch);
    }

    public void SetPlayMode(Animation.PlayMode mode)
    {
        this.animSprite.setPlayMode(mode);
    }

    public static class Factory implements RecyclableArray.Factory<AnimatedSprite> {

        @Override
        public AnimatedSprite newItem() {
            return new AnimatedSprite();
        }
    }

    public static class FactoryMethos {
        public static AnimatedSprite CreateBomb(String bombName) {

            TextureAtlas dynamiteTex = AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);

            Array<TextureAtlas.AtlasRegion> region = dynamiteTex.findRegions (bombName);
            for(TextureAtlas.AtlasRegion tmpRegion : region)
            {
                tmpRegion.flip(false,true);
            }
            Animation animSprite = new Animation(FRAME_DURATION, region);

            return new AnimatedSprite(region.get(0).getTexture(), WIDTH, HEIGHT, animSprite);
        }

        public static AnimatedSprite CreateTreasure(String treasureName) {
            //FIXME: loading treasure image
            TextureAtlas dynamiteTex = AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);

            Array<TextureAtlas.AtlasRegion> region = dynamiteTex.findRegions (treasureName);
            for(TextureAtlas.AtlasRegion tmpRegion : region)
            {
                tmpRegion.flip(false,true);
            }
            Animation animSprite = new Animation(FRAME_DURATION_T, region, Animation.PlayMode.LOOP_PINGPONG);

            return new AnimatedSprite(region.get(0).getTexture(), WIDTH, HEIGHT, animSprite);
        }



        public static AnimatedSprite CreatePlayer(String playerSkin)
        {
            TextureAtlas playerTex = AssetLoader.GetAtlas(Settings.PLAYER_SKIN);

            Array<TextureAtlas.AtlasRegion> region = playerTex.findRegions ("player_brake");
            for(TextureAtlas.AtlasRegion tmpRegion : region)
            {
                tmpRegion.flip(false,true);
            }
            Animation animSprite = new Animation(0.15f, region);

            return new AnimatedSprite(region.get(0).getTexture(), 10, 10, animSprite);
        }
    }
}
