package com.tiger.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AlarmService extends Service {

	public static final String TAG = "Alarm";
	private Intent myIntent = null;
	private PendingIntent pendingIntent = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {

		myIntent = new Intent(AlarmService.this, AlarmReceiver.class);
			
		pendingIntent = PendingIntent.getBroadcast(AlarmService.this, 0, myIntent, 0);
		
		
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 60 *1000, pendingIntent);
		
		super.onStart(intent, startId);
	}
}
