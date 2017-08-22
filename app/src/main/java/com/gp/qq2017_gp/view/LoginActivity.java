package com.gp.qq2017_gp.view;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gp.qq2017_gp.R;
import com.gp.qq2017_gp.presenter.LoginPresenter;
import com.gp.qq2017_gp.presenter.impl.LoginPresenterImpl;
import com.gp.qq2017_gp.utils.StringUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView, TextView.OnEditorActionListener {

    private static final int REQUEST_CODE = 1;

    @InjectView(R.id.et_username)
    EditText etUsername;
    @InjectView(R.id.til_username)
    TextInputLayout tilUsername;
    @InjectView(R.id.et_pwd)
    EditText etPwd;
    @InjectView(R.id.til_pwd)
    TextInputLayout tilPwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    @InjectView(R.id.tv_newuser)
    TextView tvNewuser;

    private LoginPresenter mLoginPresenter;

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

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        init();
    }

    private void init() {
        etPwd.setOnEditorActionListener(this);

        //取本地存储
        etUsername.setText(getUserName());
        etPwd.setText(getPassWord());

        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //注册成功后的数据回显
        etUsername.setText(getUserName());
        etPwd.setText(getPassWord());
    }

    @OnClick({R.id.btn_login, R.id.tv_newuser})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_newuser:
                super.startActivity(RegisterActivity.class, false);
                break;
        }
    }


    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

        if (textView.getId() == R.id.et_pwd) {
            if (i == EditorInfo.IME_ACTION_DONE)
                login();
            return true;
        }

        return false;
    }

    private void login() {

        String username = etUsername.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (StringUtils.checkUserName(username)) {
            tilUsername.setErrorEnabled(false);
        } else {
            tilUsername.setErrorEnabled(true);
            tilUsername.setError("好好打用户名。。。");
            etUsername.requestFocus(EditText.FOCUS_RIGHT);
        }

        if (StringUtils.checkPwd(pwd)) {
            tilPwd.setErrorEnabled(false);
        } else {
            tilPwd.setErrorEnabled(true);
            tilUsername.setError("好好打密码。。。");
            etPwd.requestFocus(EditText.FOCUS_RIGHT);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            return;//这里先return 回调方法会再次调用login方法
        }

        showDialog("使劲登！！");

        mLoginPresenter.login(username, pwd);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (REQUEST_CODE == requestCode) {
            if (grantResults[0] == PermissionChecker.PERMISSION_GRANTED) {
                login();
            } else {
                showToast("不让你登");
            }
        }
    }

    @Override
    public void onLogin(String name, String pwd, boolean isSuccess, String message) {
        hideDialog();
        if (isSuccess) {
            saveUserInfo(name, pwd);
            startActivity(MainActivity.class, true);
        } else {
            showToast("登录失败：" + message);
        }
    }
}
