package com.me.Players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.me.Graphics.ShapeProgressBar;
import com.me.minebomber.MineBomber;

import java.util.ArrayList;

/**
 * Created by alekseev on 26.03.2014.
 */
public class PlayerController {

    static public ArrayList<AbstractPlayer> players=new ArrayList<AbstractPlayer>();
    public static int hotSeatPlayers = 0;
    static FightInputProcessor []inputProcessor;
    static ShapeProgressBar shapeProgressBar;

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
        //TODO think about place for winning controller
        int alive = players.size();
        for(int i=0;i<players.size();i++) {
            player=players.get(i);
            if (player.isDead()) alive--;
            player.calculate(time);
        }
        if (alive == 1) Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                MineBomber.getFSM().doIt(MineBomber.Inputs.close);
            }
        });
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
