package com.minebomber.MenuManager;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by alekseev on 30.12.2014.
 */
public interface IMenu {
    String getName();

    Stage getStage();

    void applyShowActions();
    void SetText(String name,String text);
    String GetTag();
}
