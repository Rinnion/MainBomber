package com.me.Graphics;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by alekseev on 15.09.2014.
 */
public class ShapeCircle {
    private  static ShapeRenderer shapeRenderer=new ShapeRenderer();




    public static void Draw(Matrix4 projectionMatrix)
    {
        /*shapeRenderer.setProjectionMatrix(projectionMatrix);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        ArrayList<AbstractBomb> bomsList=BombController.mBombList;

        for(AbstractBomb bomb :bomsList)
        {
           //bomb.Property.
            CircleExplosion tmpObj=(CircleExplosion)(bomb.behavior);
            //tmpObj.position.y
            float radius=tmpObj.range*2;
            float x=bomb.position.x*2;
            float y=bomb.position.y*2;

            shapeRenderer.circle(x,y,radius);

        }



        shapeRenderer.end();
        Gdx.gl.glDisable(GL10.GL_BLEND);
          */
    }

}
