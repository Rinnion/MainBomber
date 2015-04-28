package com.me.TilesManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.me.Map.MapManager;
import com.me.Map.MapProperty;
import com.me.Map.PixmapHelper;
import com.me.minebomber.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by alekseev on 09.09.2014.
 */
public class Tiles {

    public static HashMap<Integer, TileGroup> Info;
    private static HashMap<Integer, Tile> tiles;
    private static TextureAtlas tilesAtlas;

    private static void AddTile(Tile tile) {
        tiles.put(tile.id, tile);
    }

    public static Tile GetTile(Integer id) {
        return tiles.get(id);
    }

    private static void Initialize() {
        Info = new HashMap<>();
        tiles = new HashMap<>();
        tilesAtlas = new TextureAtlas(Gdx.files.internal(Settings.PAK_OBJECTS));
    }

    private static void Done() {

        Info.clear();
        tiles.clear();
        Info = null;
        tiles = null;
        tilesAtlas = new TextureAtlas(Gdx.files.internal(Settings.PAK_OBJECTS));
    }

    private static TextureAtlas.AtlasRegion GetTileRegion(String name) {
        return tilesAtlas.findRegion(name);

    }

    public static void UpdateTilesPixmap(int stepX, int stepY, MapProperty mapProperty) {

        int count = stepX * stepY;


        for (Tile tileInfo : tiles.values()) {
            tileInfo.miniTile = new Pixmap[count];

            for (int iY = 0; iY < stepY; iY++)
                for (int iX = 0; iX < stepX; iX++) {
                    int index = iY * stepX + iX;

                    int startX = tileInfo.region.getRegionX() + iX * MapManager.rowW;//rowW;
                    int startY = tileInfo.region.getRegionY() + iY * MapManager.rowH;//rowH;

                    tileInfo.miniTile[index] = new Pixmap(MapManager.rowW, MapManager.rowH, Pixmap.Format.RGBA8888);
                    PixmapHelper.DrawMiniObject(tileInfo.miniTile[index], startX, startY);
                }
        }
    }

    public static class Loader {

        static Logger logger = LoggerFactory.getLogger(Loader.class);

        static Document xmlDocument;
        static XPath xPath;
        static NodeList nodeList;

        private static String getAttribute(Node node, String name, String def) {
            Node tmpAttributeNode = node.getAttributes().getNamedItem(name);

            if (tmpAttributeNode == null)
                return def;

            return tmpAttributeNode.getNodeValue();

        }

        private static Tile[] getTiles(TileGroup tileGroup, int index) {
            List<Tile> tiles = new ArrayList<Tile>();
            try {

                NodeList nList = (NodeList) xPath.compile("/tiles/group[" + (index + 1) + "]/tile").evaluate(xmlDocument, XPathConstants.NODESET);

                for (int i = 0; i < nList.getLength(); i++) {
                    String sId = getAttribute(nList.item(i), "id", "001");
                    Tile tmpTile = new Tile(Integer.parseInt(sId), Tiles.GetTileRegion(sId), tileGroup);
                    Tiles.AddTile(tmpTile);
                    tiles.add(tmpTile);
                }
                return tiles.toArray(new Tile[tiles.size()]);
            } catch (Exception _ex) {
                logger.error(_ex.getMessage());
                throw new NullPointerException("getTiles()->cannot get tile Index: " + index + " Group: " + tileGroup.id);
            }

        }

        private static void parseXML() {
            int gId;
            int gLife;
            int gNext;
            boolean gCanMove;
            boolean gCanDestroy;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                gId = Integer.parseInt(getAttribute(node, "id", "000"));
                gLife = Integer.parseInt(getAttribute(node, "life", "0"));
                gNext = Integer.parseInt(getAttribute(node, "next", "000"));

                gCanMove = Boolean.parseBoolean(getAttribute(node, "canmove", "false"));
                gCanDestroy = Boolean.parseBoolean(getAttribute(node, "candestroy", "true"));

                TileGroup tileGroup = new TileGroup(gId, gNext, gLife, gCanMove, gCanDestroy);
                tileGroup.SetTiles(getTiles(tileGroup, i));

                Tiles.Info.put(gId, tileGroup);

            }

            for (TileGroup tmpGroup : Tiles.Info.values()) {
                tmpGroup.next = Tiles.Info.get(tmpGroup.nextGroupId);
            }
        }

        public static void Load() {

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                FileHandle fH = Gdx.files.internal(Settings.MAP_TILES_XML);

                xmlDocument = builder.parse(fH.read());

                XPathFactory xPathfactory = XPathFactory.newInstance();
                xPath = xPathfactory.newXPath();

                nodeList = (NodeList) xPath.compile("/tiles/group").evaluate(xmlDocument, XPathConstants.NODESET);

                logger.debug("XPath evaluate complete.");

                Tiles.Initialize();
                logger.debug("Tiles texture success loaded.");
                parseXML();

                logger.debug("Tiles success loaded");
            } catch (Exception _ex) {
                throw new IllegalStateException(_ex.getMessage() + _ex.toString());
            }
        }

        public static void Unload() {
            Tiles.Done();
        }
    }

}
