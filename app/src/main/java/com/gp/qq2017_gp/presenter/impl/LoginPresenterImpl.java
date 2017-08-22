package com.gp.qq2017_gp.presenter.impl;

import com.gp.qq2017_gp.presenter.LoginPresenter;
import com.gp.qq2017_gp.view.LoginView;
import com.gp.qq2017_gp.wrapper.EMCallBackWrapper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Created by GP on 2017/8/5.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(final String name, final String pwd) {

        EMClient.getInstance().login(name, pwd, new EMCallBackWrapper() {
            @Override
            public void onSuccessMain() {
                loginView.onLogin(name, pwd, true, "");
            }

            @Override
            public void onErrorMain(int i, String s) {
                loginView.onLogin(name, pwd, false, "登录失败：" + s);
            }
        });

    }
}
