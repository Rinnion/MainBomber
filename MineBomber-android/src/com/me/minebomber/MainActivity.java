package com.me.minebomber;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AndroidApplication {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Logger logger = LoggerFactory.getLogger(MainActivity.class);
        logger.info("Start game...");
        logger.debug("Debug game...");

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        //cfg.useGL20 = true;
        cfg.numSamples=2;
        initialize(new MineBomberApplication(), cfg);
    }
}