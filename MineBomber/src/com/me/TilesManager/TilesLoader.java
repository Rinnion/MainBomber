package com.me.TilesManager;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.Map.*;
import com.me.assetloader.AssetLoader;
import com.me.logger.Log;
import com.me.minebomber.Settings;
import com.sun.org.apache.bcel.internal.classfile.ClassFormatException;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.NodeType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alekseev on 09.09.2014.
 */
public class TilesLoader {
    static Document xmlDocument;
    static XPath xPath;
    static NodeList nodeList;

    private static String getAttribute(Node node,String name,String def)
    {
        Node tmpAttributeNode= node.getAttributes().getNamedItem(name);

        if(tmpAttributeNode==null)
            return  def;

        return tmpAttributeNode.getNodeValue();

    }

    private static Tile[] getTiles(TileGroup tileGroup,int index)
    {
        List<Tile> tiles=new ArrayList<Tile>();
       try {

           NodeList nList = (NodeList) xPath.compile("/tiles/group[" + (index+1) + "]/tile").evaluate(xmlDocument, XPathConstants.NODESET);

           for(int i=0;i<nList.getLength();i++) {
               String sId=getAttribute(nList.item(i),"id","001");
               Tile tmpTile=new Tile(Integer.parseInt(sId),Tiles.GetTileRegion(sId),tileGroup);
               Tiles.AddTile(tmpTile);
               tiles.add(tmpTile);
           }
           return (Tile[])tiles.toArray(new Tile[tiles.size()]);
       }
       catch (Exception _ex)
       {
           Log.e(_ex.getMessage());
           throw new NullPointerException("getTiles()->cannot get tile Index: " + index + " Group: " + tileGroup.id);
       }

    }

    private static void parseXML()
    {
         //String
        int gId;
        int gLife;
        int gNext;
        boolean gCanMove;
        boolean gCanDestroy;

        //Tiles.Info=new HashMap<Integer, TileGroup>();
                //new TileGroup[nodeList.getLength()];
         for (int i=0;i<nodeList.getLength();i++)
         {
            Node node=nodeList.item(i);
            gId=Integer.parseInt(getAttribute(node,"id","000"));
            gLife=Integer.parseInt(getAttribute(node,"life","0"));
            gNext=Integer.parseInt(getAttribute(node, "next", "000"));

            gCanMove=Boolean.parseBoolean(getAttribute(node,"canmove","false"));
            gCanDestroy=Boolean.parseBoolean(getAttribute(node,"candestroy","true"));

             TileGroup tileGroup=new TileGroup(gId,gNext,gLife,gCanMove,gCanDestroy);
             tileGroup.SetTiles(getTiles(tileGroup,i));

             Tiles.Info.put(gId,tileGroup);


         }

    }

    public static void Initialize()
    {
        //XPath xPath= XPathFactory.newInstance()


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            FileInputStream fileInputStream=new FileInputStream(Settings.MAP_TILES_XML);

            xmlDocument= builder.parse(fileInputStream);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            xPath = xPathfactory.newXPath();

            nodeList =(NodeList)xPath.compile("/tiles/group").evaluate(xmlDocument, XPathConstants.NODESET );

            Log.d("XPath evaluate complete.");

            Tiles.Initialize();
            Log.d("Tiles texture success loaded.");
            parseXML();

            Log.d("Tiles success loaded");
        }
        catch (Exception _ex )
        {

            Log.e(_ex.getMessage());
            throw  new ClassFormatException(_ex.getMessage());

        }


    }

}
