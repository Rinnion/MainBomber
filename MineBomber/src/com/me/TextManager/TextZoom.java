package com.me.TextManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by alekseev on 15.04.2014.
 */
public class TextZoom extends TextOut {

    static final float DEF_Scale=0.5f;
    static final float speedScale=1.5f;
    static final float speedAlpha=0.9f;
    static final float speed=100.0f;
    static final float DEF_A=1f;

    float a=DEF_A;
    float scaleAmount=DEF_Scale;
    float dirX;
    float dirY;



    Vector2 originalPos;

    ITextCallback callBack;

    public TextZoom(ITextCallback callBack)
    {
       super();

       dirX= ((float)Math.random()-0.5f)*speed;
       dirY= ((float)Math.random()-0.5f)*speed;


        this.SetScale(scaleAmount);
        pos.x+=((scaleAmount)/2);
        pos.y+=((scaleAmount)/2);
        this.callBack=callBack;

    }




    @Override
    public void Draw(SpriteBatch sb)
    {
        refresh();
            super.Draw(sb);


    }



    @Override
    public void Reset() {
        super.Reset();
        a=DEF_A;
        scaleAmount=DEF_Scale;
        this.SetScale(scaleAmount);

    }

    private void refresh() {


        this.SetScale(scaleAmount);



        this.SetColor(this.color);

        a=a- (Gdx.graphics.getDeltaTime()*speedAlpha);
        if(a<0) {
            a = 0;
            callBack.OnFree(this);
        }

        this.color.a=a;

       // float prevScale=scaleAmount;

       // scaleAmount+= Gdx.graphics.getDeltaTime()*speedScale;


        float speedX=Gdx.graphics.getDeltaTime()*dirX;
        float speedY=Gdx.graphics.getDeltaTime()*dirY;

        pos.x+=speedX;//+((prevScale-scaleAmount));
        pos.y+=speedY;//+((prevScale-scaleAmount));


        if(scaleAmount>1f)
            scaleAmount=1f;

    }


}
