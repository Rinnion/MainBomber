package com.menuengine;

import com.menuengine.MenuElement;

/**
 * Created by alekseev on 07.10.2014.
 */
public interface MenuCallback {
    public void checked(MenuElement element,boolean value);
    public void buttonDown(MenuElement element);

}
