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
    Animation animSprite;
    Sprite sprite;
    private float elapsedTime = 0;

    public AbsBomb(BombProperty property, Vector2 pos) {
        super(property, pos);
        visible=true;

        TextureAtlas dynamiteTex= AssetLoader.GetAtlas(Settings.BOMB_DYNAMITE);
        Array<TextureAtlas.AtlasRegion> region = dynamiteTex.findRegions ("dst_bomb");
        for(TextureAtlas.AtlasRegion tmpRegion : region)
        {
            tmpRegion.flip(false,true);
        }
        animSprite=new Animation(0.24f,region);
        sprite=new Sprite(region.get(0).getTexture());
        sprite.setSize(8,8);
    }

    @Override
    public void Render(Batch batch) {
        if(destroyed)
            return;
        if(visible)
        {
            elapsedTime += Gdx.graphics.getDeltaTime();
            sprite.setRegion(animSprite.getKeyFrame(elapsedTime, true));
            sprite.draw(batch);


        }
    }
}
