package com.me.logger;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 18.02.14
 * Time: 8:30
 * To change this template use File | Settings | File Templates.
 */
public class Log {
    private static Logger logger;

    public static void Initialize(String logTagName) {
        int mLogLevel = Application.LOG_DEBUG;
        Gdx.app.setLogLevel(mLogLevel);
        logger = new Logger(logTagName, mLogLevel);
    }

    public static void e(String s) {
        if (logger != null) logger.e(s);
    }

    public static void i(String s) {
        if (logger != null) logger.i(s);
    }

    public static void d(String s) {
        if (logger != null) logger.d(s);
    }
}

class LogManager {
    public static Logger getCurrentClssLogger() {
        String className = new Exception().getStackTrace()[1].getClassName();
        return new Logger(className, getLoggerLevel(className));
    }

    private static int getLoggerLevel(String className) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        if (hashMap.containsKey(className)) return hashMap.get(className);

        if (className == "" || className == null) return Application.LOG_NONE;

        String[] split = className.split(".");
        int l = split.length;

        for (int i = l; i > 0; i--) {

        }
        return Application.LOG_NONE;
    }
}

class Logger {
    private String tag;
    private int mLogLevel;

    public Logger(String tag, int level) {
        this.tag = tag;
        mLogLevel = level;
    }

    public void e(String s) {
        if (mLogLevel > Application.LOG_ERROR) Gdx.app.log(tag, s);
    }

    public void i(String s) {
        if (mLogLevel > Application.LOG_INFO) Gdx.app.log(tag, s);
    }

    public void d(String s) {
        if (mLogLevel > Application.LOG_DEBUG) Gdx.app.log(tag, s);
    }
}
