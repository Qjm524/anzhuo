package com.example.foodordering;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USER_TYPE = "user_type";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_USERNAME = "username";

    public static final int USER_TYPE_GUEST = 0;
    public static final int USER_TYPE_NORMAL = 1;
    public static final int USER_TYPE_ADMIN = 2;

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void login(Context context, int userType, String username) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_TYPE, userType);
        editor.putString(KEY_USERNAME, username);
        editor.apply();
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(KEY_IS_LOGGED_IN, false);
        editor.putInt(KEY_USER_TYPE, USER_TYPE_GUEST);
        editor.putString(KEY_USERNAME, "");
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        return getPrefs(context).getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static int getUserType(Context context) {
        return getPrefs(context).getInt(KEY_USER_TYPE, USER_TYPE_GUEST);
    }

    public static String getUsername(Context context) {
        return getPrefs(context).getString(KEY_USERNAME, "");
    }

    public static boolean isAdmin(Context context) {
        return getUserType(context) == USER_TYPE_ADMIN;
    }

    public static boolean isNormalUser(Context context) {
        return getUserType(context) == USER_TYPE_NORMAL;
    }
}