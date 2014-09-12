package com.me.Map;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.Vector2IDamage;
import com.me.Map.TiledMapLoader.Loader;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.Players.AbstractPlayer;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.TilesManager.Tile;
import com.me.TilesManager.TileGroup;
import com.me.TilesManager.Tiles;
import com.me.controlers.GameObjectController;
import com.me.minebomber.AbstractGameObject;
import com.me.minebomber.DrawManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 17.03.14
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class MapManager {

    public static final int FIELD_CAPACITY = 16;
    //private static FrameBuffer backGroundBuffer;
    private static FrameBuffer foreGroundBuffer;

    private static Texture mTextureForeground;
    //private static Texture mTextureBackground;

    //private static Sprite  mSpriteBackground;
     private static Sprite  mSpriteForeground;

    static IMap mMap;
    public static MapProperty mapProperty;
    //private static TiledMap mMap;
    //private static OrthogonalTiledMapRenderer mapRenderer;



    public static int maxCel;
    public static int maxRow;
    /*
    public static int scrW;
    public static int scrH;*/

    public static int rowH=2;
    public static int rowW=2;

    /*
    static int[] backGroundIndex=new int[]{0};
    static int[] foreGroundIndex=new int[]{1};
    static int[] objectsIndex=new int[]{3};
    */

    //информация по тайлам
    //public static HashMap<Integer,TilesInfo> mapTiles;
    //информация об обьектах на карте
    public static MapInfo[] mapInfo;

    public static int[] fieldDamage;
    public static int[] fieldDigDamage;

    public static ArrayList<AbstractGameObject>[] fieldObjects;

    public static void RedrawPixmap(int mapIndex)
    {

        MapInfo info=mapInfo[mapIndex];
        //TilesInfo tile=  //mapTiles.get(info.GetId());
        Tile tile;
        try {
            tile = Tiles.GetTile(info.GetId());
            PixmapHelper.DrawPixmap(tile.miniTile[info.GetPixmapIndex()], info.GetX(), info.GetY());
        }
        catch (Exception _ex)
        {
            tile=Tiles.GetTile(0);

        }


    }

    public static void BindForeground()
    {
        PixmapHelper.Bind(mTextureForeground);
    }


     public static void Initialize()
    {
        //mMap=new TmxMapLoader().load("data/tiles.tmx");

        mMap=new Loader("data/maps/map001.tmx");
        mMap.Initialize();
        mapProperty=mMap.GetMap();

        //backGroundBuffer=new FrameBuffer(Pixmap.Format.RGB888 ,scrW,scrH,false);
        foreGroundBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,mapProperty.width  ,mapProperty.height,false);

        /*mapTiles=new HashMap<Integer, TilesInfo>();

        for(TiledMapTile tile : mMap.getTileSets().getTileSet(0))
        {
            MapProperties properties=tile.getProperties();

            if(properties.containsKey("id"))
            {
                int id=Integer.parseInt((String)properties.get("id"));
                int nextId=Integer.parseInt((String)properties.get("next"));
                int life=Integer.parseInt((String) properties.get("life"));
                int type=Integer.parseInt((String)properties.get("type"));
                mapTiles.put(id, new TilesInfo(id, nextId,type,life, tile.getTextureRegion()));
            }
        }
        */
        updateMapInfo(false,true);

    }

    public static void addDamageToField(Vector2IDamage[] damageMask, Vector2I position) {
        addDamageToField(damageMask, position.x, position.y);
    }

    public static void addDamageToField(Vector2IDamage[] damageMask, int sx, int sy) {
        for (Vector2IDamage vm: damageMask) {
            int x = vm.x + sx;
            int y = vm.y + sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -1)) continue;
            if ((y < 1) || (y > maxRow -1)) continue;
            //add damage
            fieldDamage[y*maxCel + x] += vm.damage;
        }
    }

    public static void addDamageToField(Vector2I[] mask, int damage, Vector2 position) {
        addDamageToField(mask, damage, position.x, position.y);
    }

    public static void addDamageToField(Vector2I[] mask, int damage, float sx, float sy) {
        for (Vector2I vm: mask) {
            int x = vm.x + (int) sx;
            int y = vm.y + (int) sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -1)) continue;
            if ((y < 1) || (y > maxRow -1)) continue;
            //add damage
            fieldDamage[y*maxCel + x] += damage;
        }
    }

    public static void addDigDamageToField(Vector2I[] mask, int damage, float sx, float sy) {
        for (Vector2I vm: mask) {
            int x = vm.x + (int) sx;
            int y = vm.y + (int) sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -2)) continue;
            if ((y < 1) || (y > maxRow -2)) continue;
            //add damage
            int index = y * maxCel + x;
            fieldDigDamage[index] += damage;
        }
    }

    public static void applyDamage(long time) {
        ArrayList<AbstractPlayer> players = PlayerController.players;
        MapInfo[] mapInfos = mapInfo;

        for (int i = 0; i < fieldDamage.length; i++) {
            if (fieldDamage[i] == 0 && fieldDigDamage[i] == 0) continue;
            MapInfo mapInfo = mapInfos[i];
            int life = mapInfo.life - fieldDamage[i] - fieldDigDamage[i];

            if (life < 0) {
                int mNextId = 0;
                int mId=mapInfo.GetId();
                Tile tile=Tiles.GetTile(mId);
                while (life < 0) {

                    mNextId = tile.group.next;



                    TileGroup nextTileGroup=Tiles.Info.get(mNextId);
                    if(nextTileGroup==null)
                        throw new NullPointerException("nextTileGroup is null");

                    life +=nextTileGroup.life;   //mapTiles.get(mNextId).mLife;


                    if(mNextId==tile.group.id)break;
                    mId=nextTileGroup.GetRandomTileId();
                    tile = Tiles.GetTile(mId);

                    if (mNextId == 0) break;
                }
                if(life<0) life=0;


                mapInfo.SetInfo(mId, life,tile.group.canmove);
            } else {
                mapInfo.life = life;
            }

            if (fieldDamage[i] > 0) {
                for (AbstractGameObject ago : fieldObjects[i]) {
                    //FIXME: should cast send which player
                    ago.applyDamage(null, fieldDamage[i], time);
                }

                for (IPlayer bm : players) {
                    float sx=bm.getX();
                    float sy=bm.getY();

                    float mapX=mapInfo.mX;
                    float mapW=mapX+ rowW;
                    float mapY=mapInfo.mY;
                    float mapH=mapY+ rowH;

                    if((sx>mapX)&&(sx<mapW)&&(sy>mapY)&&(sy<mapH))  {
                        bm.DealDamage(fieldDamage[i]);
                        //TextManager.Add(fieldDamage[i] + "", Color.RED, bm.getX(), bm.getY());
                    }
                }

            }


            fieldDamage[i] = 0;
            fieldDigDamage[i] = 0;
            DrawManager.Append(i);
        }
    }


    public int getHeight()
    {
        return  mapProperty.height;
    }

    public int getWidth()
    {
        return  mapProperty.width;
    }



    public static void Render(SpriteBatch batch)
    {
        RedrawMap();
        //mSpriteBackground.draw(batch);
        mSpriteForeground.draw(batch);


    }

    static private void updateMapInfo(boolean flipX,boolean flipY)
    {
        //MapLayers layers= mMap.getLayers();

        Tile tmpInfo;
        //TiledMapTileLayer tilesLayer=(TiledMapTileLayer)layers.get(foreGroundIndex[0]);

        int colCount = mapProperty.mapW;  //tilesLayer.getWidth();
        int rowCount= mapProperty.mapH;  //tilesLayer.getHeight();

        int h=mapProperty.tileHeight; //(int)tilesLayer.getTileHeight();
        int w=mapProperty.tileWidth; //(int)tilesLayer.getTileWidth();

        int stepY=h/rowH;
        int stepX=w/rowW;
        maxCel=colCount*stepX;
        maxRow=rowCount*stepY;
        int count=(colCount*(w/rowW))*(rowCount*(h/rowH));

        mapInfo=new MapInfo[count];
        fieldDamage = new int[count];
        fieldDigDamage = new int[count];
        Arrays.fill(fieldDamage, 0);
        fieldObjects = new ArrayList[count];
        for (int i = 0; i<count; i++)
        {
            fieldObjects[i] = new ArrayList<AbstractGameObject>(FIELD_CAPACITY);
        }


        for(int col=0;col<colCount;col++)
            for(int row=0;row<rowCount;row++) {
                int fX=col;
                int fY=row;
                if(flipX)
                    fX=colCount-col-1;
                if(flipY)
                    fY=rowCount-row-1;

                //TiledMapTileLayer.Cell cell = tilesLayer.getCell(fX, fY);

                //int id=Integer.parseInt((String) cell.getTile().getProperties().get("id"));
                tmpInfo=mapProperty.foreGroundMap[fY*colCount+fX];
                int id=tmpInfo.id;
                //tmpInfo=Tiles.GetTile(id); //mapTiles.get(id);


                for(int iY=0;iY<stepY;iY++)
                for(int iX=0;iX<stepX;iX++)
                  {
                     int rowIndex=((row*stepY)+iY)*maxCel;
                     int colIndex=((col*stepX)+iX);

                     int rowX=((col*stepX)+iX)*rowW;
                     int rowY=((row*stepY)+iY)*rowH;

                     int index= rowIndex + colIndex;



                     mapInfo[index] = new MapInfo(index,id, rowX, rowY,iY*stepX+iX, tmpInfo.group.life,tmpInfo.group.canmove);
                 }    /**/


            }


        Tiles.UpdateTilesPixmap(stepX, stepY, mapProperty);




    }





    static long mCurId;

    static public void RedrawMap()
    {
        DrawManager.RedrawAll();
    }

    static  public   void Refresh(Camera cam)
    {

        SpriteBatch batch=new SpriteBatch();
        batch.setProjectionMatrix(cam.combined );

        //cam.viewportHeight


        foreGroundBuffer.begin();

        batch.disableBlending();
        batch.begin();

        for(int i=0;i<mapProperty.foreGroundMap.length;i++) {
            //mapInfo[i].GetId()
           Tile tile= mapProperty.foreGroundMap[i];

           //Tile tmpTile=Tiles.GetTile(tmpMapInfo.GetId());

            //Sprite tmpSprite=new Sprite(tmpTile.region.getTexture());
            //tmpSprite.draw(batch);

            int x=(i%mapProperty.mapW)*mapProperty.tileWidth;
            int y=(i/mapProperty.mapW)*mapProperty.tileHeight;

            batch.draw(tile.region.getTexture(),x,y,tile.region.getRegionX(),tile.region.getRegionY(),tile.region.getRegionWidth(),tile.region.getRegionHeight());

            //batch.draw(tmpTile.region.getTexture(),tmpMapInfo.GetX(),tmpMapInfo.GetY(),tmpTile.region.getRegionX(),tmpTile.region.getRegionY(),tmpTile.region.getRegionWidth(),tmpTile.region.getRegionHeight());

        }
        batch.end();
         foreGroundBuffer.end();
       /*
        backGroundBuffer.begin();
            mapRenderer.render(backGroundIndex);
        backGroundBuffer.end();

              //BatchTiledMapRenderer d=(BatchTiledMapRenderer)foreGroundBuffer;

        foreGroundBuffer.begin();
           mapRenderer.render(foreGroundIndex);
        //apRenderer.render(objectsIndex);
           //mapRenderer.renderObject(mMap.);




                     //mapRenderer.renderObject(mMap.getLayers().get(objectsIndex[0]).getObjects().get(1));

            //mapRenderer.render(objectsIndex);
        //  mapRenderer.render();
               //mapRenderer.render();
        //mapRenderer.render();
        foreGroundBuffer.end();

        /*MapObjects objects= mMap.getLayers().get(objectsIndex[0]).getObjects();
        for(MapObject object : objects) {

            TiledMapTile tile = mMap.getTileSets().getTile(400);

            tile.getTextureRegion().flip(false, true);
        } */

        mTextureForeground=foreGroundBuffer.getColorBufferTexture();
        mSpriteForeground  =new Sprite(mTextureForeground);
        mSpriteForeground.flip(false,true);

        /*mTextureBackground=backGroundBuffer.getColorBufferTexture();
        mTextureForeground=foreGroundBuffer.getColorBufferTexture();

        mSpriteBackground =new Sprite(mTextureBackground);
        mSpriteForeground  =new Sprite(mTextureForeground);

        mSpriteBackground.flip(false,true);
        mSpriteForeground.flip(false,true);
        */
    }

    public static boolean isEmptyField(Vector2 position, Vector2I[] mask) {
        return isEmptyField(position.x, position.y, mask);
    }

    public static boolean isEmptyField(float px, float py, Vector2I[] mask){
        boolean can = true;
        for (Vector2I vm: mask) {
            if (!can) break;
            int x = (int)(px + vm.x);
            int y = (int)(py + vm.y);
            //correct bounds
            if ((x < 1) || (x > maxCel -2)) return false;
            if ((y < 1) || (y > maxRow -2)) return false;

            can = can & (mapInfo[y*maxCel + x].canmove);
        }
        return can;
    }

    public static void Calculate(long dtStart) {
        applyDamage(dtStart);
    }

    public static void collect(IPlayer who, Vector2I[] mask, float px, float py, long time) {
        for (Vector2I vm: mask) {
            int x = (int)Math.ceil(px) + vm.x;
            int y = (int)Math.ceil(py) + vm.y;
            //correct bounds
            if ((x < 1) || (x > maxCel -2)) continue;
            if ((y < 1) || (y > maxRow -2)) continue;

            Iterator<AbstractGameObject> iterator = fieldObjects[y * maxCel + x].iterator();
            while (iterator.hasNext()){
                AbstractGameObject next = iterator.next();
                if (next.applyTake(who, time)) {
                    iterator.remove();
                    GameObjectController.Remove(next);
                }
            }

        }
    }
}
