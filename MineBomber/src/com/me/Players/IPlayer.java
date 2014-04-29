package com.me.Players;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayer {

    float GetDmgRadius();
    float GetGoRadius();
    int GetDigDmg();

    float GetMaxLife();
    float GetLife();

    String getName();


    boolean isVisible();
    //boolean isVisibleLife();

    LifeProgressBar GetLifeBar();

    void Render(Batch batch);

    void DealDamage(int dmg);

    float getX();
    float getY();
    float getH();
    float getW();




}
