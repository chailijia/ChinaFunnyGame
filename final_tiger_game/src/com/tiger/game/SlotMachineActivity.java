package com.tiger.game;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import com.tiger.utils.ViewUtil;
import com.tiger.wheel.widget.OnWheelChangedListener;
import com.tiger.wheel.widget.OnWheelScrollListener;
import com.tiger.wheel.widget.WheelView;
import com.tiger.widget.adapters.AbstractWheelAdapter;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SlotMachineActivity extends BaseActivity implements
		OnClickListener {
	
	private String TAG = "SlotMachineActivity";
	private int START_SLOT_TIME = 5000;  //按开始后的初始滚动时间
	private int STOP_TIME = 2000;   //手动按停止后的的滚动时间
	private int STOP_ROUND = 1;   //手动按停止后的的滚动圈数
	private int START_SLOT_ROUND = 25; //按开始后的初始滚动圈数
//	private TextView resultTipstv;
//
//	private Button mStopABtn;
//	private Button mStopAll;
//	private Button mStopBBtn;
//	private Button mStopCBtn;
//	private Button mix ;
//	
	private WheelView mWheelA;
	private WheelView mWheelB;
	private WheelView mWheelC;
	
//	private EditText mEtA;
//	private EditText mEtB;
//	private EditText mEtC;
	
	private int fixA = 0;
	private int fixB = 1;
	private int fixC = 2;
	
	
	Button mBtnHelp;
	Button mBtnBack;
	TextView mDeviceID;
	TextView mCoins;
	Button mBtnGetOne;
	Button mBtnGetTwo;
	Button mBtnGetThree;
	Button mBtnGetAll;
	Button mBtnDesTimes;
	Button mBtnAddTimes;
	TextView mTvSingleAdd;
	TextView mTvAllput;
	Button mBtnMove;
	TextView mTvWinCoin;
	ImageView mLeftLigt1;
	ImageView mLeftLigt2;
	ImageView mLeftLigt3;
	ImageView mRightLigt1;
	ImageView mRightLigt2;
	ImageView mRightLigt3;
	
	boolean mGetOneBool;
	boolean mGetTwoBool;
	boolean mGetThreeBool;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.slot_machine_layout);

		mWheelA = initWheel(R.id.slot_1);
		mWheelA.setWheelPosition(WheelView.WHEELVIEWA);
		mWheelB = initWheel(R.id.slot_2);
		mWheelB.setWheelPosition(WheelView.WHEELVIEWB);
		mWheelC = initWheel(R.id.slot_3);
		mWheelC.setWheelPosition(WheelView.WHEELVIEWC);
//		resultTipstv = (TextView) findViewById(R.id.pwd_status);
//		mix = (Button) findViewById(R.id.btn_mix);
//		mix.setOnClickListener(this);
//
//		mStopABtn = (Button) findViewById(R.id.btn_stop_a);
//		mStopBBtn = (Button) findViewById(R.id.btn_stop_b);
//		mStopCBtn = (Button) findViewById(R.id.btn_stop_c);
//		mStopAll = (Button) findViewById(R.id.btn_stop_all);
//		
//		mEtA = (EditText) findViewById(R.id.et_a);
//		mEtB = (EditText) findViewById(R.id.et_b);
//		mEtC = (EditText) findViewById(R.id.et_c);
//		
//		mStopAll.setOnClickListener(this);
//		mStopABtn.setOnClickListener(this);
//		mStopBBtn.setOnClickListener(this);
//		mStopCBtn.setOnClickListener(this);
//		mStopABtn.setClickable(false);
//		mStopBBtn.setClickable(false);
//		mStopCBtn.setClickable(false);
//		mStopAll.setClickable(false);
		
		mWheelA.setFinish(false);
		mWheelA.setFinish(false);
		mWheelA.setFinish(false);
		
		initView();
	}

	private void initView() {
		mBtnHelp = (Button) findViewById(R.id.btn_help);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnGetOne = (Button) findViewById(R.id.btn_press_one);
		mBtnGetTwo = (Button) findViewById(R.id.btn_press_two);
		mBtnGetThree = (Button) findViewById(R.id.btn_press_three);
		mBtnGetAll = (Button) findViewById(R.id.btn_press_all);
		mBtnDesTimes = (Button) findViewById(R.id.btn_min);
		mBtnAddTimes = (Button) findViewById(R.id.btn_add);
		mBtnMove = (Button) findViewById(R.id.btn_move);
		mDeviceID = (TextView) findViewById(R.id.device_tv);
		mCoins = (TextView) findViewById(R.id.tv_num_coin);
		mTvSingleAdd = (TextView) findViewById(R.id.tv_times);
		mTvAllput = (TextView) findViewById(R.id.tv_all_times);
		mTvWinCoin = (TextView) findViewById(R.id.tv_all_get_point);
		mLeftLigt1 = (ImageView) findViewById(R.id.slot_1_left_light);
		mLeftLigt2 = (ImageView) findViewById(R.id.slot_2_left_light);
		mLeftLigt3 = (ImageView) findViewById(R.id.slot_3_left_light);
		
		mRightLigt1 = (ImageView) findViewById(R.id.slot_1_right_light);
		mRightLigt2 = (ImageView) findViewById(R.id.slot_2_right_light);
		mRightLigt3 = (ImageView) findViewById(R.id.slot_3_right_light);
		
		mBtnHelp.setOnClickListener(this);
		mBtnBack.setOnClickListener(this);
		mBtnGetOne.setOnClickListener(this);
		mBtnGetTwo.setOnClickListener(this);
		mBtnGetThree.setOnClickListener(this);
		mBtnGetAll.setOnClickListener(this);
		mBtnDesTimes.setOnClickListener(this);
		mBtnAddTimes.setOnClickListener(this);
		mBtnMove.setOnClickListener(this);
	}

	// 车轮滚动标志
	private boolean wheelScrolled = false;

	// 车轮滚动的监听器
	OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
		}

		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			String temp = "A";
			int wheelPosition = wheel.getWheelPosition();
