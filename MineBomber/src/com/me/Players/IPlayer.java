package com.me.Players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.me.bomb.activator.DestinationActivator;

/**
 * Created by alekseev on 26.03.2014.
 */
public interface IPlayer {

    float GetMaxLife();
    float GetLife();
    LifeProgressBar GetLifeBar();
    void Render(Batch batch);
    void DealDamage(int dmg);
    void activateBombs(long time);

    void addMoney(long value);
    Arsenal getArsenal();

    void addActivator(DestinationActivator activator);

    boolean isDead();
}
