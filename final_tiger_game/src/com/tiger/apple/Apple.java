package com.tiger.apple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.tiger.game.FruitGameActivity;
import com.tiger.game.R;
import com.tiger.utils.AlarmReceiver;
import com.tiger.utils.RandomMetho;

import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Movie;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.DisplayMetrics;
import android.util.Log;

public class Apple {
	static private int ICON_JIHUI = -2;
	static private int ICON_MINGYUN = -1;
	
//	1. 既然有用到比例尺，所以我们要先确立一个参照的标准尺寸 
    public static int SCREEN_WIDTH_STANDARD=800;//屏幕标准宽度
    public static int SCREEN_HEIGHT_STANDARD=444;//屏幕标准高度
  
    public static int SCREEN_WIDTH;//屏幕实际宽度
    public static int SCREEN_HEIGHT;//屏幕实际高度


	Bitmap m_BitMapArray[] = null; // 位置
	final int m_AppleBitMapSize = 8; // 水果数量

	float mRightX;
	float mRightY;

	int leftIcon = 0;

	int m_Winsize[]; // 大
	FruitGameActivity fruitActivity; // 视窗
	Tile appleTileArray[]; // 水果格子
	float m_ScreenWidth; // 屏幕宽度
	float m_ScreenHeight; // 屏幕高度
	int m_TileCows = 7; // 列数
	int m_TileRows = 5; // 行数,不算头尾

	int m_TileCount = 0; // 格子总数
	int m_AppleImageArray[]; // 水果图ID数组
	WinnerRecord m_WinnerRecord[]; // 玩家
	Paint m_WinnerPaint[]; // 创建画笔对象
	int m_WinnerSize = 1; // 玩家数量
	final int m_MaxWinnerSize = 2; // 最大玩家数量
	int m_CurrWinnerID = 0; // 当前玩家ID
	int m_InitIconSize = 200; // 初始金币数量

	int m_ShowIconHeight = 60; // 显示玩家下注的高度
	int m_ShowIconTop = 0; // 显示玩家下注时的位置

	float m_ShowStartBtnX = 0; // 显示GO按钮的位置X
	float m_ShowStartBtnY = 0; // 显示GO按钮的位置Y
	boolean m_startClick = false;
	double m_startClickTime = 0;

	float m_ShowSetMoneyBtnX = 0; // 显示reset按钮的位置X
	float m_ShowSetMoneyBtnY = 0; // 显示reset按钮的位置Y

	float m_ShowGetMoneyBtnX = 0;
	float m_ShowGetMoneyBtnY = 0;
	boolean m_getMoneyClick = false;
	double m_getMeneyClickTime = 0;

	float m_ShowBackBtnX = 0;
	float m_ShowBackBtnY = 0;
	boolean m_backClick = false;
	double m_backClickTime = 0;

	float m_ShowQuestionBtnX = 0;
	float m_ShowQuestionBtnY = 0;

	float m_ShowBigBtnX = 0; // 显示中间压大按钮的位置X
	boolean m_BigBtnClick = false;
	float m_ShowBigBtnY = 0; // 显示中间压大按钮的位置Y

	float m_ShowDoubleBtnX = 0; // 显示中间压大按钮的位置X
	boolean m_DoubleBtnClick = false;
	boolean m_DoubleBtnAdd = false;
	float m_ShowDoubleBtnY = 0; // 显示中间压大按钮的位置Y

	float m_ShowSmallBtnX = 0; // 显示中间压小按钮的位置X
	boolean m_SmallBtnClick = false;
	float m_ShowSmallBtnY = 0; // 显示中间压小按钮的位置Y

	float m_ShowGetCoinBtnX = 0; // 显示中间退币按钮的位置X
	boolean m_GetCoinBtnClick = false;
	float m_ShowGetCoinBtnY = 0; // 显示中间退币按钮的位置Y
	boolean m_GetCoinIng = false; // 正在退币

	float animationX;
	float animationY;
	float animationCount;

	double m_GameExitTime = 0; // 获取当前时间，单位为纳秒
	double m_currenttime = 0; // 记录当前时间
	double m_starttime = 0; // 开始的时间
	double m_TimeLength = 0; // 间隔时间
	int m_currentpathid = 0; // 路径ID

	int tWinIconSisze = 0;

	int m_ResultRound; // 需要转动的圈数
	int m_ResultTarget; // 停止的位置
	int m_CurrentRoundCount; // 当前转动的圈数，用于判断停止

	double m_Timer = 1000; // 每隔多久前进一次
	double m_MinTimer = 30; // 最小时间间隔
	double m_MaxTimer = 700; // 最大时间间隔
	double m_DecTimer; // 加速度

	boolean m_bStartRun = false; // 开始运动
	boolean m_WinnerState = false;
	Matrix m_BackGroundMatrix = new Matrix();
	Matrix m_CenterMatrix = new Matrix();
	public static float m_ScaleW;
	public static float m_ScaleH;

	int m_TileW = (int) (58 * m_ScaleW); // 格子宽
	int m_TileH = (int) (45 * m_ScaleH); // 格子高

	int m_Left = (int) (127 * m_ScaleW); // 左上角X
	int m_Top = (int) (60 * m_ScaleH); // 左上角Y

	AppleThread m_appleThread = null; // 水果线程
	SoundPool m_SoundPool; // 音效控制器
	HashMap<Integer, Integer> m_SoundPoolMap; // 音效容器

	boolean m_SoundOpen = true; // 音效开关
	boolean m_MusicOpen = true; // 声音天并

	int m_RunNum = 0; // 运行次数

	int m_WinnerAppleID = -1; // 胜利者

	boolean m_ShowWinner = true; // 显示胜利者
	Random random = new Random(); // 随机数
	boolean m_show_gif = false;
	boolean m_show_shuaizi_gif = false;

	Paint m_UITitle = new Paint(); // 创建UI的标题画笔对象
	int m_UITitleHeight = 0; // 创建UI时的标题字体大小
	Paint m_UIText = new Paint(); // 创建UI的文字画笔对象t
	int m_UITextHeight = 0; // 创建UI的文字画笔字体大小

	Movie mMovie;
	Movie mNumberMovie;
	Movie mTouBiMovie;
	long mTouBiStart = 0;
	long mMovieStart = 0;
	long mNumberMovieStart = 0;
	double m_shuaiziStarttime = 0; // 筛子开始的时间
	int resultShuaizi = -1;
	private int guess;
	private boolean special = false;
	Map specialMap;
	boolean showToubi = false;
	double toubiStartTime = 0;

	boolean lyeClick = false;
	long lyeClickTime;
	long doubleLyeClick;

	// 构造函数
	Apple(FruitGameActivity Activity, int vScreenWidth, int vScreenHeight) {
		fruitActivity = Activity;
		m_ScreenWidth = vScreenWidth; // 屏幕宽度
		m_ScreenHeight = vScreenHeight;// 屏幕高度
		// m_ScaleW = m_ScreenWidth / 800.0f;
		// m_ScaleH = m_ScreenHeight / 444.0f;
		InitApple(m_InitIconSize);
	}

