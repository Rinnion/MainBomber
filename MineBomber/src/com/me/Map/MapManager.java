package com.me.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.BombPlaser;
import com.me.Bombs.IBomb;
import com.me.ObjectMaskHelper.IMask;
import com.me.ObjectMaskHelper.MaskController;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.TileDamager.DamageController;
import com.me.minebomber.DrawManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 17.03.14
 * Time: 18:09
 * To change this template use File | Settings | File Templates.
 */
public class MapManager {

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

    public static boolean full_redraw=false;

    public static void Redraw(int mapIndex)
    {
        MapInfo info=mapInfo[mapIndex];
        TilesInfo tile=mapTiles.get(info.GetId());
        PixmapHelper.Draw(tile.mTexRegion, info.GetTextureStpes() , mTextureForeground, info.GetX() , info.GetY() );

    }

    public static void BindForeground()
    {
        PixmapHelper.Bind(mTextureForeground);
    }

    public static void RedrawWoBind(int mapIndex)
    {
        MapInfo info=mapInfo[mapIndex];
        TilesInfo tile=mapTiles.get(info.GetId());
        PixmapHelper.BindDraw (tile.mTexRegion, info.GetTextureStpes() ,  info.GetX() , info.GetY() );

    }


    public static   void doBombDamage(int startX, int startY,IBomb bomb)
    {

        float radius=bomb.GetProperty().range;
        Vector2[] mask= MaskController.GetMask(radius);

        ArrayList<IPlayer> buffer=new ArrayList<IPlayer>();

        int x;
        int y;

        for(int i=0;i<mask.length;i++)
        {
            Vector2 pos=mask[i];
            x=(int)pos.x+(startX/2);
            y=(int)pos.y+(startY/2);

            if((x<0)||(y<0))
                continue;
            if((x>maxCel)||(y>maxRow))
                continue;

            int index = ((y*MapManager.maxCel)+x);

            DamageController.damageOnTile(index, bomb.GetProperty ().dmgMin + (float)Math.random()*bomb.GetProperty().dmgMax);
            PlayerController.DealDamage(buffer, x * MapManager.rowW, y * MapManager.rowH, bomb);
            BombPlaser.DealDamage(x * MapManager.rowW, y * MapManager.rowH, bomb);

        }



        /*int sx=startX/ MapManager.rowW;
        int sy=startY/MapManager.rowH;

        int radius = (int)Math.ceil(radiusDig);

        int left = sx-radius-1;
        int top = sy-radius-1;
        int right = sx+radius+1;
        int bottom = sy+radius+1;

        if (left<1) left = 1;
        if (right>MapManager.maxCel-2) right = MapManager.maxCel-1;
        if (top<1) top = 1;
        if (bottom>MapManager.maxRow-2) bottom = MapManager.maxRow-1;

        float radDig= radiusDig*radiusDig*MapManager.rowW*MapManager.rowH;
        float radGo= radiusGo*radiusGo*MapManager.rowW*MapManager.rowH;
        ArrayList<IPlayer> buffer=null;
        if(bomb!=null)
            buffer=new ArrayList<IPlayer>();


        boolean isEmpty=true;
        for(int x=left;x<right;x++)
            for(int y=top; y<bottom;y++){
                int fx = (x*MapManager.rowW+(MapManager.rowW/2))-startX;
                int fy = (y*MapManager.rowH+(MapManager.rowH/2))-startY;

                int sum=(fx*fx)+(fy*fy);
                int index = (y*MapManager.maxCel)+x;
                if (sum <radDig ){


                    if(bomb!=null) {
                        DamageController.damageOnTile(index, bomb.GetProperty ().dmgMin + (float)Math.random()*bomb.GetProperty().dmgMax);
                        PlayerController.DealDamage(buffer, x * MapManager.rowW, y * MapManager.rowH, bomb);
                        BombPlaser.DealDamage(x * MapManager.rowW, y * MapManager.rowH, bomb);
                    }
                    else
                        DamageController.damageOnTile(index, dmg);
                }

                if(sum<radGo)
                {
                    if(!MapManager.mapInfo[index].isFree())
                        isEmpty=false;
                }
            }


        return isEmpty;
       */
    }


