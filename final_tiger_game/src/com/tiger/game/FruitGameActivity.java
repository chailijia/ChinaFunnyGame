package com.tiger.game;


import com.tiger.apple.AppleView;
import com.tiger.utils.AlarmService;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class FruitGameActivity extends Activity{
	
	AppleView appleview;
	Button mBtnStart;
	
	 Intent timeService;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//设置不显示标题
        getWindow().setFlags(									//设置为全屏模式
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appleview = new AppleView(this);		//创建BallView对象
        setContentView(appleview);			//将屏幕设置为BallView对象
        timeService = new Intent();
        //启动定时服务
        timeService.setClass(FruitGameActivity.this, AlarmService.class);
        
//        setContentView(R.layout.welcome_activity);
    }
    
//    @Override
//	protected void onBackPress() {
//    	
//    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	startService(this, AlarmService.class);
    }
    
	@Override
	public void onStop() {
		super.onStop();
		stopPollingService(this, AlarmService.class);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		appleview.exit();
	}
	
	//开启轮询服务  
    public static void startService(Context context, Class<?> cls) {  
        //获取AlarmManager系统服务  
        AlarmManager manager = (AlarmManager) context  
                .getSystemService(Context.ALARM_SERVICE);  
          
        //包装需要执行Service的Intent  
        Intent intent = new Intent(context, cls);  
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,  
                intent, PendingIntent.FLAG_UPDATE_CURRENT);  
          
        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service  
        manager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), 60 *1000, pendingIntent); 
        
    }  
  
    //停止轮询服务  
    public static void stopPollingService(Context context, Class<?> cls) {  
        AlarmManager manager = (AlarmManager) context  
                .getSystemService(Context.ALARM_SERVICE);  
        Intent intent = new Intent(context, cls);  
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,  
                intent, PendingIntent.FLAG_UPDATE_CURRENT);  
        //取消正在执行的服务  
        manager.cancel(pendingIntent);  
    }  
}
