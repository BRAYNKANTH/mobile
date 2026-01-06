package com.example.sllifeline.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private static final String PREF_NAME = "sllifeline_session";
    private static final String KEY_LOGIN = "is_logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE = "role";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void createSession(int userId, String role) {
        editor.putBoolean(KEY_LOGIN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_ROLE, role);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_LOGIN, false);
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public String getRole() {
        return prefs.getString(KEY_ROLE, null);
    }

    public void logout() {
        editor.clear();
        editor.apply();
    }
}
