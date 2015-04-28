package com.me.TextManager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by alekseev on 15.04.2014.
 */
public class TextManager {

    private static final int PoolSize = 200;
    static Logger logger = LoggerFactory.getLogger(TextManager.class);

    private static TextPool[] pool;

    private static Object objSync = new Object();


    public static void Initialize() {
        pool = new TextPool[PoolSize];
        for (int i = 0; i < PoolSize; i++) {
            pool[i] = new TextPool();
            pool[i].isFree = true;
        }
    }

    private static TextPool getFree() {
        logger.trace("synchronized (objSync) TextManager.getFree");
        for (int i = 0; i < PoolSize; i++) {
            if (pool[i].isFree) {
                if (pool[i].text == null)
                    pool[i].text = new TextZoom();
                pool[i].isFree = false;
                return pool[i];
            }
        }
        return null;
    }

    public static void free(TextPool item) {
        synchronized (objSync) {
            logger.trace("synchronized (objSync) TextManager.free");
            item.isFree = true;
        }

    }

    public static void Add(String text, Color color, float x, float y) {
        TextPool textPool = getFree();

        if (textPool == null) {
            logger.error("Error TextManager can't get free TextOut");
            return;
        }

        IText outText = textPool.text;

        outText.SetColor(color);
        outText.SetText(text);
        outText.SetPosition(x, y);
    }

    public static void Draw(SpriteBatch sb) {
        logger.trace("synchronized (objSync) TextManager.Draw");

        for (int i = 0; i < PoolSize; i++) {
            if (!pool[i].isFree) {
                if (!pool[i].text.Draw(sb))
                    free(pool[i]);
            }
        }
    }

    public static void Done() {
        pool = null;
    }
}
