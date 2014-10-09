package com.menuengine;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;

/**
 * Created by alekseev on 09.10.2014.
 */
public class CheckBoxListener extends ButtonListener {
    private CheckBox checkBox;
    public CheckBoxListener(MenuElement element, CheckBox checkBox, MenuCallback callback) {
        super(element, callback);
        this.checkBox=checkBox;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        menuCallback.checked (curElement,checkBox.isChecked());
    }
}
