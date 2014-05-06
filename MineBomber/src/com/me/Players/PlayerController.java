package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.Graphics.ShapeProgressBar;
import com.me.Map.MapManager;


import java.util.ArrayList;

/**
 * Created by alekseev on 26.03.2014.
 */
public class PlayerController {

    static public ArrayList<AbstractPlayer> players=new ArrayList<AbstractPlayer>();

    static FightInputProcessor inputProcessor1;
    static FightInputProcessor inputProcessor2;

    static ShapeProgressBar shapeProgressBar;

    public static void Initialize()
    {
        //Gdx.input.setInputProcessor(new FightInputProcessor());
        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();
        inputProcessor1=new FightInputProcessor(new Rectangle(0,0,w*0.25f,h));
        inputProcessor2=new FightInputProcessor(new Rectangle(w*0.75f,0,w*0.25f,h));

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(inputProcessor1);
        im.addProcessor(inputProcessor2);
        Gdx.input.setInputProcessor(im);

        //AiPlayer inputPlayer=new AiPlayer();
        //inputProcessor.setListener(inputPlayer);
        Add(new Player("First player", inputProcessor2, new Vector2(0, 10)));
        Add(new Player("Second player", inputProcessor1, new Vector2(100,10)));

      //
      //
     // Add(new AiPlayer(0,new Vector2(100,400)));
       // Add(new AiPlayer(1,new Vector2(400,300)));

        //Add(new AiPlayer(0,new Vector2(200,400)));
        //Add(new AiPlayer(1,new Vector2(500,300)));

        //Add(new AiPlayer(0,new Vector2(300,400)));
       // Add(new AiPlayer(1,new Vector2(600,300)));

       // Add(new AiPlayer(0,new Vector2(400,400)));
       // Add(new AiPlayer(1,new Vector2(700,300)));

        //Add(new AiPlayer(0,new Vector2(500,400)));
        //Add(new AiPlayer(1,new Vector2(800,300)));

        shapeProgressBar=new ShapeProgressBar();

        //Add(new AiPlayer(inputProcessor,new Vector2(200,10)));
        //Add(new AiPlayer(inputProcessor,new Vector2(300,10)));
        //Add(new AiPlayer(inputProcessor,new Vector2(400,10)));
        //Add(new AiPlayer(inputProcessor,new Vector2(500,10)));
        //Add(new AiPlayer(inputProcessor,new Vector2(600,10)));
    }

    public static IPlayer GetPlayer(int index)
    {
        return players.get(index);
    }

    public static void Add(AbstractPlayer player)
    {
        players.add(player);
    }

    public static void Render(SpriteBatch sb)
    {
        IPlayer player;
        for(int i=0;i<players.size();i++) {
            player=players.get(i);
            player.Render(sb);
        }
    }

    public static void Calculate(long time){
        AbstractPlayer player;
        for(int i=0;i<players.size();i++) {
            player=players.get(i);
            player.calculate(time);
        }

        MapManager.applyDamage(time);
    }

    public static  void AfterBatch(Matrix4 projectionMatrix)
    {
        shapeProgressBar.Draw(players, projectionMatrix);
    }



}
