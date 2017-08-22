package com.gp.qq2017_gp.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.gp.qq2017_gp.model.User;
import com.gp.qq2017_gp.utils.Constant;
import com.gp.qq2017_gp.utils.ToastUtils;

/**
 * Created by GP on 2017/8/2.
 */

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(true);

        mPreferences = getSharedPreferences("data", MODE_PRIVATE);
    }

    public void startActivity(Class clazz, boolean isFinishCurPage) {
        startActivity(new Intent(this, clazz));
        if (isFinishCurPage) {
            finish();
        }
    }

    public void showDialog(String msg) {
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    public void hideDialog() {
        mProgressDialog.hide();
    }

    protected void saveUserInfo(String userName, String pwd) {
        mPreferences
                .edit()
                .putString("", userName)
                .putString("", pwd)
                .commit();
    }

    protected String getUserName() {
        return mPreferences.getString(Constant.SP_KEY_USERNAME, "");
    }

    protected String getPassWord() {
        return mPreferences.getString(Constant.SP_KEY_PWD, "");
    }

    protected void showToast(String msg) {
        ToastUtils.showToast(this, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }
}
