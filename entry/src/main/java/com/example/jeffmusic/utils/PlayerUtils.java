package com.example.jeffmusic.utils;

import com.example.jeffmusic.MyApplication;

public class PlayerUtils {
    public static boolean isEmptyString(String str) {
        return str == null || str.equals("");
    }

    public static boolean isLogin() {
        return !isEmptyString(MyApplication.getInstance().getToken());
    }
}
