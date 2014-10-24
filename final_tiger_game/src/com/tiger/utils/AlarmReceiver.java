package com.tiger.utils;

import org.json.JSONException;
import org.json.JSONObject;
import com.connect.util.ChinaGameManager;
import com.connect.util.GameQueryListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	public static boolean houseLight = false;
	public static boolean sureBone = false;
	public static String houseLightMsg = "";
	public static int hourseCredit = 0;

	private static String TAG = "AlarmReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "AlarmReceiver onReceive");

		ChinaGameManager.queryMarquee(houseLightresponseListener);
		ChinaGameManager.getBonus(sureBoneresponseListener);

	}

	private GameQueryListener houseLightresponseListener = new GameQueryListener() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "houseLight return :" + response);

				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("ret");
					if (0 == resultCode) {
						int status = object.getInt("status");
						if (status == 1) {
							houseLight = true;
							houseLightMsg = object.getString("msg");
							hourseCredit = object.getInt("credits");
						} else {
							houseLight = false;
							houseLightMsg = "";
						}
					} else {
						// 提示错误
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

	private GameQueryListener sureBoneresponseListener = new GameQueryListener() {
			@Override
			public void onResponse(String response) {
				Log.d(TAG, "sureBone return :" + response);

				JSONObject object;
				try {
					object = new JSONObject(response.toString());

					int resultCode = object.getInt("ret");
					if (0 == resultCode) {
						int status = object.getInt("status");
						if (status == 1) {
							sureBone = true;
						}
					} else {
						// 提示错误
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
}
