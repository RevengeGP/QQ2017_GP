package com.gp.qq2017_gp.presenter.impl;

import com.gp.qq2017_gp.model.User;
import com.gp.qq2017_gp.presenter.RegisterPresenter;
import com.gp.qq2017_gp.utils.ThreadUtils;
import com.gp.qq2017_gp.view.RegisterView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by GP on 2017/8/5.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView registerView;

    public RegisterPresenterImpl(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void register(final String name, final String pwd) {

        User user = new User();
        user.setUsername(name);
        user.setPassword(pwd);

        user.signUp(new SaveListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null) {//异常为空表示请求成功

                    ThreadUtils.runOnThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //这段代码是同步的 要放到线程中执行
                                EMClient.getInstance().createAccount(name, pwd);

                                //没有报异常就表示请求执行成功（我草。。。）
                                //这里要回到主线程
                                ThreadUtils.runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //通知view执行成功
                                        registerView.onRegister(name, pwd, true, "");
                                    }
                                });
                            } catch (final HyphenateException e1) {
                                e1.printStackTrace();
                                //走到这里说明环信注册用户失败 把bmob上的用户数据删除
                                user.delete();
                                //回到主线程
                                ThreadUtils.runOnUIThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //通知view执行失败
                                        registerView.onRegister(name, pwd, false, e1.getMessage());
                                    }
                                });

                            }
                        }
                    });
                } else {
                    //通知view执行失败
                    registerView.onRegister(name, pwd, false, e.getMessage());
                }
            }
        });

    }
}
