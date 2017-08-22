package com.gp.qq2017_gp.wrapper;

import com.gp.qq2017_gp.utils.ThreadUtils;
import com.hyphenate.EMCallBack;

/**
 * Created by GP on 2017/8/15.
 */

public abstract class EMCallBackWrapper implements EMCallBack {

    public abstract void onSuccessMain();

    public abstract void onErrorMain(int i, String s);

    public void onProgressMain(int i, String s) {

    }

    @Override
    public void onSuccess() {
        ThreadUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                onSuccessMain();
            }
        });
    }

    @Override
    public void onError(final int i, final String s) {
        ThreadUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                onErrorMain(i, s);
            }
        });
    }

    @Override
    public void onProgress(final int i, final String s) {
        ThreadUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                onProgressMain(i, s);
            }
        });
    }
}
