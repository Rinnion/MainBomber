package com.me.minebomber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by alekseev on 24.03.2014.
 */
public class TextOut {

    static BitmapFont bitmapFont;
     private static String mText="";
    public static void Initialize()
    {
        bitmapFont=new BitmapFont(Gdx.files.internal("data/font.fnt"),Gdx.files.internal("data/font.png"),true);
        bitmapFont.setColor(Color.WHITE);
        //bitmapFont.dr
    }

    public static void SetText(String text)
    {
        mText=text;
    }


    public static void Draw(SpriteBatch sb,int x,int y)
    {

        bitmapFont.draw(sb, mText,(float)x+20,(float)y+20);


    }



}
