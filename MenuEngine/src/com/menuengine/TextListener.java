package com.menuengine;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by alekseev on 09.10.2014.
 */
public class TextListener extends InputListener {
    MenuElement curElement;

    public TextListener(MenuElement element)
    {
        curElement=element;
    }

    @Override
    public boolean keyDown(InputEvent event, int keycode) {
        return super.keyDown(event, keycode);
    }
}
