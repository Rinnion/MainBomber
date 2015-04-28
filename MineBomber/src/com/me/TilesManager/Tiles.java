package com.me.TilesManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.Map.MapManager;
import com.me.Map.MapProperty;
import com.me.Map.PixmapHelper;
import com.me.minebomber.Settings;

import java.util.HashMap;

/**
 * Created by alekseev on 09.09.2014.
 */
public class Tiles {
    public static HashMap<Integer,TileGroup> Info;
    private static HashMap<Integer,Tile> tiles;
    private static TextureAtlas tilesAtlas;

    public static void AddTile(Tile tile)
    {
        tiles.put(tile.id,tile);
    }

    public static Tile GetTile(Integer id)
    {
        return tiles.get(id);

    }

    public static void Initialize()
    {
        Info=new HashMap<Integer, TileGroup>();
        tiles=new HashMap<Integer, Tile>();
        LoadTexture();
    }




    public static TextureAtlas.AtlasRegion GetTileRegion(String name)
    {
        return tilesAtlas.findRegion(name);

    }

    private static void LoadTexture()
    {
                   tilesAtlas=new TextureAtlas(Gdx.files.internal(Settings.PAK_OBJECTS));
    }

    public static void UpdateTilesPixmap(int stepX,int stepY,MapProperty mapProperty)
    {

        int count=stepX*stepY;



        for(Tile tileInfo : tiles.values())
        {
            tileInfo.miniTile =new Pixmap[count];

            for(int iY=0;iY<stepY;iY++)
                for(int iX=0;iX<stepX;iX++)
                {
                    int index=iY*stepX+iX;

                    int startX=tileInfo.region.getRegionX()+iX* MapManager.rowW;//rowW;
                    int startY=tileInfo.region.getRegionY()+iY*MapManager.rowH;//rowH;

                    tileInfo.miniTile[index]=new Pixmap(MapManager.rowW,MapManager.rowH, Pixmap.Format.RGBA8888);
                    PixmapHelper.DrawMiniObject(tileInfo.miniTile[index], startX, startY);


                }
        }

    }

}
