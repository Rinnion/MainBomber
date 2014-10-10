package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.*;
import com.me.TextManager.TextManager;
import com.me.controlers.ActionController;
import com.me.controlers.actions.ActivateBombAction;
import com.me.controlers.actions.PutBombAction;
import com.me.logger.Log;
import com.me.minebomber.MemoryManager;
import com.me.minebomber.Settings;

import java.util.Calendar;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player extends AbstractPlayer implements IPlayerControls {

    public void ChangeMoveDirection(Vector2 vec) {
        v=vec;
    }

    public void PlaceBomb() {
        if(mDie) return;

        AbstractBomb bomb=arsenal.PutBomb(this,new Vector2(sprite.getX() + sprite.getOriginX(),sprite.getY() + sprite.getOriginY()));
        arsenalInfo.DoItVisible();

        if (bomb != null) {
            bombList.add(bomb);
            PutBombAction take = MemoryManager.take(PutBombAction.class);
            take.update(this, Calendar.getInstance().getTime().getTime(), bomb);
            ActionController.Add(take);
        }

    }

    public void DetonateBomb() {
        if(mDie) return;
        long time=Calendar.getInstance().getTimeInMillis();
        ActivateBombAction take = MemoryManager.take(ActivateBombAction.class);
        take.update(this, time);
        ActionController.Add(take);
    }

    @Override
    public void onDoubleTap() {
        Log.d(String.format("%s: onDoubleTap", this.getName()));
        DetonateBomb();
    }

    @Override
    public void onDoubleSwipe(Vector2 v) {
        arsenal.addIndex();
        TextManager.Add("CurBomb: " + arsenal.sindex, Color.GRAY,sprite.getX() + sprite.getOriginX(),sprite.getY() + sprite.getOriginY() );
        arsenalInfo.DoItVisible();
        Log.d(String.format("%s: onDoubleSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onTap() {
        Log.d(String.format("%s: onTap", this.getName()));
        PlaceBomb();
    }

    @Override
    public void onSwipe(Vector2 v) {
        Log.d(String.format("%s: onSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onPan(Vector2 v) {
        Log.d(String.format("%s: onPan(%s)", this.getName(), v.toString()));
        ChangeMoveDirection(v);
    }

    @Override
    public void onDoublePan(Vector2 v) {
        Log.d(String.format("%s: onDoublePan(%s)", this.getName(), v.toString()));
    }

    public Player(String mName, IListenerRegistration registration, Vector2 position)
    {
        super(mName);
        sprite = AnimatedSprite.FactoryMethos.CreatePlayer(Settings.PLAYER_SKIN);
        sprite.setSize(9.5f,9.5f);
        sprite.setOrigin(sprite.getWidth()/2f,sprite.getHeight()/2f);
        sprite.setPosition( position.x - sprite.getOriginX(), position.y - sprite.getOriginY() );
        registration.setListener(this);
    }


}
