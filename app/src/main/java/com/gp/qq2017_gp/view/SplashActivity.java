package com.gp.qq2017_gp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.ImageView;

import com.gp.qq2017_gp.R;
import com.gp.qq2017_gp.presenter.SplashPresenter;
import com.gp.qq2017_gp.presenter.impl.SplashPresenterImpl;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SplashActivity extends BaseActivity implements SplashView {

    @InjectView(R.id.iv_splash)
    ImageView ivSplash;


    private SplashPresenter mSplashPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.inject(this);

        mSplashPresenter = new SplashPresenterImpl(this);

        /**
         * 1.判断是否登录了
         *
         * 2.如果登录了，直接进入mainactivity
         *
         * 3.否则2秒后进入登录页面
         *
         */

        mSplashPresenter.checkLogin();

    }

    @Override
    public void onCheckLogin(boolean isLogin) {
        if (isLogin) {
            startActivity(MainActivity.class, true);
        } else {
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(ivSplash, "alpha", 0.0f, 1.0f).setDuration(1500L);
            alphaAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    startActivity(LoginActivity.class, true);
                }
            });

            alphaAnimator.start();

        }
    }
}
