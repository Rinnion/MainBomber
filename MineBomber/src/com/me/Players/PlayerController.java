package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by alekseev on 26.03.2014.
 */
public class PlayerController {

    static private ArrayList<IPlayer> players=new ArrayList<IPlayer>();

    static FightInputProcessor inputProcessor1;
    static FightInputProcessor inputProcessor2;
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

        //Player inputPlayer=new Player();
        //inputProcessor.setListener(inputPlayer);
        Add(new Player(inputProcessor2, new Vector2(0, 10)));
        Add(new Player(inputProcessor1, new Vector2(100,10)));
        //Add(new Player(inputProcessor,new Vector2(200,10)));
        //Add(new Player(inputProcessor,new Vector2(300,10)));
        //Add(new Player(inputProcessor,new Vector2(400,10)));
        //Add(new Player(inputProcessor,new Vector2(500,10)));
        //Add(new Player(inputProcessor,new Vector2(600,10)));
    }

    public static void Add(IPlayer player)
    {
        players.add(player);
    }

    public static void Render(SpriteBatch sb)
    {
        IPlayer player;
        for(int i=0;i<players.size();i++) {
            player=players.get(i);
            player.Render();
            if(player.isVisible())
                player.getPlayerSprite().draw(sb);

        }
    }



}
