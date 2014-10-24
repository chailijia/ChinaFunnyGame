package com.tiger.game;

import com.connect.util.Checker;
import com.tiger.model.User;
import com.tiger.utils.AppContext;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	final static String USER_COIN_STRING = "user_coins_string";
	final static int LOGIN_REQUEST_COED = 1001;

	Button mLoginBtn;
	TextView mDeviceTv;
	TextView mCoinTv;
	// Button mTigerGameBtn;
	User user;
	AppContext appContext;
	Button mBtnQuit;
	// Button mFruitGameBtn;

	Button mGameBtn1;
	Button mGameBtn2;
	Button mGameBtn3;
	Button mGameBtn4;
	Button mGameBtn5;
	Button mGameBtn6;
	Button mMoneyBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		appContext = (AppContext) getApplicationContext();
		user = appContext.getUser();
		initView();
	}

	private void initView() {
		mLoginBtn = (Button) findViewById(R.id.login_btn);
		mDeviceTv = (TextView) findViewById(R.id.device_tv);
		mCoinTv = (TextView) findViewById(R.id.owner_coin_tv);
		// mTigerGameBtn = (Button) findViewById(R.id.tigrt_game_btn);
		mBtnQuit = (Button) findViewById(R.id.quit_btn);
		// mFruitGameBtn = (Button) findViewById(R.id.fruit_game_btn);

		mBtnQuit.setVisibility(View.GONE);
		mLoginBtn.setVisibility(View.GONE);
		mDeviceTv.setVisibility(View.GONE);
		mCoinTv.setVisibility(View.GONE);
		// mFruitGameBtn.setOnClickListener(this);

		mBtnQuit.setOnClickListener(this);
		mLoginBtn.setOnClickListener(this);
		// mTigerGameBtn.setOnClickListener(this);

		// if (null == user) {
		// mLoginBtn.setVisibility(View.VISIBLE);
		// } else {
		mBtnQuit.setVisibility(View.GONE);
		mDeviceTv.setVisibility(View.VISIBLE);
		mCoinTv.setVisibility(View.VISIBLE);
		// }

		mDeviceTv.setText(" 001");

		mGameBtn1 = (Button) findViewById(R.id.game_btn1);
		mGameBtn2 = (Button) findViewById(R.id.game_btn2);
		mGameBtn3 = (Button) findViewById(R.id.game_btn3);
		mGameBtn4 = (Button) findViewById(R.id.game_btn4);
		mGameBtn5 = (Button) findViewById(R.id.game_btn5);
		mGameBtn6 = (Button) findViewById(R.id.game_btn6);
		mMoneyBtn = (Button) findViewById(R.id.btn_money);
		mGameBtn1.setOnClickListener(this);
		mGameBtn2.setOnClickListener(this);
		mGameBtn3.setOnClickListener(this);
		mGameBtn4.setOnClickListener(this);
		mGameBtn5.setOnClickListener(this);
		mGameBtn6.setOnClickListener(this);
		mMoneyBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.login_btn:
			Intent intent;
			break;
		case R.id.game_btn1:
			Intent gameIntent = new Intent(this, FruitGameActivity.class);
			startActivity(gameIntent);
			break;
		case R.id.game_btn2:
			intent = new Intent();
			intent.setComponent(new ComponentName("com.max.zajinhua",
					"com.io.pyds.Pyds"));
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			break;
		case R.id.game_btn3:
			intent = new Intent();
			intent.setComponent(new ComponentName("com.io.shuihu_io",
					"com.io.pyds.Pyds"));
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			break;
		case R.id.game_btn4:
			intent = new Intent();
			intent.setComponent(new ComponentName("com.max.jcby",
					"com.io.pyds.Pyds"));
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			break;
		case R.id.game_btn5:
			// air.com.mj_tech.texas/.AppEntry

			intent = new Intent();
			intent.setComponent(new ComponentName("air.com.mj_tech.texas",
					"air.com.mj_tech.texas.AppEntry"));
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			break;
		case R.id.game_btn6:
			intent = new Intent();
			intent.setComponent(new ComponentName("com.io.oxbattle_io",
					"com.io.pyds.Pyds"));
			intent.setAction(Intent.ACTION_VIEW);
			startActivity(intent);
			break;
		case R.id.btn_money:
			showMoneyDialog();
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;

		switch (requestCode) {
		case LOGIN_REQUEST_COED:
			mDeviceTv.setVisibility(View.VISIBLE);
			mCoinTv.setVisibility(View.VISIBLE);
			mBtnQuit.setVisibility(View.VISIBLE);
			mLoginBtn.setVisibility(View.GONE);
			String coins = data.getStringExtra(USER_COIN_STRING);
			mCoinTv.setText(coins);
			user = appContext.getUser();
			break;
		default:
			break;
		}
	}

	private void showMoneyDialog() {

		final Dialog dialog = new Dialog(this);
		final View view = LayoutInflater.from(this).inflate(R.layout.money_dialog, null);
		
		ImageView closeBtn = (ImageView) view.findViewById(R.id.close);
		ImageButton commitBtn = (ImageButton) view.findViewById(R.id.btn_ok);
		closeBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});
		
		commitBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EditText firstEt = (EditText) view.findViewById(R.id.et_jifen);
				EditText secondEt = (EditText) view.findViewById(R.id.et_pwd);
				String firString = firstEt.getText().toString();
				String secondString = secondEt.getText().toString();
				
				if (null == firString || firstEt.length() == 0 || secondString == null || secondString.length() == 0) {
					Toast.makeText(MainActivity.this, "不能为空", Toast.LENGTH_LONG).show();
				} else {
					dialog.dismiss();
				}
			}
		});
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		dialog.setContentView(view);
		
	}

}
