package com.me.Bombs;

import com.badlogic.gdx.Gdx;
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
public class AnimatedSpriteAnimator extends RecyclableObject {

    private AnimatedSprite animatedSprite = null;
    private float elapsedTime;

    public AnimatedSpriteAnimator update(AnimatedSprite animatedSprite){
        this.animatedSprite = animatedSprite;
        return this;
    }

    public void draw(Batch batch, float x, float y) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        animatedSprite.draw(batch, x, y, elapsedTime);
    }

    public static class Factory implements RecyclableArray.Factory<AnimatedSpriteAnimator> {

        @Override
        public AnimatedSpriteAnimator newItem() {
            return new AnimatedSpriteAnimator();
        }
    }
}
