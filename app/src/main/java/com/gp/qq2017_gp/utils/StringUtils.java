package com.gp.qq2017_gp.utils;

import android.text.TextUtils;

/**
 * Created by GP on 2017/8/5.
 */

public class StringUtils {

    public static boolean checkUserName(String userName) {

        if (TextUtils.isEmpty(userName)) {
            return false;
        }

        return userName.matches("^[a-zA-Z]\\w{2,19}$");
    }

    public static boolean checkPwd(String pwd) {
        if (TextUtils.isEmpty(pwd)) {
            return false;
        }
        return pwd.matches("^[a-zA-Z0-9]{3,20}$");
    }
}
