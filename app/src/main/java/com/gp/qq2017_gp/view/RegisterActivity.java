package com.gp.qq2017_gp.view;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gp.qq2017_gp.R;
import com.gp.qq2017_gp.presenter.RegisterPresenter;
import com.gp.qq2017_gp.presenter.impl.RegisterPresenterImpl;
import com.gp.qq2017_gp.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegisterView, TextView.OnEditorActionListener {

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.til_username)
    TextInputLayout tilUsername;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.btn_regist)
    Button btnRegist;

    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);

        init();

    }

    private void init() {
        presenter = new RegisterPresenterImpl(this);
        etPwd.setOnEditorActionListener(this);
    }


    @OnClick(R.id.btn_regist)
    public void onClick() {
        register();
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        if (textView.getId() == R.id.et_pwd) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                register();
                return true;
            }
        }

        return false;
    }

    private void register() {
        String username = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (!StringUtils.checkUserName(username)) {
            tilUsername.setErrorEnabled(true);
            tilUsername.setError("用户名不合法");

            etUsername.requestFocus(View.FOCUS_RIGHT);

            return;
        } else {
            tilUsername.setErrorEnabled(false);
        }
        if (!StringUtils.checkPwd(pwd)) {
            tilPwd.setErrorEnabled(true);
            tilPwd.setError("密码不合法");

            etPwd.requestFocus(View.FOCUS_RIGHT);
            return;
        } else {
            tilPwd.setErrorEnabled(false);
        }
        showDialog("正在注册...");
        presenter.register(username, pwd);
    }

    @Override
    public void onRegister(String userName, String pwd, boolean isSuccess, String message) {
        hideDialog();
        if (isSuccess) {
            /**
             * 将注册成功的数据保存到本地
             *
             * 跳转到登录界面
             */
            showToast("注册成功！去玩耍吧~");
            saveUserInfo(userName, pwd);
            startActivity(LoginActivity.class, true);
        } else {
            showToast(message);
        }
    }
}
