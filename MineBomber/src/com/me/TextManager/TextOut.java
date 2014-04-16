package com.me.TextManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.me.ObjectMaskHelper.Vector2I;

/**
 * Created by alekseev on 24.03.2014.
 */
public class TextOut implements IText {

     private BitmapFont bitmapFont;
     private String mText;
    protected Vector2 pos;
    protected Color color;
    public TextOut()
    {

        bitmapFont=new BitmapFont(Gdx.files.internal("data/font.fnt"),Gdx.files.internal("data/font.png"),true);
        color=new Color(Color.WHITE);
        pos=new Vector2();
        mText="1";

        //specialEffects.RegistrationTextOut(this);
        //bitmapFont.dr
    }

    @Override
    public void SetText(String text)
    {
        mText=text;
    }

    @Override
    public void Draw(SpriteBatch sb)
    {

        bitmapFont.draw(sb, mText,pos.x,pos.y);
    }


    public void Draw(SpriteBatch sb,float x,float y)
    {

        bitmapFont.draw(sb, mText,x,y);
    }

    @Override
    public void SetColor(float r,float g,float b,float a)
    {
        color.set(r,g,b,a);
        bitmapFont.setColor(color);
    }


    @Override
    public void SetColor(Color color)
    {
        this.color=new Color(color);
        bitmapFont.setColor( this.color);
    }


    @Override
    public void SetScale(float XY)
    {
        bitmapFont.setScale(XY);
    }
    @Override
    public void Scale(float amount)
    {
        bitmapFont.scale(amount);
    }

    @Override
    public void SetPosition(Vector2I pos) {

        this.pos.x=(float)pos.x;
        this.pos.y=(float)pos.y;
    }

    @Override
    public void SetPosition(float x, float y) {
        pos.x=x;
        pos.y=y;
    }

    @Override
    public void SetPosition(int x, int y) {
       pos.x=(float)x;
        pos.y=(float)y;
    }


    @Override
    public void Reset() {

    }


}
