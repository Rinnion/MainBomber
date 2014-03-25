package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.me.Particles.ParticleManager;
import com.sun.org.apache.xalan.internal.lib.ExsltDynamic;

import java.awt.*;
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

    static FrameBuffer backGroundBuffer;
    static FrameBuffer foreGroundBuffer;

    static Sprite mSpriteForeground;
    static Sprite mSpriteBackground;

    static Texture mTextureForeground;
    static Texture mTextureBackground;

    //static Pixmap pixMask;

    static Texture mTextureMask;
    static Sprite mSpriteMask;



    static TiledMap mMap;
    static OrthogonalTiledMapRenderer mapRenderer;

    static int scrW;
    static int scrH;



    //static Sprite mSprite;
      static Player player;

    static Pixmap mapObjects;


    static HashMap<Integer,TilesInfo> tilesList;
    public static MapInfo[] mapsInfo;

    static int maxCel;
    static int maxRow;

    static int rowH=4;
    static int rowW=4;

    public static boolean DrawPixMap(Pixmap pixMap,int x,int y)
    {
        Integer [] indexes= createBoundList(x,y);
        boolean dogo=true;
        int id;
        if(indexes.length >0) {


            for(int i=0;i<indexes.length;i++) {
                MapInfo info= mapsInfo[indexes[i]];
                id=info.mId;
                if(info.mLife>0) {
                   info.mLife-=1;

                    if(info.mLife<1)
                    {
                        if(tilesList.get(id).mNextid!=-1)
                        if(tilesList.get(id).mNextid!=info.mId)
                        {
                            //info.mId=tilesList.get(info.mId).mNextid;
                            info.mId=tilesList.get(id).mNextid;
                            info.mLife=tilesList.get(id).mLife;
                        }

                        TextOut.SetText(info.mId+"");
                        PixmapHelper.Draw(tilesList.get(info.mId).mTexRegion,mTextureForeground,info.mX,info.mY);
                        //ParticleManager.Fire(info.mX,info.mY);
                    }

                   dogo=false;
                }

                if(dogo==true) {
                    //Gdx.gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureForeground.getTextureObjectHandle());
                    //Gdx.gl.glTexSubImage2D(GL10.GL_TEXTURE_2D, 0, (int) info.mX, (int) info.mY, pixMap.getWidth(), pixMap.getHeight(), pixMap.getGLFormat(), pixMap.getGLType(), pixMap.getPixels());
                }
            }
        }
        return dogo;
    }


    static int radius=1;

    private static Integer[] createBoundList(int startX, int startY)
    {
        int sx=startX/rowW;
        int sy=startY/rowH;

        int left = sx-radius-1;
        int top = sy-radius-1;
        int right = sx+radius+1;
        int bottom = sy+radius+1;

        if (left<1) left = 1;
        if (right>maxCel-2) right = maxCel-1;
        if (top<1) top = 1;
        if (bottom>maxRow-2) bottom = maxRow-1;


        int width = (right-left);
        int height = bottom - top;

        ArrayList<Integer> al = new ArrayList<Integer>();
        for(int x=left;x<right;x++)
        for(int y=top; y<bottom;y++){
            int fx = x*rowW+(rowW/2);
            int fy = y*rowH+(rowH/2);
            if ((fx-startX)*(fx-startX) + (fy-startY)*(fy-startY) < radius*radius*rowW*rowH){
                int index = (y*maxCel)+x;
                al.add(index);
            }
        }

         return al.toArray(new Integer[al.size()]);


    }


    static int getMaxPower(int power,int size)
    {
        int startPower=1;
        int retpower=power;
        while (true)
        {


               //retpower = (int) Math.pow((double) 2, (double) power);
              retpower=retpower*power;

            //startPower++;
            if(retpower>=size)
                return retpower;

        }


    }


    public static void Initialize()
    {


         mMap=new TmxMapLoader().load("data/tiles.tmx");

         //renderer=new TiledMapRenderer(mMap,32);
        mapRenderer=new OrthogonalTiledMapRenderer(mMap);





        //mMap.getTileSets().



        scrW=Integer.parseInt((String) mMap.getProperties().get("scrW"));
        scrH=Integer.parseInt((String) mMap.getProperties().get("scrH"));



        backGroundBuffer=new FrameBuffer(Pixmap.Format.RGB888 ,scrW,scrH,false);
        foreGroundBuffer=new FrameBuffer(Pixmap.Format.RGBA8888,scrW,scrH,false);


         tilesList=new HashMap<Integer, TilesInfo>();
        //mMap.getTileSets().getTileSet(0).i

        for(TiledMapTile tile : mMap.getTileSets().getTileSet(0))
        {
            MapProperties properties=tile.getProperties();

            if(properties.containsKey("id"))
            {
                // tile.getTextureRegion()

                int nextid=Integer.parseInt((String)properties.get("next"));
                int id=Integer.parseInt((String)properties.get("id"));
                int life=Integer.parseInt((String)properties.get("life"));
                int index=tile.getId();

                tilesList.put(id, new TilesInfo(life, id, nextid, tile.getTextureRegion()));

            }
        }


       //mTextureMask=new Texture(scrW,scrH, Pixmap.Format.Alpha);

        //mSpriteMask=new

       // mask=new Pixmap(30,30, Pixmap.Format.Alpha);
       // mask.setColor(new Color(1,0,1,0.5f));


        player=new Player();
        Gdx.input.setInputProcessor(new FightInputProcessor(player));


         //maskTexture=new Texture(mask);


        //mask.fill();

       // mask.setColor(new Color(0f,0f,0f,0));
        //mask.fillRectangle(0,0,32,32);
        //maskTexture=new Texture(mask);
       // mPixMap=new Pixmap();

       // mPixMap.

       // d.begin();
       // d.flush();
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

        //mapRenderer.render();
        //mask.setBlending(Pixmap.Blending.None);
       // batch.disableBlending();
        //mask.setBlending(Pixmap.Blending.SourceOver);

        //mTexture.draw(mask,100,100);
        //mTexture.

       //batch.draw(mTexture,0,0);
       // batch.disableBlending();

        //mSprite.draw(batch);

        mSpriteBackground.draw(batch);
        mSpriteForeground.draw(batch);

        player.Render(batch);

        //batch.draw(mTexture,0,0);
        //batch.draw(maskTexture,0,0);
       // batch.enableBlending();

        //batch.draw();

       // batch.enableBlending();
        //batch.draw(maskTexture,0,0);

       // batch.enableBlending();
        //mask.setBlending(Pixmap.Blending.SourceOver);

    }

      static int[] backGroundIndex=new int[]{0};
      static int[] foreGroundIndex=new int[]{1};

    static private void updateMapInfo(boolean flipX,boolean flipY)
    {
        //MapObjects mo=mMap.getLayers(). .getObjects();
        MapLayers layers= mMap.getLayers();

        TiledMapTileLayer tilesLayer=(TiledMapTileLayer)layers.get(foreGroundIndex[0]);


       // TiledMapTileLayer.Cell cell2 = tilesLayer.getCell(8, 3);
       // int id2=Integer.parseInt((String) cell2.getTile().getProperties().get("id"));

        int colCount =  tilesLayer.getWidth();
        int rowCount=tilesLayer.getHeight();

        int h=(int)tilesLayer.getTileHeight();
        int w=(int)tilesLayer.getTileWidth();


        maxCel=colCount*2;//(w/rowW);
        maxRow=rowCount*2;//(h/rowH);
        int count=(colCount*(w/rowW))*(rowCount*(h/rowH));//((h+w)/(rowH+rowW));
        mapsInfo=new MapInfo[count];

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

                int rowIndex1=(row*2)*maxCel; // +-
                int colIndex1=(col*2);          // --

                int rowIndex2=(row*2)*maxCel; // -+
                int colIndex2=(col*2)+1;              // --

                int rowIndex3=((row*2)+1)*maxCel;   // --
                int colIndex3=(col*2);            // +-

                int rowIndex4=((row*2)+1)*maxCel; // --
                int colIndex4=(col*2)+1;            // -+

                int rowX1= (col*2)*rowW;     // +-
                int rowY1= (row*2)*rowH;     // --

                int rowX2= ((col*2)+1)*rowW; // -+
                int rowY2= (row*2)*rowH;     // --

                int rowX3= (col*2)*rowW;     // --
                int rowY3= ((row*2)+1)*rowH;     // +-;

                int rowX4= ((col*2)+1)*rowW;     // --
                int rowY4= ((row*2)+1)*rowH;     // -+


                mapsInfo[rowIndex1+colIndex1] = new MapInfo( id, rowX1,rowY1,tilesList.get(id).mLife);
                mapsInfo[rowIndex2+colIndex2]=new MapInfo( id, rowX2,rowY2,tilesList.get(id).mLife);
                mapsInfo[rowIndex3+colIndex3]=new MapInfo( id, rowX3,rowY3,tilesList.get(id).mLife);
                mapsInfo[rowIndex4+colIndex4]=new MapInfo( id,rowX4,rowY4,tilesList.get(id).mLife);

            }


       // mapsInfo=new TilesInfo[mapsCount];



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
