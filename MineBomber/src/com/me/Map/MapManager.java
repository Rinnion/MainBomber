package com.me.Map;

import com.badlogic.gdx.Gdx;
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
import com.me.TextManager.TextManager;
import com.me.TextManager.TextOut;
import com.me.TilesManager.Tile;
import com.me.TilesManager.TileGroup;
import com.me.TilesManager.Tiles;
import com.me.Utility.IntArray;
import com.me.controlers.GameObjectController;
import com.me.logger.Log;
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

    private static FrameBuffer foreGroundBuffer;

    private static Texture mTextureForeground;




    private static Sprite  mSpriteForeground;

    static IMap mMap;
    public static MapProperty mapProperty;




    public static int maxCel;
    public static int maxRow;


    public static int rowH=2;
    public static int rowW=2;




    //информация об обьектах на карте
    public static MapInfo[] mapInfo;
    private static final IntArray redrawArray=new IntArray(DrawManager.FIELDS_COUNT);
    public static int[] fieldDamage;
    public static int[] fieldDigDamage;

    public static IntArray applyIndexDamage;

    public static ArrayList<AbstractGameObject>[] fieldObjects;

    public static void RedrawPixmap(int mapIndex)
    {

        MapInfo info=mapInfo[mapIndex];
        //TilesInfo tile=  //mapTiles.get(info.GetId());

        //Tile tile = info.mTile;



            PixmapHelper.DrawPixmap(info.mTile.miniTile[info.mPixmapIndex], info.mX , info.mY);
        info.view=true;


    }

    public static void BindForeground()
    {
        PixmapHelper.Bind(mTextureForeground);
    }




     public static void Initialize()
    {
        mMap=new Loader("data/maps/map001.tmx");
        mMap.Initialize();
        mapProperty=mMap.GetMap();
        foreGroundBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,mapProperty.width  ,mapProperty.height,false);
        updateMapInfo(false,true);

    }



    public static void beginRedrawViewMask(Vector2I[] mask, float sx, float sy) {
        for (Vector2I vm: mask) {
            int x = vm.x + (int) sx;
            int y = vm.y + (int) sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -2)) continue;
            if ((y < 1) || (y > maxRow -2)) continue;
            //add damage
            int index = y * maxCel + x;

            MapManager.applyIndexDamage.add(index);
        }
    }


    public static void addDamageToField(Vector2IDamage[] damageMask, int sx, int sy) {
        for (Vector2IDamage vm: damageMask) {
            int x = vm.x + sx;
            int y = vm.y + sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -1)) continue;
            if ((y < 1) || (y > maxRow -1)) continue;
            //add damage
            int index = y * maxCel + x;
            fieldDamage[index] += vm.damage;
            MapManager.applyIndexDamage.add(index);

        }
    }




    public static void addDigDamageToField(Vector2I[] mask, int damage, float sx, float sy) {
        IntArray arrayIndexDamage = MapManager.applyIndexDamage;
        for (Vector2I vm: mask) {
            int x = vm.x + (int) sx;
            int y = vm.y + (int) sy;
            //correct bounds
            if ((x < 1) || (x > maxCel -2)) continue;
            if ((y < 1) || (y > maxRow -2)) continue;
            //add damage
            int index = y * maxCel + x;
            fieldDigDamage[index] += damage;
            arrayIndexDamage.add(index);
        }
    }

    public static void applyDamage(long time) {
        ArrayList<AbstractPlayer> players = PlayerController.players;


        final MapInfo[] mapInfos = mapInfo;
        final int [] fieldDamages=fieldDamage;
        final int []fieldDigDamages=fieldDigDamage;
        final int []indexArray=applyIndexDamage.getFullArray();
        final int count=applyIndexDamage.size();

        boolean redraw=false;






        for (int index = 0; index < count; index++) {
            redraw=false;
            int i=indexArray[index];
            MapInfo mapInfo = mapInfos[i];
            if(!mapInfo.view)
                redraw=true;

            if (fieldDamages[i] == 0 && fieldDigDamages[i] == 0) {

                if(redraw)
                    redrawArray.add(i);
                continue;
            }



            int life = mapInfo.life - fieldDamages[i] - fieldDigDamages[i];


            if (life < 0) {
                //int mNextId = 0;
                //int mId=mapInfo.mId;
                Tile tile=mapInfo.mTile; //Tiles.GetTile(mId);

                TileGroup curGroup=tile.group;




                while (life < 0) {
                    TileGroup nextTileGroup=curGroup.next;

                     if(nextTileGroup==null)
                        throw new NullPointerException("nextTileGroup is null");

                    life +=nextTileGroup.life;   //mapTiles.get(mNextId).mLife;


                    if(nextTileGroup.id==tile.group.id)break;
                    tile=nextTileGroup.GetRandomTile();
                    redraw=true;
                    //Log.d("random tile: " + tile.id);


                    //tile = Tiles.GetTile(mId);

                    if (nextTileGroup.id == 0) break;

                }
                if(life<0) life=0;


                mapInfo.SetInfo(tile, life, tile.group.canmove);
            } else {
                mapInfo.life = life;
            }



            if (fieldDamages[i] > 0) {
                for (AbstractGameObject ago : fieldObjects[i]) {
                    //FIXME: should cast send which player
                    ago.applyDamage(null, fieldDamages[i], time);
                }

                for (IPlayer bm : players) {
                    float sx=bm.getX();
                    float sy=bm.getY();

                    float mapX=mapInfo.mX;
                    float mapW=mapX+ rowW;
                    float mapY=mapInfo.mY;
                    float mapH=mapY+ rowH;

                  //  AbstractPlayer ap =(AbstractPlayer)bm;


                    if((sx>mapX)&&(sx<mapW)&&(sy>mapY)&&(sy<mapH))  {
                        bm.DealDamage(fieldDamages[i]);
                        //TextManager.Add(fieldDamage[i] + "", Color.RED, bm.getX(), bm.getY());
                    }
                }

            }


            fieldDamages[i] = 0;
            fieldDigDamages[i] = 0;
            if(redraw)
                redrawArray.add(i);
            //DrawManager.Append(i);

        }
        //if(redraw)
        DrawManager.AddArray(redrawArray);
        redrawArray.clear();
        applyIndexDamage.clear();

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

        mSpriteForeground.draw(batch);

        //batch.enableBlending();

        //batch.disableBlending();



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
        applyIndexDamage=new IntArray(count);


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

                //tmpInfo=Tiles.GetTile(id); //mapTiles.get(id);


                for(int iY=0;iY<stepY;iY++)
                for(int iX=0;iX<stepX;iX++)
                  {
                     int rowIndex=((row*stepY)+iY)*maxCel;
                     int colIndex=((col*stepX)+iX);

                     int rowX=((col*stepX)+iX)*rowW;
                     int rowY=((row*stepY)+iY)*rowH;

                     int index= rowIndex + colIndex;



                     mapInfo[index] = new MapInfo(index,tmpInfo, rowX, rowY,iY*stepX+iX, tmpInfo.group.life,tmpInfo.group.canmove);
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


            if(mapInfo[i].view)
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
        //mTextureFog=new Texture(mTextureForeground.getWidth(),mTextureForeground.getHeight(), Pixmap.Format.RGBA8888);
        //mPixmapFog=new Pixmap(mTextureForeground.getWidth(),mTextureForeground.getHeight(), Pixmap.Format.Alpha);


        //mPixmapFog.setColor(0,0,0,1);
        //mPixmapFog.fill();

        //mTextureFog=new Texture(mPixmapFog);



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
