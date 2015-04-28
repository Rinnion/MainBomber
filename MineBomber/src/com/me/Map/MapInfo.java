package com.me.Map;


import com.me.TilesManager.Tile;

/**
 * Created by alekseev on 21.03.2014.
 */
public class MapInfo {

    final public int mX;
    final public int mY;
    final public int index;
    public int life;
    public Tile tile;
    public int mPixmapIndex;
    public boolean canmove;

    public boolean view;

    public MapInfo(int index, Tile tile, int x, int y, int pixmapIndex, int life, boolean canmove) {
        mX = x;
        mY = y;
        this.tile = tile;
        this.life = life;
        mPixmapIndex = pixmapIndex;
        this.index = index;
        this.canmove = canmove;
        view = false;
    }

    public void SetInfo(Tile tile, int life) {
        this.tile = tile;
        this.life = life < 0 ? 0 : life;
        this.canmove = tile.group.canmove;
    }

}
