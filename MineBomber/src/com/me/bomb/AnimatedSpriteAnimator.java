package com.me.bomb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.me.Utility.RecyclableArray;
import com.me.Utility.RecyclableObject;

/**
* Created by tretyakov on 14.04.2014.
*/
public class AnimatedSpriteAnimator extends RecyclableObject {

    private AnimatedSprite animatedSprite = null;
    private float elapsedTime;

    public AnimatedSpriteAnimator(RecyclableArray array) {
        super(array);
    }

    public AnimatedSpriteAnimator update(AnimatedSprite animatedSprite){
        this.animatedSprite = animatedSprite;
        return this;
    }

    public void draw(Batch batch, float x, float y) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        animatedSprite.draw(batch, x, y, elapsedTime);
    }

}
