package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.Bombs.AnimatedSprite;
import com.me.Bombs.AnimatedSpriteAnimator;
import com.me.TextManager.TextManager;
import com.me.controlers.ActionController;
import com.me.controlers.actions.ActivateBombAction;
import com.me.controlers.actions.PutBombAction;
import com.me.minebomber.MemoryManager;
import com.me.minebomber.Settings;

import java.util.Calendar;

/**
 * Created by alekseev on 20.03.2014.
 */
public class Player extends AbstractPlayer implements IPlayerControls {

    public Player(String mName, IListenerRegistration registration, Vector2 position) {
        super(mName);
        AnimatedSprite animatedSprite = AnimatedSprite.FactoryMethos.CreatePlayer(Settings.PLAYER_SKIN);

        X = position.x;
        Y = position.y;

        sprite = MemoryManager.take(AnimatedSpriteAnimator.class).update(animatedSprite);
        registration.setListener(this);
    }

    public void ChangeMoveDirection(Vector2 vec) {
        v=vec;
    }

    public void PlaceBomb() {
        if(mDie) return;

        AbstractBomb bomb = arsenal.PutBomb(this, new Vector2(X, Y));

        arsenalInfo.DoItVisible();

        if (bomb != null) {
            bombList.add(bomb);
            PutBombAction take = MemoryManager.take(PutBombAction.class).update(this, Calendar.getInstance().getTime().getTime(), bomb);
            ActionController.Add(take);
        }

    }

    public void DetonateBomb() {
        if(mDie) return;
        long time=Calendar.getInstance().getTimeInMillis();
        ActivateBombAction take = MemoryManager.take(ActivateBombAction.class).update(this, time);
        ActionController.Add(take);
    }

    @Override
    public void onDoubleTap() {
        //Log.d(String.format("%s: onDoubleTap", this.getName()));
        DetonateBomb();
    }

    @Override
    public void onDoubleSwipe(Vector2 v) {
        arsenal.addIndex();
        TextManager.Add("CurBomb: " + arsenal.sindex, Color.GRAY, X, Y);
        arsenalInfo.DoItVisible();
        //Log.d(String.format("%s: onDoubleSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onTap() {
        //Log.d(String.format("%s: onTap", this.getName()));
        PlaceBomb();
    }

    @Override
    public void onSwipe(Vector2 v) {
        //Log.d(String.format("%s: onSwipe(%s)", this.getName(), v.toString()));
    }

    @Override
    public void onPan(Vector2 v) {
        //Log.d(String.format("%s: onPan(%s)", this.getName(), v.toString()));
        ChangeMoveDirection(v);
    }

    @Override
    public void onDoublePan(Vector2 v) {
        //Log.d(String.format("%s: onDoublePan(%s)", this.getName(), v.toString()));
    }


}
