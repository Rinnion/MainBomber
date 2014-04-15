package com.me.Players;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayerControls {
    //void ChangeMoveDirection(Vector2 vec);
    //void PlaceBomb();
    //void DetonateBomb();

    void onDoubleTap();
    void onDoubleSwipe(Vector2 v);
    void onTap();
    void onSwipe(Vector2 v);
    void onPan(Vector2 v);
    void onDoublePan(Vector2 v);
}
