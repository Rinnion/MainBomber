package com.me.Players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.me.Bombs.AbstractBomb;
import com.me.controlers.treasure.AbstractTreasure;

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

    void removebomb(AbstractBomb bomb);


    float getX();
    float getY();
    float getH();
    float getW();


    Vector2 GetOrigin();

    Arsenal GetArsenal();


    void addMoney(long value);

}
