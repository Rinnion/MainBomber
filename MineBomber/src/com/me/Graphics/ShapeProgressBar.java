package com.me.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.me.Players.AbstractPlayer;
import com.me.Players.IPlayer;
import com.me.Players.Player;


import java.util.ArrayList;

/**
 * Created by alekseev on 01.04.2014.
 */
public class ShapeProgressBar {
    ShapeRenderer shapeRenderer;


    public ShapeProgressBar()
    {
          shapeRenderer=new ShapeRenderer();

    }

    public void Draw(ArrayList<AbstractPlayer> players,Matrix4 projectionMatrix)
    {
        shapeRenderer.setProjectionMatrix(projectionMatrix);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(IPlayer player : players)
        {
            ILifeProgressBar bar=player.GetLifeBar();
            if(!bar.visibleLifeBar())
                continue;
            //shapeRenderer.setColor(Color.RED );
            //shapeRenderer.setColor(bar.colorBack());
            //shapeRenderer.rect(player.getX(),player.getY()-4,player.getW(),2);
            //shapeRenderer.setColor(bar.colorFront());

            //shapeRenderer.rect(player.getX(),player.getY()-4,(player.getW()/player.GetMaxLife())*player.GetLife() ,2);
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

    }

}
