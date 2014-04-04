package com.me.Players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.IBomb;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayer {

    float GetDmgRadius();
    float GetGoRadius();
    float GetDigDmg();


    float GetMaxLife();
    float GetLife();


    boolean isVisible();
    //boolean isVisibleLife();

    LifeProgressBar GetLifeBar();
    void DealDamage(IBomb bomb);

    void Render(Batch batch);

    float getX();
    float getY();
    float getH();
    float getW();




}
