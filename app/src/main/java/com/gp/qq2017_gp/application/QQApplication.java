package com.gp.qq2017_gp.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.gp.qq2017_gp.utils.CommonUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import cn.bmob.v3.Bmob;


/**
 * Created by GP on 2017/8/1.
 */

public class QQApplication extends Application {

    private static final String TAG = "QQApplication";

    private static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = this;
        initHuanXin();
        initBmob();

    }


    private void initHuanXin() {

        int pid = android.os.Process.myPid();
        String processAppName = CommonUtil.getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //初始化
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
    }

    private void initBmob() {
        Bmob.initialize(this, "bb8663c12ea529c6c20e6d2e57a034bf");
    }
}
