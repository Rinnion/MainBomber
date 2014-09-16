package com.me.TextManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.ObjectMaskHelper.Vector2I;

/**
 * Created by alekseev on 15.04.2014.
 */
public interface IText {

    void SetText(String text);
    boolean Draw(SpriteBatch sb);
    void SetColor(float r,float g,float b,float a);
    void SetColor(Color color);

    void SetScale(float XY);
    void Scale(float amount);
    void SetPosition(Vector2I pos);
    void SetPosition(float x,float y);
    void SetPosition(int x,int y);
    void Reset();
}
