package com.me.Players;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayerControls {
    void ChangeMoveDirection(Vector2 vec);
    void PlaceBomb();
    void DetonateBomb();
}
