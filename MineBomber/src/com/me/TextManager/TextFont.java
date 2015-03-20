package com.me.TextManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.me.logger.Log;

/**
 * Created by alekseev on 15.09.2014.
 */
public class TextFont {
     private static BitmapFont bitmapFont;



    public static void Initialize()
    {
        bitmapFont=new BitmapFont(Gdx.files.internal("data/fnt.fnt"),Gdx.files.internal("data/fnt.png"),true);


    }

    public static void Draw(Batch batch,String text,FontInfo font, float x,float y)
    {   synchronized (bitmapFont)
        {
            Log.d("synchronized (bitmapFont) TextFont.Draw");
            bitmapFont.scale(font.scale);
            bitmapFont.setScale(font.scaleXY);
            bitmapFont.setColor(font.color);
            bitmapFont.draw(batch, text, x, y);
        }
    }




}
