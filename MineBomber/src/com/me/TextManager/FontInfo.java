package com.me.TextManager;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by alekseev on 15.09.2014.
 */
public class FontInfo {
        public float scale;
        public Color color;
        public float scaleXY;

        public FontInfo(Color color,float scale,float scaleXY)
        {
            this.color=color;
            this.scale=scale;
            this.scaleXY=scaleXY;
        }


}
