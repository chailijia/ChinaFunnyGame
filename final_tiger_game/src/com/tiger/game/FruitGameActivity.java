package com.tiger.game;


import com.tiger.apple.AppleView;
import com.tiger.utils.AlarmService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class FruitGameActivity extends Activity{
	
	AppleView appleview;
	Button mBtnStart;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        requestWindowFeature(Window.FEATURE_NO_TITLE);		//设置不显示标题
        getWindow().setFlags(									//设置为全屏模式
        		WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appleview = new AppleView(this);		//创建BallView对象
        setContentView(appleview);			//将屏幕设置为BallView对象
        Intent intent = new Intent();
		intent.setClass(FruitGameActivity.this, AlarmService.class);
		startService(intent);
        //启动定时服务
        
        
        
        
//        setContentView(R.layout.welcome_activity);
    }
    
//    @Override
//	protected void onBackPress() {
//    	
//    }
	
}
