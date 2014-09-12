package com.me.TilesManager;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Map;

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

        if(region==null) throw  new NullPointerException("Error load Tiles TextureAtlas Tile()->TextureRegion is null");


        this.region=new TextureRegion(region);
         this.group=group;
        //this.region=new TextureAtlas.AtlasRegion(region);
    }

}
