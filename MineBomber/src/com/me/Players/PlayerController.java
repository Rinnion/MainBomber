package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Graphics.ShapeProgressBar;
import com.me.Map.MapManager;
import com.me.ObjectMaskHelper.Vector2I;
import com.me.TextManager.TextManager;
import com.me.controlers.TreasureController;
import com.me.controlers.treasure.SmallChestTreasure;
import com.me.minebomber.AbstractGameObject;


import java.util.ArrayList;
import java.util.Random;

/**
 * Created by alekseev on 26.03.2014.
 */
public class PlayerController {

    static public ArrayList<AbstractPlayer> players=new ArrayList<AbstractPlayer>();

    static FightInputProcessor inputProcessor1;
    static FightInputProcessor inputProcessor2;
    static FightInputProcessor inputProcessor3;
    static ShapeProgressBar shapeProgressBar;

    public static void Initialize()
    {
        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();
        inputProcessor1=new FightInputProcessor(new Rectangle(0,0,w*0.25f,h));
        inputProcessor2=new FightInputProcessor(new Rectangle(w*0.75f,0,w*0.25f,h));
        inputProcessor3=new FightInputProcessor(new Rectangle(w*0.25f,h*0.5f,w*0.75f,h));

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(inputProcessor1);
        im.addProcessor(inputProcessor2);
        im.addProcessor(inputProcessor3);
        Gdx.input.setInputProcessor(im);

        Add(new Player("First player", inputProcessor2, new Vector2(700, 10)));
        Add(new Player("Second player", inputProcessor1, new Vector2(10,10)));
        Add(new Player("Second player3", inputProcessor3, new Vector2(300,300)));


        shapeProgressBar=new ShapeProgressBar();
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
    }

    public static void RemoveObject(AbstractGameObject object)
    {
        AbstractPlayer player;
        AbstractBomb bomb=(object instanceof AbstractBomb ? (AbstractBomb)object : null);

        for(int i=0;i<players.size();i++) {
            player=players.get(i);
            if(bomb!=null)
            player.removebomb(bomb);
        }
    }

    public static  void AfterBatch(Matrix4 projectionMatrix)
    {

        shapeProgressBar.Draw(players, projectionMatrix);



    }

    public static void addMoney(IPlayer who, long value){

        who.addMoney(value);
        TextManager.Add("+" +value + "", Color.YELLOW,who.getX(),who.getY());

    }

    public static void removeMoney(IPlayer who, long value){
        who.addMoney(- value);
    }

}
