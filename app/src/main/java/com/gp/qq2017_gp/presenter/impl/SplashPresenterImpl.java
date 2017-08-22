package com.gp.qq2017_gp.presenter.impl;

import com.gp.qq2017_gp.presenter.SplashPresenter;
import com.gp.qq2017_gp.view.SplashView;
import com.hyphenate.chat.EMClient;

/**
 * Created by GP on 2017/8/2.
 */

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView mView;

    public SplashPresenterImpl(SplashView view) {
        this.mView = view;
    }

    @Override
    public void checkLogin() {
        boolean isLogin = EMClient.getInstance().isLoggedInBefore() && EMClient.getInstance().isConnected();
        mView.onCheckLogin(isLogin);
    }
}
