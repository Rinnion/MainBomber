package com.me.ThreedPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by alekseev on 26.03.2014.
 */
public class ThreedPool {

    static final int cpus = Runtime.getRuntime().availableProcessors();
    static final int  scaleFactor=2;
    static final int maxThreads = cpus * scaleFactor;

    static ExecutorService mExecutorService;

    public static void Initialize()
    {
        mExecutorService =

                new ThreadPoolExecutor(

                        maxThreads, // core thread pool size

                        maxThreads, // maximum thread pool size

                        1, // time to wait before resizing pool

                        TimeUnit.SECONDS,

                        new ArrayBlockingQueue<Runnable>(maxThreads, true),

                        new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void Execute()
    {
        //mExecutorService.execute(new ParticleCallback());
    }


}
