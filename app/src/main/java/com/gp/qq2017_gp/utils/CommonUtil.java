package com.gp.qq2017_gp.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.gp.qq2017_gp.application.QQApplication;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by GP on 2017/8/2.
 */

public class CommonUtil {

    public static String getAppName(int pid) {
        String processName = null;
        ActivityManager am = (ActivityManager) QQApplication.getAppContext().getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

}
