package com.tiger.apple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Tile {
	/*
	 * static final int FX_RIGHTTOP = 0; static final int FX_RIGHT = 1; static
	 * final int FX_RIGHTBOTTOM = 2; static final int FX_BOTTOM = 3; static
	 * final int FX_LEFTBOTTOM = 4; static final int FX_LEFT = 5; static final
	 * int FX_LEFTTOP = 6; static final int FX_TOP = 7;
	 */

	int TileX = 0; // 格子位置
	int TileY = 0;
	// int FangXiang = FX_RIGHTTOP; //方向 //
	int ImageID = 0; // 图像ID
	Bitmap Image;// 图像
	Bitmap bgImage;

	Tile() {

	}

	// 设置位置
	public void SetTile(int vTileX, int vTileY)// ,int vFangXiang)
	{
		TileX = vTileX;
		TileY = vTileY;
		// FangXiang = vFangXiang ;
	}

	// 取得位置X
	public int GetTileX() {
		return TileX;
	}

	// 取得位置Y
	public int GetTileY() {
		return TileY;
	}

	// 设置图像ID
	public void SetImage(int vBitMapID, Bitmap vBitMap) {
		ImageID = vBitMapID;
		Image = vBitMap;
	}

	// 取得图像ID
	public int GetImageID() {
		return ImageID;
	}

	// 绘制背景
	public void DrawBack(Canvas canvas,Paint paint) {

//		int tWidth = bgImage.getWidth();
//		int tHeight = bgImage.getHeight();
//
//		int tBorderSize = 2;
//
//		Paint tBackPaint = new Paint(); // 创建画笔对象
//		tBackPaint.setColor(Color.YELLOW); // 为画笔设置颜色
//
//		// 绘制背景图
//		canvas.drawRect(TileX - tBorderSize, TileY - tBorderSize, TileX
//				+ tWidth + tBorderSize, TileY + tHeight + tBorderSize,
//				tBackPaint);
//		
		canvas.drawBitmap(Image, TileX, TileY, paint);

	}

	// 绘制
	public void OnDraw(Canvas canvas, Paint paint) {
//		canvas.drawBitmap(bgImage, TileX, TileY, paint);
//		canvas.drawBitmap(Image, TileX, TileY, paint);
	}
}