	// 初始化
	public void InitApple(int vIconSize) {
		//获取屏幕尺寸
        DisplayMetrics dm=new DisplayMetrics();
        fruitActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if(dm.widthPixels>dm.heightPixels){
            SCREEN_WIDTH = dm.widthPixels;
            SCREEN_HEIGHT = dm.heightPixels;
        }else{
            SCREEN_WIDTH = dm.heightPixels;
            SCREEN_HEIGHT = dm.widthPixels;
        }
        
        m_ScaleW = (float)SCREEN_WIDTH/ (float)SCREEN_WIDTH_STANDARD;
    	m_ScaleH =  (float)SCREEN_HEIGHT/ (float)SCREEN_HEIGHT_STANDARD;
    	m_ScaleH = m_ScaleW;
    	m_TileW = (int) (59 * m_ScaleW); // 格子宽
    	m_TileH = (int) (46 * m_ScaleH); // 格子高

    	m_Left = (int) (127 * m_ScaleW); // 左上角X
    	m_Top = (int) (60 * m_ScaleH); // 左上角Y
    	
		m_SoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		m_SoundPoolMap = new HashMap<Integer, Integer>();
		m_SoundPoolMap.put(1,
				m_SoundPool.load(fruitActivity, R.raw.sound_click, 1));
		m_SoundPoolMap.put(2,
				m_SoundPool.load(fruitActivity, R.raw.sound_coin, 1));
		m_SoundPoolMap.put(3,
				m_SoundPool.load(fruitActivity, R.raw.sound_end, 1));
		m_SoundPoolMap.put(4,
				m_SoundPool.load(fruitActivity, R.raw.sound_move, 1));
		m_SoundOpen = true;
		m_MusicOpen = true;
		// 玩家管理器
		m_WinnerRecord = new WinnerRecord[m_MaxWinnerSize];
		m_WinnerPaint = new Paint[m_MaxWinnerSize];
		for (int i = 0; i < m_MaxWinnerSize; i++) {
			// 玩家
			m_WinnerRecord[i] = new WinnerRecord(i);
			m_WinnerRecord[i].SetIconSize(vIconSize);

			// 字体
			m_WinnerPaint[i] = new Paint();
			String familyName_Player = "宋体";
			Typeface font_Player = Typeface.create(familyName_Player,
					Typeface.BOLD);
			m_WinnerPaint[i].setTypeface(font_Player);
			m_WinnerPaint[i].setColor(m_WinnerRecord[i].GetColor()); // 为画笔设置颜色
			m_WinnerPaint[i].setTextSize(16 * m_ScaleH); // 为画笔设置字体大小
			m_WinnerPaint[i].setAntiAlias(true); // 设置抗锯齿
		}
		mTouBiMovie = Movie.decodeStream(fruitActivity.getResources()
				.openRawResource(R.drawable.gif_toubi));
		int tBitMapSize = 29;
		m_BitMapArray = new Bitmap[tBitMapSize];
		List<Integer> listHeigh = new ArrayList<Integer>();
		List<Integer> listWeigh = new ArrayList<Integer>();

		m_BitMapArray[0] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.icon_tile);
		listHeigh.add(76);
		listWeigh.add(61);
		
		m_BitMapArray[1] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_start_press);
		listHeigh.add(90);
		listWeigh.add(89);
		
		m_BitMapArray[2] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_up_down);
		listHeigh.add(22);
		listWeigh.add(56);
		
		m_BitMapArray[3] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_back_press);
		listHeigh.add(96);
		listWeigh.add(78);
		
		m_BitMapArray[4] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_return);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[5] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_return_press);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[6] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.icon_put_coin);
		listHeigh.add(0);
		listWeigh.add(0);

		m_BitMapArray[7] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_small);
		listHeigh.add(75);
		listWeigh.add(44);
		
		m_BitMapArray[8] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.bg_gezi);
		listHeigh.add(0);
		listWeigh.add(0);

		m_BitMapArray[9] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_back);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[10] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_quetion);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[11] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_big);
		listHeigh.add(75);
		listWeigh.add(44);
		
		m_BitMapArray[12] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.game_icon);
		listHeigh.add(0);
		listWeigh.add(0);

		// GO按钮
		m_BitMapArray[13] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_start);// 开始
		listHeigh.add(86);
		listWeigh.add(86);

		m_BitMapArray[14] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_get_coin);
		listHeigh.add(75);
		listWeigh.add(44);

		m_BitMapArray[15] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_get_money);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[16] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.money);
		listHeigh.add(0);
		listWeigh.add(0);
		
		// 背景
		m_BitMapArray[17] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.bg_fruit);
		listHeigh.add(800);
		listWeigh.add(444);

		m_BitMapArray[18] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.cover_left);
		listHeigh.add(293);
		listWeigh.add(60);
		
		m_BitMapArray[19] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.cover_right);
		listHeigh.add(223);
		listWeigh.add(67);
		
		m_BitMapArray[20] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_xiangjiao);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[21] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_pingguo);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[22] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_dabar);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[23] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_xiaobar);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[24] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_xiaoqi);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[25] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_xingxing);
		listHeigh.add(0);
		listWeigh.add(0);
		
		m_BitMapArray[26] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.select_xigua);
		listHeigh.add(0);
		listWeigh.add(0);

		m_BitMapArray[27] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.icon_no);
		listHeigh.add(0);
		listWeigh.add(0);

		m_BitMapArray[28] = BitmapFactory.decodeResource(
				fruitActivity.getResources(), R.drawable.btn_double);
		listHeigh.add(51);
		listWeigh.add(51);
		
		for (int i = 0; i < m_BitMapArray.length; i++) {
			float h = listWeigh.get(i);
			float w = listHeigh.get(i);
			if (w == 0) {
				continue;
			}
			Matrix m1=new Matrix();
	        m1.postScale((float)w / (float)m_BitMapArray[i].getWidth(), (float)h / (float)m_BitMapArray[i].getHeight());
			m_BitMapArray[i] = Bitmap.createBitmap(m_BitMapArray[i], 0, 0, (int)(m_BitMapArray[i]).getWidth(), (int)(m_BitMapArray[i]).getHeight(), m1, true);
			m_BitMapArray[i] = ScaleToFitXYRatio(m_BitMapArray[i] , m_ScaleH, m_ScaleH);
		}
		
