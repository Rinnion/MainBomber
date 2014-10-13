package com.me.Graphics;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by alekseev on 01.04.2014.
 */
public interface ILifeProgressBar {
    Color colorBack();
    Color colorFront();

    boolean visibleLifeBar();

    void Draw();
    void DoItVisible();


}
