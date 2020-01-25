package com.example.app4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class PrefManager {
    private static final String SHARED_PREF_NAME = "prodevsblogpref";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_ID = "user_id";
    private static final String KEY_NAMELECTURER = "lecturer_name";
    private static final String KEY_EMAILLECTURER = "lecturer_email";
    private static final String KEY_IDLECTURER = "lecturer_id";
    private static final String KEY_NAMEADMIN = "admin_name";
    private static final String KEY_EMAILADMIN = "admin_email";
    private static final String KEY_IDADMIN = "admin_id";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static PrefManager mInstance;
    private static Context mCtx;

    private PrefManager (Context context) {
        mCtx = context;
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }
    public void setUserLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
    }
    public void setAdminLogin(Admin admin) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_IDADMIN, admin.getId());
        editor.putString(KEY_NAMEADMIN, admin.getUsername());
        editor.putString(KEY_EMAILADMIN, admin.getEmail());
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
    }
    public void setLecturerLogin(Lecturer lecturer) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_IDLECTURER, lecturer.getId());
        editor.putString(KEY_NAMELECTURER, lecturer.getName());
        editor.putString(KEY_EMAILLECTURER, lecturer.getEmail());
        editor.putBoolean(KEY_IS_LOGGED_IN,true);
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