//		Matrix m1=new Matrix();
//        m1.postScale((float)800 / (float)m_BitMapArray[17].getWidth(), (float)444 / (float)m_BitMapArray[17].getHeight());
//		m_BitMapArray[17] = Bitmap.createBitmap(m_BitMapArray[17], 0, 0, (int)(m_BitMapArray[17]).getWidth(), (int)(m_BitMapArray[17]).getHeight(), m1, true);
//		m_BitMapArray[17] = ScaleToFitXYRatio(m_BitMapArray[17] , m_ScaleH, m_ScaleH);
//		
//		m1=new Matrix();
//        m1.postScale((float)293 / (float)m_BitMapArray[18].getWidth(), (float)60 / (float)m_BitMapArray[18].getHeight());
//		m_BitMapArray[18] = Bitmap.createBitmap(m_BitMapArray[18], 0, 0, (int)(m_BitMapArray[18]).getWidth(), (int)(m_BitMapArray[18]).getHeight(), m1, true);
//		m_BitMapArray[18] = ScaleToFitXYRatio(m_BitMapArray[18] , m_ScaleH, m_ScaleH);
//		
//		m1=new Matrix();
//        m1.postScale((float)223 / (float)m_BitMapArray[19].getWidth(), (float)67 / (float)m_BitMapArray[19].getHeight());
//		m_BitMapArray[19] = Bitmap.createBitmap(m_BitMapArray[19], 0, 0, (int)(m_BitMapArray[19]).getWidth(), (int)(m_BitMapArray[19]).getHeight(), m1, true);
//		m_BitMapArray[19] = ScaleToFitXYRatio(m_BitMapArray[19] , m_ScaleH, m_ScaleH);
		
		
		// 胜数
		m_Winsize = new int[m_AppleBitMapSize];
		m_Winsize[0] = 10;
		m_Winsize[1] = 20;
		m_Winsize[2] = 20;
		m_Winsize[3] = 30;
		m_Winsize[4] = 120;
		m_Winsize[5] = 40;
		m_Winsize[6] = 35;
		m_Winsize[7] = 30;
		// m_Winsize[8] = 10;

		m_bStartRun = false;

		// 设置缩放
		m_BackGroundMatrix.reset();
		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		m_UITitle.setTypeface(font);
		m_UITitle.setColor(Color.BLACK); // 为画笔设置颜色
		m_UITitleHeight = (int) (20 * m_ScaleH);
		m_UITitle.setTextSize(m_UITitleHeight); // 为画笔设置字体大小
		m_UITitle.setAntiAlias(true); // 设置抗锯齿

		String familyName2 = "宋体";
		Typeface font2 = Typeface.create(familyName2, Typeface.BOLD);
		m_UIText.setTypeface(font2);
		m_UIText.setColor(Color.BLACK); // 为画笔设置颜色
		m_UITextHeight = (int) (16 * m_ScaleH);
		m_UIText.setTextSize(m_UITextHeight); // 为画笔设置字体大小
		m_UIText.setAntiAlias(true); // 设置抗锯齿

		// 创建线程
		m_appleThread = new AppleThread(this);
		// 启动线程
		StartThread();
		InitGameUI();
	}

	// 初始化游戏界面
	public void InitGameUI() {

		m_ShowIconHeight = (int) (60 * m_ScaleH);
		m_ShowIconTop = (int) (m_ScreenHeight - m_ShowIconHeight); // 显示玩家下注的位置高度

		m_CurrWinnerID = 0;
		m_InitIconSize = 10; // 初始时10个金币
		m_RunNum = 0;

		m_CenterMatrix.reset();

		// 格子数量
		m_TileCount = m_TileCows * 2 + m_TileRows * 2;
		m_AppleImageArray = new int[m_TileCount];

		// 先生成格子盘面
		// for (int i = 0; i < m_TileCount; i++) {
		m_AppleImageArray[0] = 3;
		m_AppleImageArray[1] = 0;
		m_AppleImageArray[2] = 4;
		m_AppleImageArray[3] = 4;
		m_AppleImageArray[4] = 4;
		m_AppleImageArray[5] = 0;
		m_AppleImageArray[6] = 0;
		m_AppleImageArray[7] = 7;
		m_AppleImageArray[8] = 7;
		m_AppleImageArray[9] = ICON_MINGYUN;
		m_AppleImageArray[10] = 0;
		m_AppleImageArray[11] = 1;
		m_AppleImageArray[12] = 1;
		m_AppleImageArray[13] = 3;
		m_AppleImageArray[14] = 3;
		m_AppleImageArray[15] = 5;
		m_AppleImageArray[16] = 5;
		m_AppleImageArray[17] = 2;
		m_AppleImageArray[18] = 2;
		m_AppleImageArray[19] = 6;
		m_AppleImageArray[20] = 6;
		m_AppleImageArray[21] = ICON_JIHUI;
		m_AppleImageArray[22] = 0;
		m_AppleImageArray[23] = 3;

		// 生成格子
		appleTileArray = new Tile[m_TileCount];
		for (int i = 0; i < m_TileCount; i++) {
			appleTileArray[i] = new Tile();
			int tImageIndex = m_AppleImageArray[i];
			appleTileArray[i].SetImage(tImageIndex, m_BitMapArray[0]);
		}

		// 上行
		int tCount = 0;
		for (int i = 0; i < m_TileCows; i++) {
			appleTileArray[tCount].SetTile(i * (int) ((m_TileW + 6 * m_ScaleW))
					+ m_Left, m_Top);
			tCount++;
		}
		// 右列
		for (int i = 0; i < m_TileRows; i++) {
			appleTileArray[tCount].SetTile((m_TileCows - 1)
					* (int) (m_TileW + 6 * m_ScaleW) + m_Left, (i + 1)
					* (m_TileH + (int) (6 * m_ScaleH)) + m_Top);
			tCount++;
		}
		// 下行
		for (int i = m_TileCows - 1; i >= 0; i--) {
			appleTileArray[tCount].SetTile(i * (int) (m_TileW + 6 * m_ScaleW)
					+ m_Left, (m_TileRows + 1)
					* (m_TileH + (int) (6 * m_ScaleH)) + m_Top);
			tCount++;
		}
		// 左列
		for (int i = m_TileRows - 1; i >= 0; i--) {
			appleTileArray[tCount].SetTile(m_Left, (i + 1)
					* (m_TileH + (int) (6 * m_ScaleH)) + m_Top);
			tCount++;
		}

		m_starttime = System.nanoTime();// 获取当前时间，单位为纳秒
		m_currentpathid = random.nextInt(m_TileCount); // 起始位置
		m_currentpathid = 0;

	}

	// 退出释放
	public void Release() {
		m_SoundPool.stop(m_SoundPoolMap.get(1));
		m_SoundPool = null;
		m_SoundOpen = false;
		m_MusicOpen = false;
	}

	// 取得屏幕宽度
	public float GetScreenWidth() {
		return m_ScreenWidth;
	}

	// 取得屏幕高度
	public float GetScreenHeight() {
		return m_ScreenHeight;
	}

	// 播放音效
	public void PlaySound(int sound, int loop) {
		if (true == m_SoundOpen) {
			AudioManager mgr = (AudioManager) fruitActivity
					.getSystemService(Context.AUDIO_SERVICE);
			float streamVolumeCurrent = mgr
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			float streamVolumeMax = mgr
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			float volume = streamVolumeCurrent / streamVolumeMax;
			// 播放音效
			m_SoundPool.play(m_SoundPoolMap.get(sound), volume, volume, 1,
					loop, 1f);
		}
	}

	// 打开音效
	public void EnableSound(boolean vSoundOpen) {
		m_SoundOpen = vSoundOpen;
	}

	// 打开音乐
	public void EnableMusic(boolean vMusicOpen) {
		m_MusicOpen = vMusicOpen;
	}

	// 播放金币声音
	public void PlayIconSound() {
		PlaySound(2, 0);
	}

	// 播放路结束声音
	public void PlayPathSound() {
		PlaySound(3, 0);
	}

	// 播放点击声音
	public void PlayClickSound() {
		PlaySound(1, 0);
	}

	// 播放路径声音
	public void PlayMoveSound() {
		PlaySound(4, 0);
	}

	// 改变窗口大小
	public void ChangeWindowSize(int vScreenWidth, int vScreenHeight) {
		m_ScreenWidth = vScreenWidth; // 屏幕宽度
		m_ScreenHeight = vScreenHeight; // 屏幕高度
		// 屏幕高度
		// m_ScaleW = m_ScreenWidth / 240.0f;
		// m_ScaleH = m_ScreenHeight / 400.0f;
		// m_TileW = (int) (m_ScaleW * 30); // 格子宽
		// m_TileH = (int) (m_ScaleH * 32); // 格子高

		m_BackGroundMatrix.reset();

		m_CenterMatrix.reset();
	}

	// 开始线程
	public void StartThread() {
		if (!m_appleThread.isAlive()) {
			m_appleThread.start();
		}
	}

	// 退出线程
	public void ExitThread() {
		m_appleThread.Exit();
		m_appleThread = null;
		// 释放
		Release();
	}

	// 更新
	public void Update() {

		// 游戏开始状态
		if (true == m_bStartRun && !m_WinnerState) {
			m_currenttime = System.nanoTime();// 获取当前时间，单位为纳秒
			m_TimeLength = (double) ((m_currenttime - m_starttime) / 1000 / 1000);// 毫秒

			if (m_TimeLength < m_Timer)
				return;

			int lastIndex = 7;
			if ((m_CurrentRoundCount == m_ResultRound - 1  && m_ResultTarget < lastIndex ) || m_CurrentRoundCount == m_ResultRound) {
				
				//最后5个减速
				if ((m_CurrentRoundCount == m_ResultRound - 1 && 24 - m_currentpathid + m_ResultTarget < lastIndex) || (m_CurrentRoundCount == m_ResultRound  && m_ResultTarget - m_currentpathid < lastIndex)) {
					Log.d("game", "slowDown::::m_ResultTarget = " + m_ResultTarget + "----m_currentpathid=" + m_currentpathid);
					m_Timer += m_DecTimer * 8;
					if (m_Timer > m_MaxTimer) {
						m_Timer = m_MaxTimer;
					}
				}

				if (m_CurrentRoundCount >= m_ResultRound)
					// 到达目标位置，停止
					if (m_currentpathid == m_ResultTarget) {
						// 播放放金币声音
						PlayPathSound();
						// 计算胜利结果
						CalculateWinner();
						// m_bStartRun = false;
						return;
					}
			} else if (m_CurrentRoundCount < m_ResultRound) {
				// 加速
				m_Timer -= (m_DecTimer * 4);
				if (m_Timer < m_MinTimer) {
					m_Timer = m_MinTimer;
				}
			}

			m_starttime = m_currenttime;
			m_currentpathid++;
			PlayMoveSound();
			m_currentpathid = (m_currentpathid) % m_TileCount;
			if (0 == m_currentpathid) {
				m_CurrentRoundCount += 1;
			}

		} else {
			if (m_WinnerAppleID >= 0) {
				m_currenttime = System.nanoTime();// 获取当前时间，单位为纳秒
				m_TimeLength = (double) ((m_currenttime - m_starttime) / 1000 / 1000);// 毫秒

				if (m_TimeLength > 500) {
					m_ShowWinner = !m_ShowWinner;
				}

				if (m_show_shuaizi_gif) {
					if ((double) ((m_currenttime - m_shuaiziStarttime) / 1000 / 1000) > 2000) {
						m_show_shuaizi_gif = false;

						Random random = new Random(System.currentTimeMillis());
						int result = Math.abs(random.nextInt()) % 6 + 1;
						resultShuaizi = result;

						if ((guess == 0 && (result > 0 && result <= 3))
								|| (guess == 1 && result > 3)) {
							m_WinnerRecord[0].SetWinIconSize(m_WinnerRecord[0]
									.GetWinIconSize() * 2);
							Log.d("game", "win");

						} else {
							m_WinnerRecord[0].SetWinIconSize(0);
							// m_WinnerAppleID = -1;
							// m_bStartRun = false;
							Log.d("game", "lose");
						}
					}
				}
			} else {
				m_show_gif = false;
			}
		}

		if (showToubi) {
			m_currenttime = System.nanoTime();
			m_TimeLength = (double) ((m_currenttime - toubiStartTime) / 1000 / 1000);// 毫秒
			if (m_TimeLength > 1500) {
				showToubi = false;
			}
		}

		if (m_GetCoinIng) {
			m_currenttime = System.nanoTime();
			m_TimeLength = (double) ((m_currenttime - m_starttime) / 1000 / 1000);// 毫秒

			if (m_TimeLength > 30) {
				m_starttime = m_currenttime;
				long winCoin = m_WinnerRecord[0].GetWinIconSize();
				if (winCoin > 0) {
					long temp = winCoin;
					int ins;
					for (ins = 0; temp > 0; ins++) {
						temp /= 10;
					}

					if (ins - 2 < 0) {
						ins = 0;
					} else {
						ins -= 2;
					}
					ins = (int) Math.pow(10, ins);
					winCoin -= ins;
					m_WinnerRecord[0].SetWinIconSize(winCoin);
					m_WinnerRecord[0].AddIcon(ins);
					Log.d("game", "WinIconSize = " + winCoin + "allCoinSize="
							+ m_WinnerRecord[0].GetIconSize());
				} else {
					m_GetCoinIng = false;
					m_bStartRun = false;
				}
			}
		}
	}

	// 渲染
	public void Draw(Canvas canvas, Paint paint) {

		canvas.drawBitmap(m_BitMapArray[17], 0, 0, paint);
		// canvas.drawBitmap(m_BitMapArray[26], 26 * m_ScaleW, 52 * m_ScaleH,
		// paint);

		mRightX = appleTileArray[m_TileCows - 1].GetTileX() + m_TileW + 10
				* m_ScaleW;
		mRightY = appleTileArray[m_TileCows - 1].GetTileY();

		// =========================游戏主界面显示===========================================

		// 显示水果种类与胜利数量
		Paint TextPaint = new Paint(); // 创建画笔对象

		String familyName = "宋体";
		Typeface font = Typeface.create(familyName, Typeface.BOLD);
		TextPaint.setTypeface(font);
		TextPaint.setColor(Color.WHITE); // 为画笔设置颜色
		TextPaint.setTextSize(16 * m_ScaleH); // 为画笔设置字体大小
		TextPaint.setAntiAlias(true); // 设置抗锯齿

		int index = 18;
		// 右边一列
		for (int i = 0; i < 4; i++) {
			float x = 660 * m_ScaleW;
			float y = 90 * m_ScaleH;
			canvas.drawBitmap(m_BitMapArray[2], x, y + i * 82 * m_ScaleH, paint);
			index++;
		}

		// 左边一列
		for (int i = 0; i < 4; i++) {
			float x = 102 * m_ScaleW;
			float y = 102 * m_ScaleH;
			canvas.drawBitmap(m_BitMapArray[2], x, y + i * 78 * m_ScaleW, paint);
			index++;
		}

		// 显示玩家下注情况
		for (int i = 0; i < m_AppleBitMapSize; i++) {
			for (int j = 0; j < m_WinnerSize; j++) {
				int tLeft = (int) (585 * m_ScaleW);
				int tBottom = (int) ((123 + i * 82) * m_ScaleH);

				if (i >= 4) {
					tLeft = (int) (25 * m_ScaleW);
					tBottom = (int) ((140 + (i - 4) * 78) * m_ScaleH);
				}

				// 背景
				// canvas.drawRect(tLeft, tTop, tRight, tBottom,
				// m_WinnerPaint[j]);

				int tappleIndex = i;

				// 取得是否有下注
				int tRecordSize = m_WinnerRecord[j].GetRecord(tappleIndex);

				if (tRecordSize > 0) {
					if ((m_WinnerAppleID == tappleIndex)
							&& false == m_ShowWinner) {

					} else {
						// 数字
						String text = " " + tRecordSize;
						canvas.drawText(text, tLeft + 4 * m_ScaleW, tBottom - 3
								* m_ScaleH, TextPaint);
					}
				} else {
					// 数字
					String text = " " + tRecordSize;
					canvas.drawText(text, tLeft + 4 * m_ScaleW, tBottom - 3
							* m_ScaleH, TextPaint);
				}
			}
		}

		// 开始按钮&重置按钮 & 返回 & 问号
		// if (false == m_bStartRun) {
		m_ShowStartBtnX = (694 * m_ScaleW);
		m_ShowStartBtnY = (270 * m_ScaleH);

		m_ShowSetMoneyBtnX = (522 * m_ScaleW);
		m_ShowSetMoneyBtnY = ((372) * m_ScaleH);

		m_ShowGetMoneyBtnX = (689 * m_ScaleW);
		m_ShowGetMoneyBtnY = (244 * m_ScaleH);

		// 开始按钮
		if (m_startClick && AppleView.ButtonDown) {
			canvas.drawBitmap(m_BitMapArray[1], m_ShowStartBtnX,
					m_ShowStartBtnY, paint);
		} else {
			canvas.drawBitmap(m_BitMapArray[13], m_ShowStartBtnX,
					m_ShowStartBtnY, paint);
		}

		m_ShowBackBtnX = 33 * m_ScaleW;
		m_ShowBackBtnY = 20 * m_ScaleH;

		m_ShowQuestionBtnX = 110 * m_ScaleW;
		m_ShowQuestionBtnY = 25 * m_ScaleH;
		handlerDrawInternal(canvas);


		// 投币动画
		float tempX = 682 * m_ScaleW;
		float tempY = 76 * m_ScaleH;

		Paint leftCoinPaint = new Paint(); // 创建画笔对象

		familyName = "宋体";
		font = Typeface.create(familyName, Typeface.BOLD);
		leftCoinPaint.setTypeface(font);
		leftCoinPaint.setColor(Color.RED); // 为画笔设置颜色
		leftCoinPaint.setTextSize(28 * m_ScaleH); // 为画笔设置字体大小
		leftCoinPaint.setFakeBoldText(true);

		String leftCoinString = "";
		long leftC = m_WinnerRecord[0].GetWinIconSize();
		int temp = 0;
		for (temp = 0; leftC > 0; temp++) {
			leftC /= 10;
		}

		canvas.drawBitmap(m_BitMapArray[18], 0, 0, paint);

		canvas.drawBitmap(m_BitMapArray[19], (800 - 223) * m_ScaleW, 0, paint);

		for (int i = 0; i < 6 - temp; i++) {
			leftCoinString += "0";
		}
		leftCoinString += m_WinnerRecord[0].GetWinIconSize();
		canvas.drawText(leftCoinString, (148) * m_ScaleW, 50 * m_ScaleH,
				leftCoinPaint);

		// 显示玩家金币情况
		for (int i = 0; i < m_WinnerSize; i++) {
			Paint coinPaint = new Paint(); // 创建画笔对象

			familyName = "宋体";
			font = Typeface.create(familyName, Typeface.BOLD);
			coinPaint.setTypeface(font);
			coinPaint.setColor(0xffffff49); // 为画笔设置颜色
			coinPaint.setTextSize(28 * m_ScaleH); // 为画笔设置字体大小
			coinPaint.setFakeBoldText(true);
			coinPaint.setAntiAlias(true); // 设置抗锯齿
			// long tIconSize = m_WinnerRecord[i].GetIconSize();
			String tIconSize = "";
			long RightC = m_WinnerRecord[i].GetIconSize();

			for (temp = 0; RightC > 0; temp++) {
				RightC /= 10;
			}

			for (int j = 0; j < 6 - temp; j++) {
				tIconSize += "0";
			}

			String text = tIconSize + m_WinnerRecord[i].GetIconSize();
			canvas.drawText(text, (622) * m_ScaleW, 50 * m_ScaleH, coinPaint);
		}

		// 格子元素显示
		for (int i = 0; i < m_TileCount; i++) {
			appleTileArray[i].OnDraw(canvas, paint);
		}

		if (showToubi) {
			float gifx = (730) * m_ScaleW;
			float gify = (90) * m_ScaleH;
			long now = android.os.SystemClock.uptimeMillis();

			if (mTouBiStart == 0) { // first time
				mTouBiStart = now;
			}
			if (mTouBiMovie != null) {

				int dur = mTouBiMovie.duration();
				if (dur == 0) {
					dur = 1000;
				}
				int relTime = (int) ((now - mTouBiStart) % dur);
				mTouBiMovie.setTime(relTime);
				mTouBiMovie.draw(canvas, gifx, gify);
			}
		}

		// 胜利动画
		if (m_WinnerAppleID >= 0) {
			float gifx = (210 + 50) * m_ScaleW;
			float gify = (135 + 30) * m_ScaleH;
			if (true == m_show_gif) {
				long now = android.os.SystemClock.uptimeMillis();

				if (mMovieStart == 0) { // first time
					mMovieStart = now;
				}
				if (mMovie != null) {

					int dur = mMovie.duration();
					if (dur == 0) {
						dur = 1000;
					}
					int relTime = (int) ((now - mMovieStart) % dur);
					mMovie.setTime(relTime);
					if (special) {
						mMovie.draw(canvas, gifx, gify - 100 * m_ScaleH);
					} else {
						mMovie.draw(canvas, gifx, gify);
					}
				}
			}

			if (resultShuaizi != -1 && !m_show_shuaizi_gif) {
				Paint resultPaint = new Paint();
				String familyName_Player = "宋体";
				Typeface font_Player = Typeface.create(familyName_Player,
						Typeface.BOLD);
				resultPaint.setTypeface(font_Player);
				resultPaint.setColor(Color.YELLOW); // 为画笔设置颜色
				resultPaint.setTextSize(64 * m_ScaleH); // 为画笔设置字体大小
				resultPaint.setAntiAlias(true); // 设置抗锯齿
				canvas.drawText(resultShuaizi + "", gifx + (130 - 50)
						* m_ScaleW, gify + (110 - 30) * m_ScaleH, resultPaint);
			}

			if (true == m_show_shuaizi_gif) {
				long now = android.os.SystemClock.uptimeMillis();
				if (mNumberMovieStart == 0) { // first time
					mNumberMovieStart = now;
				}
				if (mNumberMovie != null) {
					int dur = mNumberMovie.duration();
					if (dur == 0) {
						dur = 1000;
					}
					int relTime = (int) ((now - mNumberMovieStart) % dur);
					mNumberMovie.setTime(relTime);
					mNumberMovie.draw(canvas, gifx, gify);
				}
			}
		}
		// 胜利中间元素显示
		if (m_WinnerAppleID >= 0) {
			// canvas.drawText(m_WinnerRecord[0].GetWinIconSize() + "",
			// 253 * m_ScaleW, 177 * m_ScaleH, m_WinnerPaint[0]);

			if (m_WinnerRecord[0].GetWinIconSize() > 0) {
				m_ShowBigBtnX = 228 * m_ScaleW;
				m_ShowBigBtnY = 288 * m_ScaleH;
				canvas.drawBitmap(m_BitMapArray[7], m_ShowBigBtnX,
						m_ShowBigBtnY, paint);
				m_ShowSmallBtnX = m_ShowGetCoinBtnX + 82 * m_ScaleW;
				m_ShowSmallBtnY = m_ShowBigBtnY;
				canvas.drawBitmap(m_BitMapArray[11], m_ShowSmallBtnX,
						m_ShowSmallBtnY, paint);
				m_ShowDoubleBtnX = 440 * m_ScaleW;
				m_ShowDoubleBtnY = 151 * m_ScaleH;
				canvas.drawBitmap(m_BitMapArray[28], m_ShowDoubleBtnX,
						m_ShowDoubleBtnY, paint);
			}

			m_ShowGetCoinBtnX = m_ShowBigBtnX + 82 * m_ScaleW;
			m_ShowGetCoinBtnY = m_ShowBigBtnY;
			canvas.drawBitmap(m_BitMapArray[14], m_ShowGetCoinBtnX,
					m_ShowGetCoinBtnY, paint);
		}

		if (special) {
			String ext = specialMap.get("ext").toString();
			String[] specialString = ext.substring(1, ext.length() - 1).split(
					",");
			for (String string : specialString) {
				string = string.trim();
				appleTileArray[Integer.parseInt(string)]
						.DrawBack(canvas, paint);
			}
			int type = Integer.parseInt(specialMap.get("type").toString());
			mMovie = Movie.decodeStream(fruitActivity.getResources()
					.openRawResource(R.drawable.gif_s_1 - 1 + type));
		}

		// 切换游戏
		if (m_backClick && AppleView.ButtonDown) {
			canvas.drawBitmap(m_BitMapArray[3], m_ShowBackBtnX - 6 * m_ScaleW,
					m_ShowBackBtnY - 3 * m_ScaleH, paint);
		}

		// 显示当前位置格子背景
		appleTileArray[m_currentpathid].DrawBack(canvas, paint);
	}

	// 重新开始
	public void Restart(float mouseX, float mouseY) {
		if (m_GetCoinIng) {
			return;
		}
		// 判断是否点击在"开始按钮"中
		int tBtnW = m_BitMapArray[13].getWidth();
		int tBtnH = m_BitMapArray[13].getHeight();

		float tLeft = m_ShowStartBtnX - 4;
		float tTop = m_ShowStartBtnY - 4;

		float tRight = tLeft + tBtnW + 4;
		float tBottom = tTop + tBtnH + 4;

		if (mouseX < tLeft)
			return;
		if (mouseX > tRight)
			return;
		if (mouseY < tTop)
			return;
		if (mouseY > tBottom)
			return;

		PlayClickSound();
		if (true == IsReady()) {
			startRun();
		}
	}

	public void startRun() {

		m_ResultRound = 4;
		special = false;
		Map result = RandomMetho.getRank(0);
		while (0 != Integer.parseInt(result.get("type").toString())) {
			result = RandomMetho.getRank(0);
		}
		m_ResultTarget = Integer.parseInt(result.get("target").toString());

		// 强制中奖
		if (AlarmReceiver.sureBone) {
			AlarmReceiver.sureBone = false;

			int bondType = (Math.abs(random.nextInt())) % m_AppleBitMapSize;
			boolean doIt = false;
			while (!doIt) {
				random = new Random(System.currentTimeMillis());
				bondType = (Math.abs(random.nextInt())) % m_AppleBitMapSize;
				for (int i = 0; i < m_AppleBitMapSize; i++) {
					int tRecordSize = m_WinnerRecord[0].GetRecord(bondType);
					if (tRecordSize > 0) {
						doIt = true;
						switch (bondType) {
						case 0:
							m_ResultTarget = 5;
							break;
						case 1:
							m_ResultTarget = 12;
							break;
						case 2:
							m_ResultTarget = 17;
							break;
						case 3:
							m_ResultTarget = 0;
							break;
						case 4:
							m_ResultTarget = 3;
							break;
						case 5:
							m_ResultTarget = 15;
							break;
						case 6:
							m_ResultTarget = 19;
							break;
						case 7:
							m_ResultTarget = 7;
							break;
						default:
							break;
						}
						break;
					}
					if (doIt) {
						break;
					}
				}
			}
		}
		m_CurrentRoundCount = 1;
		m_Timer = 500;
		m_DecTimer = (m_Timer - m_MinTimer) / m_TileCount;
		m_starttime = System.nanoTime(); // 获取当前时间，单位为纳秒
		m_RunNum++;
		m_bStartRun = true; // 开始
		m_WinnerState = false;
		m_GetCoinIng = false;
		m_CurrWinnerID = 0; // 当前玩家
	}

	// 是否可以开始[检查是否有下注]
	public boolean IsReady() {
		boolean tPutIcon = false;
		if (false == m_bStartRun) {
			m_startClick = true;
			m_startClickTime = System.nanoTime();// 获取当前时间，单位为纳秒
			for (int i = 0; i < m_AppleBitMapSize; i++) {
				for (int j = 0; j < m_WinnerSize; j++) {
					if (m_WinnerRecord[j].GetRecord(i) > 0) {
						tPutIcon = true;
						break;
					}
				}
			}
		}

		if (!tPutIcon && !m_bStartRun) {
			for (int i = 0; i < m_AppleBitMapSize; i++) {
				for (int j = 0; j < m_WinnerSize; j++) {
					m_WinnerRecord[j].AddRecord(i);
				}
			}
		}
		return tPutIcon;
	}

	// 加币
	public boolean AddIcon(float mouseX, float mouseY) {
		if (false == m_bStartRun) {
			// 显示玩家金币情况
			for (int i = 0; i < m_WinnerSize; i++) {
				long tIconSize = m_WinnerRecord[i].GetIconSize();
				if (0 == tIconSize) {
					int tLeft = (int) (500 * m_ScaleW + i * 70 * m_ScaleW);
					int tTop = (int) (100 * m_ScaleW);
					int tRight = (int) (tLeft + 60 * m_ScaleW);
					int tBottom = (int) (tTop + 125 * m_ScaleH);

					if (i >= 4) {
						tTop = (int) (236 * m_ScaleW);
						tBottom = (int) (tTop + 125 * m_ScaleH);
					}

					if (mouseX < tLeft)
						continue;
					if (mouseX > tRight)
						continue;
					if (mouseY < tTop)
						continue;
					if (mouseY > tBottom)
						continue;

					m_WinnerRecord[i].AddIcon(1);

					// 播放放金币声音
					PlayIconSound();
					return true;
				}
			}
		}
		return false;
	}

	// 投注
	public boolean DecIcon(float mouseX, float mouseY) {
		if (false == m_bStartRun) {

			if (mouseX > 700 * m_ScaleW && mouseX < 780 * m_ScaleW
					&& mouseY < 215 * m_ScaleH && mouseY > 100 * m_ScaleH) {
				if (lyeClick) {
					if ((double) ((System.nanoTime() - lyeClickTime) / 1000 / 1000) < 500) {
						m_WinnerRecord[0].AddIcon(100);
					}
					lyeClick = false;
				} else {
					//暗码 苹果 12 橙子10 香蕉35 铃铛10 Bar 20 双7 2 星星6 西瓜 13 
					if (m_WinnerRecord[0].getRecordByIndex(0) == 12 &&
							m_WinnerRecord[0].getRecordByIndex(1) == 10 &&
							m_WinnerRecord[0].getRecordByIndex(2) == 35 &&
							m_WinnerRecord[0].getRecordByIndex(3) == 10 &&
							m_WinnerRecord[0].getRecordByIndex(4) == 20 &&
							m_WinnerRecord[0].getRecordByIndex(5) == 2 &&
							m_WinnerRecord[0].getRecordByIndex(6) == 6 &&
							m_WinnerRecord[0].getRecordByIndex(7) == 13) {
						lyeClick = true;
						lyeClickTime = System.nanoTime();
					}
				}
			}

			for (int i = 0; i < m_AppleBitMapSize; i++) {
				int tLeft = (int) (613 * m_ScaleW);
				int tTop = (int) (102 * m_ScaleH + i * 82 * m_ScaleH);
				int tRight = (int) (tLeft + 70 * m_ScaleW);
				int tBottom = (int) (tTop + 56 * m_ScaleH);

				if (i >= 4) {
					tLeft = (int) (55 * m_ScaleW);
					tTop = (int) (110 * m_ScaleH + (i - 4) * 78 * m_ScaleW);
					tRight = (int) (tLeft + 70 * m_ScaleW);
					tBottom = (int) (tTop + 56 * m_ScaleH);
				}
				if (mouseX < tLeft)
					continue;
				if (mouseX > tRight)
					continue;
				if (mouseY < tTop)
					continue;
				if (mouseY > tBottom)
					continue;
				PlayClickSound();

				if (mouseY > tTop + 56 / 2 * m_ScaleH) {
					m_WinnerRecord[m_CurrWinnerID].DecRecord(i);
				} else {
					m_WinnerRecord[m_CurrWinnerID].AddRecord(i);
				}

				showToubi = true;
				toubiStartTime = System.nanoTime();
				return true;
			}
		}
		return false;
	}

	// 处理刷宝
	public void RefreshBaoWu(int vBaoWuID) {
		// 播放放金币声音
		PlayIconSound();
	}

	// 结算
	public void CalculateWinner() {
		m_WinnerAppleID = -1;
		m_DoubleBtnAdd = false;
		specialMap = null;
		// m_currentpathid = 3;

		// 计算结果
		int tCurrentImageId = appleTileArray[m_currentpathid].GetImageID();
		boolean tWin = false;

		int tImageType = tCurrentImageId;

		if (tCurrentImageId < 0) {
			if (tCurrentImageId == ICON_JIHUI) {
				startRun();
				return;
			}
		} else if (tCurrentImageId == ICON_JIHUI) {
			
		} else {
			for (int j = 0; j < m_WinnerSize; j++) {
				int tRecordSize = m_WinnerRecord[j].GetRecord(tImageType);
				// 如果下注数大于0
				if (tRecordSize > 0) {
					int[] smallFriut = { 1, 6, 8, 11, 14, 16, 18, 20, 22, 23 };
					int[] bigFriute = { 0, 3, 5, 7, 10, 12, 13, 15, 17, 19 };
					Map result = RandomMetho.getRank(1);
					int type = Integer.parseInt(result.get("type").toString());

					for (int i : smallFriut) {
						if (m_currentpathid == i) {
							tWinIconSisze = tRecordSize * 3;
							if (type == 2 || type == 3 || type == 4
									|| type == 5 || type == 7) {
								special = true;
								specialMap = result;
							}

							if (m_currentpathid == 3 && type == 6) {
								special = true;
								specialMap = result;
							}
							break;
						}
					}

					for (int i : bigFriute) {
						if (m_currentpathid == i) {
							tWinIconSisze = m_Winsize[tCurrentImageId]
									* tRecordSize;
							if (type == 1 || type == 3 || type == 4
									|| type == 7) {
								special = true;
								specialMap = result;
							}
							break;
						}
					}

					if (2 == m_currentpathid) {
						tWinIconSisze = tRecordSize * 40;
						if (type == 6) {
							special = true;
							specialMap = result;
						}
					}

					if (4 == m_currentpathid) {
						tWinIconSisze = tRecordSize * 60;
						if (type == 6) {
							special = true;
							specialMap = result;
						}
					}

					m_WinnerAppleID = tImageType;

					if (special) {
						String ext = specialMap.get("ext").toString();
						String[] specialString = ext.substring(1,
								ext.length() - 1).split(",");
						for (String string : specialString) {
							string = string.trim();
							int imageID = appleTileArray[Integer
									.parseInt(string)].GetImageID();

							for (int i : smallFriut) {
								if (Integer.parseInt(string) == i) {
									tWinIconSisze += tRecordSize * 3;
								}
							}

							for (int i : bigFriute) {
								if (Integer.parseInt(string) == i) {
									tWinIconSisze += m_Winsize[imageID]
											* tRecordSize;
								}
							}

							if (2 == Integer.parseInt(string)) {
								tWinIconSisze += tRecordSize * 40;
							}

							if (4 == Integer.parseInt(string)) {
								tWinIconSisze += tRecordSize * 60;
							}
						}
					}

					m_WinnerRecord[j].SetWinIconSize(tWinIconSisze);
					tWin = true;
					m_WinnerState = true;
					m_show_gif = true;

					if (special) {
						mMovie = Movie.decodeStream(fruitActivity
								.getResources().openRawResource(
										R.drawable.gif_s_1 - 1 + type));
					} else {
						mMovie = Movie
								.decodeStream(fruitActivity.getResources()
										.openRawResource(
												R.drawable.gif_4 - 4
														+ tCurrentImageId));
					}

					mNumberMovie = Movie.decodeStream(fruitActivity
							.getResources().openRawResource(
									R.drawable.gif_shuaizi));
				}
			}
		}

		if (false == tWin) {
			// 清空
			for (int j = 0; j < m_WinnerSize; j++) {
				m_WinnerRecord[j].CleanRecord(-1);
			}
			m_bStartRun = false;
		}
	}

	// 判断胜利中间面板点击
	public boolean winBtnClick(float mouseX, float mouseY) {
		if (m_show_shuaizi_gif) {
			return false;
		}
		if (m_WinnerAppleID >= 0 && m_bStartRun) {
			m_BigBtnClick = false;
			m_SmallBtnClick = false;
			m_GetCoinBtnClick = false;
			// 判断是否点击在"开始按钮"中
			int tBtnW = m_BitMapArray[7].getWidth();
			int tBtnH = m_BitMapArray[7].getHeight();

			float tLeft = m_ShowBigBtnX - 4;
			float tTop = m_ShowBigBtnY - 4;

			float tRight = tLeft + tBtnW + 4;
			float tBottom = tTop + tBtnH + 4;

			if (mouseX < tLeft || mouseX > tRight || mouseY < tTop
					|| mouseY > tBottom)
				m_BigBtnClick = false;
			else {
				m_BigBtnClick = true;
			}

			if (m_BigBtnClick) {
				if (m_WinnerRecord[0].GetWinIconSize() == 0) {
					return false;
				}
				// 押大小逻辑
				guess = 0;
				bigSmallGame();
				PlayClickSound();
				return true;
			}

			tLeft = m_ShowSmallBtnX - 4;
			tTop = m_ShowSmallBtnY - 4;

			tRight = tLeft + tBtnW + 4;
			tBottom = tTop + tBtnH + 4;

			if (mouseX < tLeft || mouseX > tRight || mouseY < tTop
					|| mouseY > tBottom)
				m_SmallBtnClick = false;
			else {
				m_SmallBtnClick = true;
			}

			if (m_SmallBtnClick) {
				// 押大小逻辑
				if (m_WinnerRecord[0].GetWinIconSize() == 0) {
					return false;
				}
				guess = 1;
				PlayClickSound();
				bigSmallGame();
				return true;
			}

			tLeft = m_ShowGetCoinBtnX - 4;
			tTop = m_ShowGetCoinBtnY - 4;

			tRight = tLeft + tBtnW + 4;
			tBottom = tTop + tBtnH + 4;

			if (mouseX < tLeft || mouseX > tRight || mouseY < tTop
					|| mouseY > tBottom)
				m_GetCoinBtnClick = false;
			else {
				m_GetCoinBtnClick = true;
			}

			if (m_GetCoinBtnClick) {
				// 退币逻辑
				PlayClickSound();
				PlayIconSound();
				getIconFromWin();
				return true;
			}

			tLeft = m_ShowDoubleBtnX - 4;
			tTop = m_ShowDoubleBtnY - 4;

			tBtnW = m_BitMapArray[28].getWidth();
			tBtnH = m_BitMapArray[28].getHeight();

			tRight = tLeft + tBtnW + 4;
			tBottom = tTop + tBtnH + 4;

			if (mouseX < tLeft || mouseX > tRight || mouseY < tTop
					|| mouseY > tBottom)
				m_DoubleBtnClick = false;
			else {
				m_DoubleBtnClick = true;
			}

			if (m_DoubleBtnClick) {
				if (m_WinnerRecord[0].GetWinIconSize() == 0) {
					return false;
				}
				PlayClickSound();
				// 翻倍逻辑
				if (!m_DoubleBtnAdd
						&& m_WinnerRecord[0].GetIconSize() >= m_WinnerRecord[0]
								.GetWinIconSize()) {
					m_WinnerRecord[0]
							.SetIconSize(m_WinnerRecord[0].GetIconSize()
									- m_WinnerRecord[0].GetWinIconSize());
					m_WinnerRecord[0].SetWinIconSize(m_WinnerRecord[0]
							.GetWinIconSize() * 2);
					m_DoubleBtnAdd = true;
				}
				return true;
			}
		} else if (!m_bStartRun) {
			m_backClick = false;
			int tBtnW = m_BitMapArray[9].getWidth();
			int tBtnH = m_BitMapArray[9].getHeight();

			float tLeft = m_ShowBackBtnX - 4;
			float tTop = m_ShowBackBtnY - 4;

			float tRight = tLeft + tBtnW + 4;
			float tBottom = tTop + tBtnH + 4;

			if (mouseX < tLeft || mouseX > tRight || mouseY < tTop
					|| mouseY > tBottom)
				m_backClick = false;
			else {
				PlayClickSound();
				m_backClick = true;
			}

			if (m_backClick && !m_bStartRun) {
				// 切换游戏
				fruitActivity.finish();
				PlayClickSound();
				return true;
			}
		}

		return false;
	}

	private void bigSmallGame() {
		m_DoubleBtnAdd = false;
		m_show_gif = false;
		m_show_shuaizi_gif = true;
		m_shuaiziStarttime = System.nanoTime();
		if (m_WinnerAppleID >= 0) {
			// 清空
			for (int j = 0; j < m_WinnerSize; j++) {
				m_WinnerRecord[j].CleanRecord(-1);
			}
		}
	}

	private void getIconFromWin() {
		if (m_WinnerAppleID >= 0) {
			// 清空
			for (int j = 0; j < m_WinnerSize; j++) {
				m_WinnerRecord[j].CleanRecord(-1);
			}
		}
		m_WinnerAppleID = -1;
		m_bStartRun = false;
		m_WinnerAppleID = -1;
		resultShuaizi = -1;
		m_GetCoinIng = true;
		Log.d("game", "getcoin");
	}

	public void clearBtnState() {
		m_startClick = false;
		m_backClick = false;
		m_GetCoinBtnClick = false;
		m_SmallBtnClick = false;
		m_BigBtnClick = false;
		m_getMoneyClick = false;

	}

	/**
	 * 跑马灯
	 */
	// private SurfaceHolder mHolder;
	private String mText = "";
	private int xOffset = (int) ((800 - 223) * m_ScaleW);

	private void handlerDrawInternal(Canvas canvas) {
		 if (canvas == null && !AlarmReceiver.houseLight) {
			 return;
		 }
		mText = AlarmReceiver.houseLightMsg;
		
		m_WinnerRecord[0].AddIcon(AlarmReceiver.hourseCredit);
		AlarmReceiver.hourseCredit = 0;
		// canvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG); // 创建画笔
		paint.setTextSize((int) (20 * m_ScaleH));
		paint.setColor(Color.WHITE);

		// font height
		float fontHeight = getFontHeight(paint);

		// int height = mHolder.getSurfaceFrame().height();
		// int width = mHolder.getSurfaceFrame().width();
		float height = 70 * m_ScaleH;
		float width = 290 * m_ScaleW;

		Rect textBound = new Rect();
		paint.getTextBounds(mText.toCharArray(), 0, mText.length(), textBound);

		int textWidth = textBound.width();

		// base line
		float baseLineY = height / 2 + fontHeight / 2;
		canvas.drawText(mText, xOffset, baseLineY, paint);

		if (xOffset < (width - textWidth)) {
			xOffset = (int) ((800 - 223) * m_ScaleW);
		} else {
			xOffset -= 2;
		}

	}

	private static float getFontHeight(Paint paint) {
		Rect bounds = new Rect();
		paint.getTextBounds("这", 0, 1, bounds);
		return bounds.height();
	}

	private static float pixelsToSp(Context context, Float px) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return px / scaledDensity;
	}

	private static float spToPixel(Context context, Float sp) {
		float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		return sp * scaledDensity;
	}
	
	public Bitmap ScaleToFitXYRatio(Bitmap bm,float xRatio,float yRatio){
        float width=bm.getWidth();
        float height=bm.getHeight();
        Matrix m1=new Matrix();
        m1.postScale(xRatio, yRatio);
        Bitmap bmResult=Bitmap.createBitmap(bm, 0, 0, (int)width, (int)height, m1, true);
        return bmResult;
}
}
