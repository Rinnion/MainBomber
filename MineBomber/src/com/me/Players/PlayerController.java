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

    static FightInputProcessor []inputProcessor;

    static ShapeProgressBar shapeProgressBar;
    public static int hotSeatPlayers =0;
    public static void Initialize()
    {
        float h = Gdx.graphics.getHeight();
        float w = Gdx.graphics.getWidth();

        inputProcessor=new FightInputProcessor[hotSeatPlayers];



        if(hotSeatPlayers>=2) {
            inputProcessor[0] = new FightInputProcessor(new Rectangle(0, 0, w * 0.25f, h));
            inputProcessor[1] = new FightInputProcessor(new Rectangle(w * 0.75f, 0, w * 0.25f, h));
        }


        if(hotSeatPlayers>=3)
        inputProcessor[2]=new FightInputProcessor(new Rectangle(w*0.25f,h*0.5f,w*0.75f,h));

        if(hotSeatPlayers>=4)
            inputProcessor[3]=new FightInputProcessor(new Rectangle(w*0.25f,h*0f,w*0.75f,h*0.5f));



        InputMultiplexer im = new InputMultiplexer();
        for(int i=0;i<hotSeatPlayers;i++) {
            im.addProcessor(inputProcessor[i]);
            Vector2 plPos=null;

            switch (i)
            {
                case 0:
                    plPos=new Vector2(10,10);
                    break;
                case 1:
                    plPos=new Vector2(700,10);
                    break;
                case 2:
                    plPos=new Vector2(10,580);
                    break;
                case 3:
                    plPos=new Vector2(700,580);
                break;

            }
            Add(new Player("player" + i, inputProcessor[i], plPos));

        }


            Gdx.input.setInputProcessor(im);
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
            if(bomb!=null) {
                //FIXME 13.10.2014 commented
                //player.removebomb(bomb);
            }
        }
    }

    public static  void AfterBatch(Matrix4 projectionMatrix)
    {

        shapeProgressBar.Draw(players, projectionMatrix);



    }

    public static void addMoney(IPlayer who, long value){

        who.addMoney(value);
        //TextManager.Add("+" +value + "", Color.YELLOW, who.getX(), who.getY());

    }

    public static void addRandomWeapon(IPlayer who)
    {
        int cb=who.getArsenal().AddRandom();
        //TextManager.Add("+" +cb + "", Color.GREEN ,who.getX(),who.getY());
    }

    public static void removeMoney(IPlayer who, long value){
        who.addMoney(- value);
    }

}
