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

     //private
    FontInfo fontInfo;
    private String mText;
    protected Vector2 pos;

    public TextOut()
    {

        fontInfo=new FontInfo(new Color(Color.WHITE),1f,1f);

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
    public boolean Draw(SpriteBatch sb)
    {
        TextFont.Draw(sb,mText,fontInfo,pos.x,pos.y);
        return true;
        //bitmapFont.draw(sb, mText,pos.x,pos.y);
    }


    public void Draw(SpriteBatch sb,float x,float y)
    {
        TextFont.Draw(sb,mText,fontInfo,x,y);
        //bitmapFont.draw(sb, mText,x,y);

    }

    @Override
    public void SetColor(float r,float g,float b,float a)
    {
        fontInfo.color=new Color(r,g,b,a);
    }


    @Override
    public void SetColor(Color color)
    {
        fontInfo.color=new Color(color);
    }


    @Override
    public void SetScale(float XY)
    {
        fontInfo.scaleXY=XY;
    }
    @Override
    public void Scale(float amount)
    {
        fontInfo.scale=amount;
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
