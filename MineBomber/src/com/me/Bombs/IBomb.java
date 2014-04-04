package com.me.Bombs;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.Players.IPlayer;

/**
 * Created by alekseev on 27.03.2014.
 */
public interface IBomb {
    void Render(Batch sb);
    void Activate();
    void ImmediatelyDetonate();
    void ImmediatelyDetonate(long activationTime);
    IPlayer GetOwner();
    long GetActivationTime();

    BombProperty GetProperty();

    float getX();
    float getY();
    float getW();
    float getH();



}
