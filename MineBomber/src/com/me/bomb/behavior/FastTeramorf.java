package com.me.bomb.behavior;

import com.badlogic.gdx.math.Vector2;
import com.me.Map.MapInfo;
import com.me.Map.MapManager;
import com.me.TilesManager.Tile;
import com.me.TilesManager.Tiles;
import com.me.Utility.RecyclableArray;
import com.me.bomb.AbstractBomb;
import com.me.minebomber.AbstractGameObject;
import com.me.minebomber.DrawManager;

import java.util.ArrayList;

/**
 * Created by alekseev on 18.09.2014.
 */
public class FastTeramorf extends RecyclableBehavior implements IBehavior {

    final static int DEF_XY_COUNT = 10000;
    int[] aX = new int[DEF_XY_COUNT];
    int[] aY = new int[DEF_XY_COUNT];
    int[] aX2 = new int[DEF_XY_COUNT];
    int[] aY2 = new int[DEF_XY_COUNT];
    int cx;
    int cy;
    Tile tile;
    int sindex;
    Vector2 pos1 = null;
    Vector2 pos2 = null;
    Vector2 pos3 = null;
    Vector2 pos4 = null;
    int xycount;
    int iterationCount = 40;
    boolean cur = true;

    public FastTeramorf(RecyclableArray array) {
        super(array);
    }

    public FastTeramorf update(int cx, int cy, int tileid) {
        this.cx = cx;
        this.cy = cy;
        sindex = cy * MapManager.maxCel + cx;
        tile = Tiles.GetTile(tileid);
        aX[0] = cx;
        aY[0] = cy;
        xycount = 1;

        iterationCount = (int) (Math.random() * iterationCount) + 10;

        return this;
    }

    @Override
    public boolean detonate(AbstractBomb bomb, long time) {

        //MapManager.addDamageToField(ExplodeMask, bomb.position.x,bomb.position.y);
        //ParticleManager.Fire(bomb.position.x*MapManager.rowW ,bomb.position.y*MapManager.rowH,range*4);
        MapInfo[] mapInfo = MapManager.mapInfo;

        ArrayList<AbstractGameObject>[] fieldObjects = MapManager.fieldObjects;

        int iteration = 0;

        while (iteration < iterationCount) {

            int[] cx = (cur) ? aX : aX2;
            int[] cy = (cur) ? aY : aY2;
            int[] tx = (cur) ? aX2 : aX;
            int[] ty = (cur) ? aY2 : aY;
            cur = !cur;

            int txycount = 0;
            int index;


            for (int i = 0; i < xycount; i++) {
                index = cy[i] * MapManager.maxCel + cx[i];

                if (mapInfo[index].mTile.group.id != 0) continue;


                mapInfo[index].SetInfo(tile, tile.group.life, false);
                DrawManager.redrawArray2.push(index);

                tx[txycount] = cx[i] + 1;
                ty[txycount] = cy[i];

                txycount++;
                tx[txycount] = cx[i] - 1;
                ty[txycount] = cy[i];
                txycount++;
                tx[txycount] = cx[i];
                ty[txycount] = cy[i] - 1;
                txycount++;
                tx[txycount] = cx[i];
                ty[txycount] = cy[i] + 1;
                txycount++;

                tx[txycount] = cx[i] + 1;
                ty[txycount] = cy[i] + 1;
                txycount++;
                tx[txycount] = cx[i] - 1;
                ty[txycount] = cy[i] - 1;
                txycount++;
                tx[txycount] = cx[i] + 1;
                ty[txycount] = cy[i] - 1;
                txycount++;
                tx[txycount] = cx[i] - 1;
                ty[txycount] = cy[i] + 1;
                txycount++;


            }

            xycount = txycount;
            iteration++;
        }

        return true;
    }

}
