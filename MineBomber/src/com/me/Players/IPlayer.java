package com.me.Players;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayer {

    void Render();
    boolean isVisible();
    Sprite getPlayerSprite();

}
