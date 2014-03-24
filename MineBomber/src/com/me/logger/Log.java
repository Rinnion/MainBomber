package com.me.logger;

import com.badlogic.gdx.Gdx;

/**
 * Created with IntelliJ IDEA.
 * User: alekseev
 * Date: 18.02.14
 * Time: 8:30
 * To change this template use File | Settings | File Templates.
 */
public class Log {
    private static String mTAG_LOG = "";

    public static void Initialize(String logTagName)
    {

        mTAG_LOG=logTagName;
    }

    public  static   void info(String s)
    {
        Gdx.app.log( mTAG_LOG, s );
    }

    public static  void debug(String s)
    {
        info(s);
    }

    public static  void error(String s)
    {
        info(s);
    }

    public static  void warrning(String s)
    {
        info(s);
    }

    public static void e(String s)
    {
        error(s);
    }

    public static void i(String s)
    {
        info(s);
    }
    public static void w(String s)
    {
        warrning(s);
    }
    public static void d(String s)
    {
        debug(s);
    }
}
