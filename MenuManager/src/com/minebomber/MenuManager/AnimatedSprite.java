package com.minebomber.MenuManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;


/**
* Created by tretyakov on 14.04.2014.
*/
public class AnimatedSprite extends Sprite {

    public static final float FRAME_DURATION = 0.24f;

    private Animation animSprite;
    private float elapsedTime;

    public AnimatedSprite(Texture texture, Animation animSprite) {
        super(texture);
        this.animSprite = animSprite;
    }

    @Override
    public void draw(Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        setRegion(animSprite.getKeyFrame(elapsedTime, true));
        super.draw(batch);
    }

    @Override
    public void draw(Batch batch,float alpha) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        setRegion(animSprite.getKeyFrame(elapsedTime, true));
        super.draw(batch,alpha);
    }

    public void DrawSprite(Batch batch)
    {
        super.draw(batch);
    }

    public static class Factory{
        public static AnimatedSprite CreateSnowmanSprite(String name) {

            TextureAtlas dynamiteTex = Assets.menuTexture;

            Array<TextureAtlas.AtlasRegion> region = dynamiteTex.findRegions ("menu_snow");
            Animation animSprite = new Animation(FRAME_DURATION, region);

            return new AnimatedSprite(region.get(0).getTexture(), animSprite);
        }

    }
}
