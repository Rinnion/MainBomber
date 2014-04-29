package com.me.Map;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.me.Bombs.BombPlaser;
import com.me.Bombs.IBomb;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.ObjectMaskHelper.MaskController;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.TileDamager.DamageController;
import com.me.minebomber.DrawManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 17.03.14
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class MapManager {

    public static final int FIELD_CAPACITY = 16;
    private static FrameBuffer backGroundBuffer;
    private static FrameBuffer foreGroundBuffer;

    private static Texture mTextureForeground;
    private static Texture mTextureBackground;

    private static Sprite  mSpriteBackground;
    private static Sprite  mSpriteForeground;


    private static TiledMap mMap;
    private static OrthogonalTiledMapRenderer mapRenderer;

    //информация по тайлам
    public static HashMap<Integer,TilesInfo> mapTiles;
    //информация об обьектах на карте
    public static MapInfo[] mapInfo;

    public static int maxCel;
    public static int maxRow;

    public static int scrW;
    public static int scrH;

    public static int rowH=2;
    public static int rowW=2;

    static int[] backGroundIndex=new int[]{0};
    static int[] foreGroundIndex=new int[]{1};
    static int[] objectsIndex=new int[]{2};
    public static boolean full_redraw=false;
    public static int[] fieldDamage;
    public static ArrayList<AbstractGameObject>[] fieldObjects;

    public static void RedrawPixmap(int mapIndex)
    {
        MapInfo info=mapInfo[mapIndex];
        TilesInfo tile=mapTiles.get(info.GetId());
        PixmapHelper.DrawPixmap(tile.miniMap[info.GetPixmapIndex()], info.GetX(), info.GetY());
    }

    public static void BindForeground()
    {
        PixmapHelper.Bind(mTextureForeground);
    }

    public static   boolean doCircleDamage(int startX, int startY,float radiusDig,float radiusGo,int dmg) {
        int sx = startX / MapManager.rowW;
        int sy = startY / MapManager.rowH;

        int radius = (int) Math.ceil(radiusDig);

        int left = sx - radius - 1;
        int top = sy - radius - 1;
        int right = sx + radius + 1;
        int bottom = sy + radius + 1;

        if (left < 1) left = 1;
        if (right > MapManager.maxCel - 2) right = MapManager.maxCel - 1;
        if (top < 1) top = 1;
        if (bottom > MapManager.maxRow - 2) bottom = MapManager.maxRow - 1;

        float radDig = radiusDig * radiusDig * MapManager.rowW * MapManager.rowH;
        float radGo = radiusGo * radiusGo * MapManager.rowW * MapManager.rowH;

        ArrayList<IPlayer> buffer = null;


        boolean isEmpty = true;
        for (int x = left; x < right; x++)
            for (int y = top; y < bottom; y++) {
                int fx = (x * MapManager.rowW + (MapManager.rowW / 2)) - startX;
                int fy = (y * MapManager.rowH + (MapManager.rowH / 2)) - startY;

                int sum = (fx * fx) + (fy * fy);
                int index = (y * MapManager.maxCel) + x;
                if (sum < radDig) {


                    DamageController.damageOnTile(index, dmg);
                }

                if (sum < radGo) {
                    if (!MapManager.mapInfo[index].isFree())
                        isEmpty = false;
                }
            }
        return isEmpty;
    }




     public static void Initialize()
    {
        mMap=new TmxMapLoader().load("data/tiles.tmx");

        mapRenderer=new OrthogonalTiledMapRenderer(mMap);

        scrW=Integer.parseInt((String) mMap.getProperties().get("scrW"));
        scrH=Integer.parseInt((String) mMap.getProperties().get("scrH"));

        backGroundBuffer=new FrameBuffer(Pixmap.Format.RGB888 ,scrW,scrH,false);
        foreGroundBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,scrW,scrH,false);

        mapTiles=new HashMap<Integer, TilesInfo>();

        MapLayers layers= mMap.getLayers();


        for(TiledMapTile tile : mMap.getTileSets().getTileSet(0))
        {
            MapProperties properties=tile.getProperties();

            if(properties.containsKey("id"))
            {

                int id=Integer.parseInt((String)properties.get("id"));
                int nextId=Integer.parseInt((String)properties.get("next"));

                int life=Integer.parseInt((String)properties.get("life"));
                int index=tile.getId();
                int type=Integer.parseInt((String)properties.get("type"));




                mapTiles.put(id, new TilesInfo(id, nextId,type,life, tile.getTextureRegion()));

            }
        }

        updateMapInfo(false,true);

    }


    public int getHeight()
    {
        return  scrH;
    }

    public int getWidth()
    {
        return  scrW;
    }

    public static void SetView(OrthographicCamera cam)
    {
        mapRenderer.setView(cam);
    }

    public static void Render(SpriteBatch batch)
    {
        RedrawMap();

        mSpriteBackground.draw(batch);
        mSpriteForeground.draw(batch);

        //mapRenderer.render();

        PlayerController.Render(batch);


         /*
        MapObjects objects= mMap.getLayers().get(objectsIndex[0]).getObjects();
        for(MapObject object : objects) {

            TiledMapTile tile= mMap.getTileSets().getTile (400);

            RectangleMapObject rect=(RectangleMapObject)object;


            batch.draw(tile.getTextureRegion(),rect.getRectangle().getX() , rect.getRectangle().getY());

        }
           **/

    }

    static private void updateMapInfo(boolean flipX,boolean flipY)
    {
        MapLayers layers= mMap.getLayers();
        TilesInfo tmpInfo;
        TiledMapTileLayer tilesLayer=(TiledMapTileLayer)layers.get(foreGroundIndex[0]);

        int colCount =  tilesLayer.getWidth();
        int rowCount=tilesLayer.getHeight();

        int h=(int)tilesLayer.getTileHeight();
        int w=(int)tilesLayer.getTileWidth();

        int stepY=h/rowH;
        int stepX=w/rowW;
        maxCel=colCount*stepX;
        maxRow=rowCount*stepY;
        int count=(colCount*(w/rowW))*(rowCount*(h/rowH));

        mapInfo=new MapInfo[count];
        fieldDamage = new int[count];
        Arrays.fill(fieldDamage, 0);
        fieldObjects = new ArrayList[count];
        for (int i = 0; i<count; i++) {
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

                TiledMapTileLayer.Cell cell = tilesLayer.getCell(fX, fY);

                int id=Integer.parseInt((String) cell.getTile().getProperties().get("id"));
                tmpInfo=mapTiles.get(id);


                for(int iY=0;iY<stepY;iY++)
                for(int iX=0;iX<stepX;iX++)
                  {
                     int rowIndex=((row*stepY)+iY)*maxCel;
                     int colIndex=((col*stepX)+iX);

                     int rowX=((col*stepX)+iX)*rowW;
                     int rowY=((row*stepY)+iY)*rowH;

                     int index= rowIndex + colIndex;



                     mapInfo[index] = new MapInfo(index,id, rowX, rowY,iY*stepX+iX, tmpInfo.mLife,mapTiles.get(id).mId==0);
                 }    /**/


            }


        UpdateTilesPixmap(stepX,stepY);




    }

   private static void UpdateTilesPixmap(int stepX,int stepY)
   {

       int count=stepX*stepY;



       for(TilesInfo tileInfo : mapTiles.values())
       {
           tileInfo.miniMap=new Pixmap[count];

           for(int iY=0;iY<stepY;iY++)
               for(int iX=0;iX<stepX;iX++)
               {
                   int index=iY*stepX+iX;

                   int startX=tileInfo.mTexRegion.getRegionX()+iX*rowW;
                   int startY=tileInfo.mTexRegion.getRegionY()+iY*rowH;

                   tileInfo.miniMap[index]=new Pixmap(rowW,rowH, Pixmap.Format.RGBA8888);
                   PixmapHelper.DrawMiniObject(tileInfo.miniMap[index],startX,startY);


               }
       }

   }



    static long mCurId;

    static public void RedrawMap()
    {
        DrawManager.RedrawAll();
    }

    static  public   void Refresh()
    {

        backGroundBuffer.begin();
            mapRenderer.render(backGroundIndex);
        backGroundBuffer.end();



        foreGroundBuffer.begin();
            mapRenderer.render(foreGroundIndex);
        //apRenderer.render(objectsIndex);
           //mapRenderer.renderObject(mMap.);
        //mapRenderer.renderObject(mMap.getLayers().get(2).getObjects().get(0));



            //mapRenderer.render(objectsIndex);
        //  mapRenderer.render();

        //mapRenderer.render();
        foreGroundBuffer.end();
        MapObjects objects= mMap.getLayers().get(objectsIndex[0]).getObjects();
        for(MapObject object : objects) {

            TiledMapTile tile = mMap.getTileSets().getTile(400);

            tile.getTextureRegion().flip(false, true);
        }

        mTextureBackground=backGroundBuffer.getColorBufferTexture();
        mTextureForeground=foreGroundBuffer.getColorBufferTexture();

        mSpriteBackground =new Sprite(mTextureBackground);
        mSpriteForeground  =new Sprite(mTextureForeground);

        mSpriteBackground.flip(false,true);
        mSpriteForeground.flip(false,true);
    }

}
