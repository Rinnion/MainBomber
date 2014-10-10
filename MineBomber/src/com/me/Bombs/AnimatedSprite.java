package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
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
    public static final float FRAME_DURATION_T = 1.0f;

    public static final String TREASURE_BIG_CHEST = "box";

    private com.badlogic.gdx.graphics.g2d.Animation animSprite;
    private float elapsedTime;

    public AnimatedSprite(Texture texture, int width, int height, Animation animSprite) {


        super(texture, width, height);

        this.animSprite = animSprite;
    }

    @Override
    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        //Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        //batch.setBlendFunction(Gdx.gl.GL_SRC_COLOR, Gdx.gl.GL_DST_ALPHA);

        setRegion(animSprite.getKeyFrame(elapsedTime, true));
        //Texture texture=getTexture();
        //
        //  float alpha=0.5f;

        super.draw(batch);
    }

    @Override
    public void draw(Batch batch,float alpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        //Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        //batch.setBlendFunction(Gdx.gl.GL_SRC_COLOR, Gdx.gl.GL_DST_ALPHA);

        setRegion(animSprite.getKeyFrame(elapsedTime, true));
        //Texture texture=getTexture();
        //
        //  float alpha=0.5f;

        super.draw(batch,alpha);
    }

    public void SetPlayMode(Animation.PlayMode mode)
    {
        this.animSprite.setPlayMode(mode);
    }

    public static class Factory{
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
