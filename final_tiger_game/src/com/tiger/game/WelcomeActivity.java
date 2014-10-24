package com.tiger.game;

import com.tiger.utils.Configuration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
/**欢迎动画activity*/
public class WelcomeActivity extends BaseActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.init(getApplication());
        setContentView(R.layout.welcome_activity);
        
        final Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
//        final Intent intent = new Intent(WelcomeActivity.this, SlotMachineActivity.class);
        //系统会为需要启动的activity寻找与当前activity不同的task;
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        //创建一个新的线程来显示欢迎动画，指定时间后结束，跳转至指定界面
//        getRegionWidth(this);
//        new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					Thread.sleep(1000);
//					//获取应用的上下文，生命周期是整个应用，应用结束才会结束
//					getApplicationContext().startActivity(intent);
//					finish();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				
//			}
//		}).start();
    }
    
    public static void getRegionWidth(Activity activity) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int srcWidth = outMetrics.widthPixels;
		int srcHeight = outMetrics.heightPixels;
		Log.d("fingerGame", "srcWidth=" + srcWidth + "__srcHeight=" + srcHeight);
	}
}