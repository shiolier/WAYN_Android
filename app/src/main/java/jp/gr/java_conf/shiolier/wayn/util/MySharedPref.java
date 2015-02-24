package jp.gr.java_conf.shiolier.wayn.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPref {
	private static final String SHARED_PREF = "SHARED_PREF";

	private static final String KEY_USER_ID = "USER_ID";
	private static final String KEY_USER_NAME = "USER_NAME";
	private static final String KEY_USER_PASSWORD = "USER_PASSWORD";

	private SharedPreferences sharedPreferences;

	public MySharedPref(Context context) {
		sharedPreferences = context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
	}

	public SharedPreferences getSharedPreferences() {
		return sharedPreferences;
	}

	public void setUserId(int userId) {
		sharedPreferences.edit().putInt(KEY_USER_ID, userId).commit();
	}

	public int getUserId(int defaultValue) {
		return sharedPreferences.getInt(KEY_USER_ID, defaultValue);
	}

	public void setUserName(String name) {
		sharedPreferences.edit().putString(KEY_USER_NAME, name).commit();
	}

	public String getUserName(String defaultValue) {
		return sharedPreferences.getString(KEY_USER_NAME, defaultValue);
	}

	public void setUserPassword(String password) {
		sharedPreferences.edit().putString(KEY_USER_PASSWORD, password).commit();
	}

	public String getUserPassword(String defaultValue) {
		return sharedPreferences.getString(KEY_USER_PASSWORD, defaultValue);
	}
}
