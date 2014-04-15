package com.me.Bombs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.me.assetloader.AssetLoader;
import com.me.minebomber.Settings;

/**
 * Created by tretyakov on 11.04.2014.
 */
public abstract class AbsBomb extends AbstractBomb {
    protected boolean destroyed=false;
    boolean visible;
    AnimatedSprite sprite;
    private float elapsedTime = 0;

    public AbsBomb(BombProperty property, Vector2 pos, AnimatedSprite sprite) {
        super(property, pos);
        visible=true;
        this.sprite = sprite;
    }

    @Override
    public void Render(Batch batch) {
        if(destroyed)
            return;
        if(visible)
        {
            sprite.draw(batch);
        }
    }

}
