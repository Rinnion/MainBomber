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
public class AnimatedSprite  {

    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    public static final float FRAME_DURATION = 0.24f;
    public static final float FRAME_DURATION_T = 1.0f;

    public static final String TREASURE_BIG_CHEST = "box";

    private com.badlogic.gdx.graphics.g2d.Animation animSprite;
    private com.badlogic.gdx.graphics.g2d.Sprite sprite;
    private float oX;
    private float oY;

    public AnimatedSprite(Texture texture, int width, int height, Animation animation){
        sprite = new Sprite(texture, width, height);
        animSprite = animation;
    }

    public void draw(Batch batch, float elapsedTime) {
        sprite.setRegion(animSprite.getKeyFrame(elapsedTime, true));
        sprite.draw(batch);
    }

    public void draw(Batch batch, float x, float y, float elapsedTime) {
        sprite.setRegion(animSprite.getKeyFrame(elapsedTime, true));
        sprite.setPosition(x - oX, y - oY);
        sprite.draw(batch);
    }

    public void setOrigin(float X, float Y){
        oX = X;
        oY = Y;
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

            AnimatedSprite animatedSprite = new AnimatedSprite(region.get(0).getTexture(), WIDTH, HEIGHT, animSprite);
            animatedSprite.setOrigin(4,4);

            return animatedSprite;
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

            AnimatedSprite animatedSprite = new AnimatedSprite(region.get(0).getTexture(), WIDTH, HEIGHT, animSprite);
            animatedSprite.setOrigin(4,4);
            return animatedSprite;
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

            AnimatedSprite animatedSprite = new AnimatedSprite(region.get(0).getTexture(), 10, 10, animSprite);
            animatedSprite.setOrigin(6,6);
            return animatedSprite;
        }
    }
}
