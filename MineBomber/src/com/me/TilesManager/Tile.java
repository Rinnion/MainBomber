package com.me.TilesManager;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by alekseev on 09.09.2014.
 */
public class Tile {

    public int id;
    public TextureRegion region;
    public TileGroup group;

    public Pixmap[] miniTile;

    public Tile(int id,TextureRegion region,TileGroup group)
    {
        this.id=id;

        if (region == null)
            throw new NullPointerException("Error load Tiles TextureAtlas tile()->TextureRegion is null");


        this.region=new TextureRegion(region);
         this.group=group;
        //this.region=new TextureAtlas.AtlasRegion(region);
    }

}
