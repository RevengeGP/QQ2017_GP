package com.gp.qq2017_gp.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by GP on 2017/8/14.
 */

public class ThreadUtils {

    private static Executor executor = Executors.newSingleThreadExecutor();

    private static Handler handler = new Handler(Looper.getMainLooper());//主线程的handler

    public static void runOnThread(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runOnUIThread(Runnable runnable) {
        handler.post(runnable);
    }


}
