package com.tiger.utils;

import com.tiger.model.User;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.telephony.TelephonyManager;

public class AppContext extends Application {

	private SharedPreferences preferences;
	private User user;
	private int screenWidth;
	private int screenHeight;

	public String SIME;
	public String phoneMode;

	public static String serverIP;
	public static String version = "2.0";

	public static String getServerIP() {
		return serverIP;
	}

	public static void setServerIP(String serverIP) {
		AppContext.serverIP = serverIP;
	}

	public static String getVersion() {
		return version;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		TelephonyManager phoneMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		SIME = phoneMgr.getDeviceId();
		preferences = getSharedPreferences("funnyGame", MODE_PRIVATE);
		user = new User();
		String userName = preferences.getString("username", null);
		String pwd = preferences.getString("password", null);
		if (null != userName && null != pwd) {
			user.setPassword(pwd);
			user.setUsername(userName);
		} else {
			user = null;
		}
		RequestManager.init(this);
		phoneMode = android.os.Build.MODEL;
		RequestManager.init(this);
//		Configuration.init(this);
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		Editor editor = preferences.edit();
		if (null == user) {
			editor.remove("username");
			editor.remove("password");
		} else {
			editor.putString("username", user.getUsername());
			editor.putString("password", user.getPassword());
		}
		editor.commit();

	}

	public String getSIME() {
		return SIME;
	}

	public void setSIME(String sIME) {
		SIME = sIME;
	}

	public String getPhoneMode() {
		return phoneMode;
	}

	public void setPhoneMode(String phoneMode) {
		this.phoneMode = phoneMode;
	}
}
