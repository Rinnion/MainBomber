package com.menuengine;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by alekseev on 09.10.2014.
 */
public class ButtonListener extends ClickListener {

    MenuElement curElement;
    MenuCallback menuCallback;

    public ButtonListener(MenuElement element,MenuCallback callback)
    {
        curElement=element;
        menuCallback=callback;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        menuCallback.buttonDown(curElement);
    }
}
