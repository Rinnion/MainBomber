package com.me.Map.TiledMapLoader;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.me.Map.IMap;
import com.me.Map.MapInfo;
import com.me.Map.MapProperty;
import com.me.TilesManager.Tile;
import com.me.TilesManager.TileGroup;
import com.me.TilesManager.Tiles;
import com.me.logger.Log;

/**
 * Created by alekseev on 10.09.2014.
 */
public class Loader implements IMap {

    private TiledMap tiledMap;


    private static FrameBuffer foreGroundBuffer;
    private Texture mTextureForeGround;
    private Sprite  mSpriteForeground;

    private final String  LAYER_foreGround="foreground";



    private MapProperty mapProperty;

    private void loadMapProperty()
    {
        mapProperty=new MapProperty();

        int mapH=Integer.parseInt(tiledMap.getProperties().get("height").toString());
        int mapW=Integer.parseInt(tiledMap.getProperties().get("width").toString());
        int tileheight=Integer.parseInt(tiledMap.getProperties().get("tileheight").toString());
        int tilewidth=Integer.parseInt(tiledMap.getProperties().get("tilewidth").toString());

        mapProperty.mapH=mapH;
        mapProperty.mapW=mapW;

        mapProperty.tileHeight =tileheight;
        mapProperty.tileWidth=tilewidth;

        mapProperty.width=mapW*tilewidth;
        mapProperty.height=mapH*tileheight;

    }

    private void loadMap()
    {
        MapLayers layers= tiledMap.getLayers();
        TiledMapTileLayer tilesLayer=(TiledMapTileLayer)layers.get(LAYER_foreGround);

        mapProperty.foreGroundMap=new Tile[mapProperty.mapH*mapProperty.mapW];

        int colCount=mapProperty.mapW;
        int rowCount=mapProperty.mapH;

        Tile tileInfo;
        for(int col=0;col<colCount;col++)
            for(int row=0;row<rowCount;row++) {
                TiledMapTileLayer.Cell cell = tilesLayer.getCell(col, row);
                int id=0;
                try {
                    id = Integer.parseInt((String) cell.getTile().getProperties().get("id"));
                }
                catch (Exception _ex)
                {
                    Log.e(_ex.getMessage());

                }

                tileInfo= Tiles.GetTile(id);
                mapProperty.foreGroundMap[row*colCount+col]=tileInfo;
            }


    }


    public Loader(String filename)
    {
        tiledMap=new TmxMapLoader().load(filename); //"data/tiles.tmx"

        loadMapProperty();
        loadMap();
    }



    @Override
    public void Initialize() {

    }

    @Override
    public MapProperty GetMap() {
        return mapProperty;
    }


}