    public static   boolean doCircleDamage(int startX, int startY,float radiusDig,float radiusGo,float dmg,IBomb bomb)
    {
        int sx=startX/ MapManager.rowW;
        int sy=startY/MapManager.rowH;

        int radius = (int)Math.ceil(radiusDig);

        int left = sx-radius-1;
        int top = sy-radius-1;
        int right = sx+radius+1;
        int bottom = sy+radius+1;

        if (left<1) left = 1;
        if (right>MapManager.maxCel-2) right = MapManager.maxCel-1;
        if (top<1) top = 1;
        if (bottom>MapManager.maxRow-2) bottom = MapManager.maxRow-1;

        float radDig= radiusDig*radiusDig*MapManager.rowW*MapManager.rowH;
        float radGo= radiusGo*radiusGo*MapManager.rowW*MapManager.rowH;
        ArrayList<IPlayer> buffer=null;
        if(bomb!=null)
            buffer=new ArrayList<IPlayer>();


        boolean isEmpty=true;
        for(int x=left;x<right;x++)
            for(int y=top; y<bottom;y++){
                int fx = (x*MapManager.rowW+(MapManager.rowW/2))-startX;
                int fy = (y*MapManager.rowH+(MapManager.rowH/2))-startY;

                int sum=(fx*fx)+(fy*fy);
                int index = (y*MapManager.maxCel)+x;
                if (sum <radDig ){


                    if(bomb!=null) {
                        DamageController.damageOnTile(index, bomb.GetProperty ().dmgMin + (float)Math.random()*bomb.GetProperty().dmgMax);
                        PlayerController.DealDamage(buffer, x * MapManager.rowW, y * MapManager.rowH, bomb);
                        BombPlaser.DealDamage(x * MapManager.rowW, y * MapManager.rowH, bomb);
                    }
                    else
                        DamageController.damageOnTile(index, dmg);
                }

                if(sum<radGo)
                {
                    if(!MapManager.mapInfo[index].isFree())
                        isEmpty=false;
                }
            }


        return isEmpty;
    }


     public static Integer[] createBoundList(int startX, int startY, float radiusFloat)
    {
        int sx=startX/rowW;
        int sy=startY/rowH;

        int radius = (int)Math.ceil(radiusFloat);

        int left = sx-radius-1;
        int top = sy-radius-1;
        int right = sx+radius+1;
        int bottom = sy+radius+1;

        if (left<1) left = 1;
        if (right>maxCel-2) right = maxCel-1;
        if (top<1) top = 1;
        if (bottom>maxRow-2) bottom = maxRow-1;
       // int countArray=0;

        ArrayList<Integer> al = new ArrayList<Integer>();


        for(int x=left;x<right;x++)
        for(int y=top; y<bottom;y++){
            int fx = x*rowW+(rowW/2);
            int fy = y*rowH+(rowH/2);
            if ((fx-startX)*(fx-startX) + (fy-startY)*(fy-startY) < radiusFloat*radiusFloat*rowW*rowH){
                int index = (y*maxCel)+x;
                al.add(index);

            }
        }

         return al.toArray(new Integer[al.size()]);
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

                float life=Float.parseFloat((String)properties.get("life"));
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
        PlayerController.Render(batch);
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



                     mapInfo[index] = new MapInfo(index,id, rowX, rowY,new Rectangle(iX*rowW,iY*rowH,rowW,rowH), tmpInfo.mLife,mapTiles.get(id).mId==0);
                 }    /**/


            }




        for(TilesInfo tileInfo : mapTiles.values())
        {

        }


    }

    static long mCurId;

    static public void RedrawMap()
    {
        DrawManager.RedrawAll();

        if(!full_redraw)
            return;

        int l = mapInfo.length;

        for(int i=0; i<l; i++)
        {
            //mCurId += i;//= mapInfo[i].GetId();
            if(mapInfo[i].redraw) {
                mapInfo[i].redraw=false;
                Redraw(i);
            }
        }
    }

    static  public   void Refresh()
    {

        backGroundBuffer.begin();
            mapRenderer.render(backGroundIndex);
        backGroundBuffer.end();

        foreGroundBuffer.begin();
            mapRenderer.render(foreGroundIndex);
        foreGroundBuffer.end();

        mTextureBackground=backGroundBuffer.getColorBufferTexture();
        mTextureForeground=foreGroundBuffer.getColorBufferTexture();

        mSpriteBackground =new Sprite(mTextureBackground);
        mSpriteForeground  =new Sprite(mTextureForeground);

        mSpriteBackground.flip(false,true);
        mSpriteForeground.flip(false,true);
    }

}
