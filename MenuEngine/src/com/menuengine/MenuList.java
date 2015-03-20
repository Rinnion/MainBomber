package com.menuengine;

import java.util.ArrayList;

/**
 * Created by alekseev on 01.10.2014.
 */
public class MenuList {
        public ArrayList<MenuElement> elements;

        public String name;
        public String prevName;

        public MenuList()
        {
            elements=new ArrayList(5);
        }

        public MenuList(String name,String prevName)
        {
            this();
            this.name=name;
            this.prevName=prevName;
        }

        public static final int EN_TYPE_CAPTION=0;
        public static final int EN_TYPE_BUTTON=1;
        public static final int EN_TYPE_CHECK=2;
        public static final int EN_TYPE_TEXTBOX=3;
    public static final int EN_TYPE_LIST=4;



}
