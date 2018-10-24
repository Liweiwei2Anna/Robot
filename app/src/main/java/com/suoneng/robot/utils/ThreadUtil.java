package com.suoneng.robot.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程工具
 * Created by weiwei.li on 2017/5/9.
 */
public class ThreadUtil {

    public static Looper sLooper = Looper.getMainLooper();
    public static Handler sHandler = new Handler(sLooper);
    private static ExecutorService sExecutor = null;
    private static ThreadPoolExecutor sExecutorPool;

    @SuppressLint({"NewApi", "ObsoleteSdkInt"})
    public static ExecutorService obtainExecutor() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            return (ExecutorService) AsyncTask.THREAD_POOL_EXECUTOR;
        } else {
            if (sExecutor == null) {
                sExecutor = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>());
            }
            return sExecutor;
        }
    }

    public synchronized static ThreadPoolExecutor obtainThreadPool() {
        return initOrResetThreadPool(3, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public synchronized static ThreadPoolExecutor obtainThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                                   TimeUnit unit, BlockingQueue<Runnable> workQueue) {

        return initOrResetThreadPool(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
    }


    private static ThreadPoolExecutor initOrResetThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                                            TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        /*if (corePoolSize < 0 || maximumPoolSize <= 0 || maximumPoolSize < corePoolSize
                || keepAliveTime < 0) {

            //throw new IllegalArgumentException();

        }*/
        if (corePoolSize < 0) {
            corePoolSize = 3;
        }
        if (maximumPoolSize < 0) {
            maximumPoolSize = 5;
        }
        if (keepAliveTime < 0) {
            keepAliveTime = 10;
        }
        if (workQueue == null) {
            //throw new NullPointerException();
            workQueue = new LinkedBlockingQueue<Runnable>();
        }
        if (sExecutorPool == null) {
            sExecutorPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                    unit, workQueue);
        } else {
            sExecutorPool.setCorePoolSize(corePoolSize);
            sExecutorPool.setMaximumPoolSize(maximumPoolSize);
            sExecutorPool.setKeepAliveTime(keepAliveTime, unit);
        }
        return sExecutorPool;
    }


    /**
     * 将runnable放在主线程执行,
     *
     * @param con
     * @param delayed 延迟时间
     */
    public static void runOnMainThread(Runnable con, long delayed) {
        sHandler.postDelayed(con, delayed);
    }

    public static void runOnMainThread(Runnable con) {
        if (Thread.currentThread() == sLooper.getThread()) {
            con.run();
        } else {
            sHandler.post(con);
        }
    }

    @SuppressLint("NewApi")
    public static void runOnBackground(Runnable con) {
        obtainExecutor().execute(con);
    }


    /**
     * for some task should not wait in the queue, malloc a new thread for them,
     * FIXME query the idle thread in threadpool first
     *
     * @param con
     * @param noWait
     */
    public static void runOnBackground(Runnable con, boolean noWait) {
        if (noWait) {
            new Thread(con).start();
        } else {
            runOnBackground(con);
        }
    }

    public static void removeRunnable(Runnable launchContinuation) {
        sHandler.removeCallbacks(launchContinuation);
    }

    public static void runOnBackgroundAtFixedRate(Runnable runnable, long period) {
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(runnable, period, period, TimeUnit.MILLISECONDS);
    }
}