//			switch (wheelPosition) {
//			case WheelView.WHEELVIEWA:
//				mStopABtn.setClickable(false);
//				mWheelA.setFinish(true);
//				break;
//			case WheelView.WHEELVIEWB:
//				temp = "B";
//				mStopBBtn.setClickable(false);
//				mWheelB.setFinish(true);
//				break;
//			case WheelView.WHEELVIEWC:
//				temp = "C";
//				mStopCBtn.setClickable(false);
//				mWheelC.setFinish(true);
//			break;
//			default:
//				break;
//			}
//			mix.setClickable(true);
			System.out.println("轮子 " + temp + "---->" + wheel.getCurrentItem());
				
			if (mWheelA.isFinish() && mWheelB.isFinish() && mWheelC.isFinish()) {
				System.out.println("updateStatus");
				updateStatus();
				mWheelA.setFinish(false);
				mWheelB.setFinish(false);
				mWheelC.setFinish(false);
			}
		}
			
	};

	// 车轮item改变的监听器
	private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (!wheelScrolled) {
//				System.out.println("轮子item---->" + wheel.getCurrentItem());
//				updateStatus();
			}
		}
	};

	/**
	 * 更新状态
	 */
	private void updateStatus() {
//		resultTipstv = (TextView) findViewById(R.id.pwd_status);
//		if (mWheelA.getCurrentItem() == mWheelA.getCurrentItem() && mWheelA.getCurrentItem() == mWheelC.getCurrentItem()) {
//			resultTipstv.setText("中奖了");
//		} else {
//			resultTipstv.setText("没有中奖");
//		}
	}

	/**
	 * 初始化轮子
	 * 
	 * @param id
	 *            the wheel widget Id
	 */
	private WheelView initWheel(int id) {
		WheelView wheel = getWheel(id);
		wheel.setViewAdapter(new SlotMachineAdapter(this));
		// wheel.setCurrentItem((int)(Math.random() * 10));
		wheel.setCurrentItem(0);
		wheel.addChangingListener(changedListener);
		wheel.addScrollingListener(scrolledListener);
		wheel.setCyclic(true);
		wheel.setEnabled(false);
		return wheel;
	}

	/**
	 * 根据id获取轮子
	 * 
	 * @param id
	 *            the wheel Id
	 * @return the wheel with passed Id
	 */
	private WheelView getWheel(int id) {
		return (WheelView) findViewById(id);
	}

	/**
	 * 测试轮子转动位置
	 * 
	 * @return true
	 */
	private boolean test() {
		int value = getWheel(R.id.slot_1).getCurrentItem();
		return testWheelValue(R.id.slot_2, value)
				&& testWheelValue(R.id.slot_3, value);
	}

	/**
	 * 根据轮子id获取当前item值
	 * 
	 * @param id
	 *            the wheel Id
	 * @param value
	 *            the value to test
	 * @return true if wheel value is equal to passed value
	 */
	private boolean testWheelValue(int id, int value) {
		return getWheel(id).getCurrentItem() == value;
	}

	/**
	 * 转动轮子
	 * 
	 * @param id
	 *            the wheel id
	 */
	private void mixWheel(int id, int round, int time) {
		WheelView wheel = getWheel(id);
		wheel.scroll(round, time);
		// wheel.scroll((int)(Math.random() * 50)+round, time);
		// wheel.scroll(-350 + (int)(Math.random() * 50), 2000);
	}
	
	/**
	 * 转动轮子
	 * 
	 * @param id
	 *            the wheel id
	 */
	private void mixWheelTofixPosition(int id, int round, int fixPosition, int time) {
		WheelView wheel = getWheel(id);
		wheel.scrollToFixPosition(round, fixPosition, time);
		// wheel.scroll((int)(Math.random() * 50)+round, time);
		// wheel.scroll(-350 + (int)(Math.random() * 50), 2000);
	}

	/**
	 * 老虎机适配器
	 */
	private class SlotMachineAdapter extends AbstractWheelAdapter {
		// 图片的大小
		final int IMAGE_WIDTH = 60;
		final int IMAGE_HEIGHT = 60;

		// 图片的数组
		private final int items[] = new int[] { 
				android.R.drawable.star_big_on,	//4
				android.R.drawable.stat_sys_warning,//5
				android.R.drawable.radiobutton_on_background,//6
				android.R.drawable.ic_delete //7
				};

		// 对图片的缓存
		private List<SoftReference<Bitmap>> images;

		// 布局膨胀器
		private Context context;

		/**
		 * 构造函数
		 */
		public SlotMachineAdapter(Context context) {
			this.context = context;
			images = new ArrayList<SoftReference<Bitmap>>(items.length);
			for (int id : items) {
				images.add(new SoftReference<Bitmap>(loadImage(id)));
			}
		}

		/**
		 * 从资源加载图片
		 */
		private Bitmap loadImage(int id) {
			Bitmap bitmap = BitmapFactory.decodeResource(
					context.getResources(), id);
			Bitmap scaled = Bitmap.createScaledBitmap(bitmap, ViewUtil.dip2px(SlotMachineActivity.this,IMAGE_WIDTH),
					 ViewUtil.dip2px(SlotMachineActivity.this,IMAGE_HEIGHT), true);
			bitmap.recycle();
			return scaled;
		}

		@Override
		public int getItemsCount() {
			return items.length;
		}

		// 设置图片布局的参数
		final LayoutParams params = new LayoutParams(ViewUtil.dip2px(SlotMachineActivity.this,IMAGE_WIDTH), ViewUtil.dip2px(SlotMachineActivity.this,IMAGE_HEIGHT));

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			ImageView img;
			if (cachedView != null) {
				img = (ImageView) cachedView;
			} else {
				img = new ImageView(context);
			}
			img.setLayoutParams(params);
			SoftReference<Bitmap> bitmapRef = images.get(index);
			Bitmap bitmap = bitmapRef.get();
			if (bitmap == null) {
				bitmap = loadImage(items[index]);
				images.set(index, new SoftReference<Bitmap>(bitmap));
			}
			img.setImageBitmap(bitmap);

			return img;
		}
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
//		if (mEtA.getText().length() > 0)
//			fixA = Integer.parseInt(mEtA.getText().toString());
//		
//		if (mEtB.getText().length() > 0)
//			fixB = Integer.parseInt(mEtB.getText().toString());
//		
//		if (mEtC.getText().length() > 0)
//			fixC = Integer.parseInt(mEtC.getText().toString());
//		switch (id) {
//		case R.id.btn_stop_all:
//			mStopABtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_1, STOP_ROUND, fixA,STOP_TIME);
//			mStopBBtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_2, STOP_ROUND, fixB,STOP_TIME + 1000);
//			mStopCBtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_3, STOP_ROUND, fixC,STOP_TIME + 2000);
//			mStopAll.setClickable(false);
//			break;
//		case R.id.btn_stop_a:
//			mStopABtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_1, STOP_ROUND, fixA,STOP_TIME);
//			break;
//		case R.id.btn_stop_b:
//			mStopBBtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_2, STOP_ROUND, fixB,STOP_TIME);
//			break;
//		case R.id.btn_stop_c:
//			mStopCBtn.setClickable(false);
//			mixWheelTofixPosition(R.id.slot_3, STOP_ROUND, fixC,STOP_TIME);
//			break;
//		case R.id.btn_mix:
//			mStopABtn.setClickable(true);
//			mStopBBtn.setClickable(true);
//			mStopCBtn.setClickable(true);
//			mStopAll.setClickable(true);
//			mix.setClickable(false);
//			
//			new Handler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					mStopABtn.setClickable(false);
//					mStopBBtn.setClickable(false);
//					mStopCBtn.setClickable(false);
//				}
//			}, START_SLOT_TIME - 3000);
//			
//			
//			resultTipstv.setText("转动中。。。");
//			mixWheelTofixPosition(R.id.slot_1, START_SLOT_ROUND, fixA,START_SLOT_TIME);
//			mixWheelTofixPosition(R.id.slot_2, START_SLOT_ROUND, fixB,START_SLOT_TIME + STOP_TIME);
//			mixWheelTofixPosition(R.id.slot_3, START_SLOT_ROUND, fixC,START_SLOT_TIME + 2 * STOP_TIME);
//			break;
//
//		default:
//			break;
//		}
		switch (id) {
		case R.id.btn_help:
			//打开帮助页
			break;
		case R.id.btn_back:
			finish();
			break;
		case R.id.btn_press_one:
			mGetOneBool = !mGetOneBool;
			mLeftLigt1.setBackgroundColor(mGetOneBool ? Color.YELLOW : Color.BLUE);
			mRightLigt1.setBackgroundColor(mGetOneBool ? Color.YELLOW : Color.BLUE);
			break;
		case R.id.btn_press_two:
			mGetTwoBool = !mGetTwoBool;
			mLeftLigt2.setBackgroundColor(mGetTwoBool ? Color.YELLOW : Color.BLUE);
			mRightLigt2.setBackgroundColor(mGetTwoBool ? Color.YELLOW : Color.BLUE);
			break;
		case R.id.btn_press_three:
			mGetThreeBool = !mGetThreeBool;
			mLeftLigt3.setBackgroundColor(mGetThreeBool ? Color.YELLOW : Color.BLUE);
			mRightLigt3.setBackgroundColor(mGetThreeBool ? Color.YELLOW : Color.BLUE);
			break;
		case R.id.btn_press_all:
			
			if (!mGetOneBool || !mGetThreeBool || !mGetTwoBool) {
				mGetOneBool = true;
				mGetTwoBool = true;
				mGetThreeBool = true;
			} else {
				mGetOneBool = !mGetOneBool;
				mGetTwoBool = !mGetTwoBool;
				mGetThreeBool = !mGetThreeBool;
			}
			mLeftLigt1.setBackgroundColor(mGetOneBool ? Color.YELLOW : Color.BLUE);
			mRightLigt1.setBackgroundColor(mGetOneBool ? Color.YELLOW : Color.BLUE);
			
			mLeftLigt2.setBackgroundColor(mGetTwoBool ? Color.YELLOW : Color.BLUE);
			mRightLigt2.setBackgroundColor(mGetTwoBool ? Color.YELLOW : Color.BLUE);
			
			mLeftLigt3.setBackgroundColor(mGetThreeBool ? Color.YELLOW : Color.BLUE);
			mRightLigt3.setBackgroundColor(mGetThreeBool ? Color.YELLOW : Color.BLUE);
			break;
		case R.id.btn_min:
			int temp = Integer.parseInt(mTvSingleAdd.getText().toString());
			if (temp > 1) {
				temp--;
				mTvSingleAdd.setText(temp + "");
			}
			break;
		case R.id.btn_add:
			 temp = Integer.parseInt(mTvSingleAdd.getText().toString()) + 1;
			 mTvSingleAdd.setText(temp + "");
			 break;
		case R.id.btn_move:
			mixWheelTofixPosition(R.id.slot_1, 10, fixA,START_SLOT_TIME);
			mixWheelTofixPosition(R.id.slot_2, 15, fixB,START_SLOT_TIME + STOP_TIME);
			mixWheelTofixPosition(R.id.slot_3, 20, fixC,START_SLOT_TIME + 2 * STOP_TIME);
			break;
		default:
			break;
		}
		
		
	}
}
