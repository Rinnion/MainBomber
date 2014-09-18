package com.me.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.me.Bombs.AbstractBomb;
import com.me.Players.AbstractPlayer;
import com.me.Players.IPlayer;
import com.me.Players.PlayerController;
import com.me.controlers.BombController;
import com.me.minebomber.AbstractGameObject;

import java.util.ArrayList;

/**
 * Created by alekseev on 15.09.2014.
 */
public class ShapeCircle {
    private  static ShapeRenderer shapeRenderer=new ShapeRenderer();




    public static void Draw(Matrix4 projectionMatrix)
    {
        shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for(AbstractBomb bomb : BombController.GetBombs())
        {
           //bomb.Property.
            AbstractGameObject tmpObj=bomb;
            //tmpObj.position.y
            float radius=bomb.Property.range*2;
            float x=tmpObj.position.x*2;
            float y=tmpObj.position.y*2;

            shapeRenderer.circle(x,y,radius);

        }



        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);

    }

}
