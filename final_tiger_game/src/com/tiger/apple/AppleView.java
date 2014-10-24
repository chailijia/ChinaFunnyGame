package com.tiger.apple;

import com.tiger.apple.DrawThread;
import com.tiger.game.FruitGameActivity;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AppleView extends SurfaceView implements SurfaceHolder.Callback {
	FruitGameActivity fruitActivity;
	DisplayMetrics DMInfo;
	Apple apple;
	DrawThread dt; // 后台屏幕绘制线程
	String fps = "FPS:N/A"; // 用于显示帧速率的字符串，调试使用
	static public boolean ButtonDown = false;

	public AppleView(Context activity) {
		super(activity); // 调用父类构造器
		fruitActivity = (FruitGameActivity) activity;
		getHolder().addCallback(this);
		init();
		dt = new DrawThread(this, getHolder()); // 初始化重绘线程
	}

	public void init() {
		DMInfo = new DisplayMetrics();
		fruitActivity.getWindowManager().getDefaultDisplay().getMetrics(DMInfo);
		apple = new Apple(fruitActivity, DMInfo.widthPixels,
				DMInfo.heightPixels);
	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			ButtonDown = true;
			// 苹果机开始
			if (false == apple.DecIcon(event.getX(), event.getY())) {
				if (false == apple.winBtnClick(event.getX(), event.getY())) {
					apple.Restart(event.getX(), event.getY());
					invalidate();
				}
			}
		} else {
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			ButtonDown = false;
			apple.clearBtnState();
		}

		return true;
	}

	public void doDraw(Canvas canvas) {
		Paint BackGraoundP = new Paint(); // 创建画笔对象
		BackGraoundP.setColor(Color.BLACK); // 为画笔设置颜色
		// 刷背景色
		RectF tRect = new RectF(0, 0, this.getWidth(), this.getHeight());
		canvas.drawRect(tRect, BackGraoundP);

		// 画水果机
		Paint paintApple = new Paint();
		apple.Draw(canvas, paintApple);

		// // 画FPS
		// Paint p = new Paint();
		// // 创建画笔对象
		// p.setColor(Color.WHITE);
		// // 为画笔设置颜色
		// p.setTextSize(48);
		// // 为画笔设置字体大小
		// p.setAntiAlias(true);
		// // 设置抗锯齿
		// canvas.drawText(fps, 100, 30, p);
		// // 画出帧速率字符串
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {// 重写surfaceChanged方法
		apple.ChangeWindowSize(width, height);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {// 从写surfaceCreated方法
		if (dt == null) {
			init();
			dt = new DrawThread(this, getHolder());
		}
		if (!dt.isAlive()) { // 如果DrawThread没有启动，就启动这个线程
			dt.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {// 重写surfaceDestroyed方法
//		dt.flag = false; // 停止线程的执行
//		dt = null; // 将dt指向的对象声明为垃圾
//
//		apple.ExitThread();
	}
}
