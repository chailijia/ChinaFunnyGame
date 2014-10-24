package com.tiger.apple;

import android.R.integer;
import android.graphics.Color;

public class WinnerRecord {

	int m_WinnerID; // ID
	long m_IconSize; // 我的金币数量
	long m_IconWin; // 当局赢得的金币数量
	int m_AppleRecord[]; // 对应的水果下注数量
	int m_Color; // 色彩

	// 构造函数
	public WinnerRecord(int vWinnerID) {
		m_WinnerID = vWinnerID;
		switch (m_WinnerID) {
		case 0:
			m_Color = Color.YELLOW;
			break;
		case 1:
			m_Color = Color.GREEN;
			break;
		case 2:
			m_Color = Color.BLUE;
			break;
		case 3:
			m_Color = Color.WHITE;
			break;
		}
		m_AppleRecord = new int[9];
		for (int i = 0; i < 9; i++) {
			m_AppleRecord[i] = 0;
		}
		m_IconSize = 0;
		m_IconWin = 0;
	}

	// 取得色彩
	public int GetColor() {
		return m_Color;
	}

	// 设置金币数量
	public void SetIconSize(long vIconSize) {
		m_IconSize = vIconSize;
	}

	// 取得金币数量
	public long GetIconSize() {
		return m_IconSize;
	}

	public void SetWinIconSize(long iconWin) {
		m_IconWin = iconWin;
	}

	public long GetWinIconSize() {
		return m_IconWin;
	}

	// 增加金币数量
	public void AddIcon(int vAddIconSize) {
		m_IconSize += vAddIconSize;
	}

	// 减少金币数量
	public void DecIcon() {
		if (m_IconSize > 0) {
			m_IconSize--;
		}
	}

	// 清空金币
	public void CleanIcon() {
		m_IconSize = 0;
	}

	// 设置对应的水果的数量
	public void SetRecord(int vAppleIndex, int vRecord) {
		m_AppleRecord[vAppleIndex] = vRecord;
	}

	public int GetRecord(int vAppleIndex) {
		return m_AppleRecord[vAppleIndex];
	}

	// 增加对应的水果的数量
	public void AddRecord(int vAppleIndex) {
		if (m_IconSize > 0) {
			m_AppleRecord[vAppleIndex]++;
			m_IconSize--;
		}
	}

	// 增加对应的水果的数量
	public void DecRecord(int vAppleIndex) {
		if (m_IconSize > 0) {
			if (m_AppleRecord[vAppleIndex] > 0) {
				m_AppleRecord[vAppleIndex]--;
				m_IconSize++;
			}
		}
	}

	// 清空对应的水果数量
	public void CleanRecord(int vAppleIndex) {
		if (vAppleIndex >= 0) {
			m_AppleRecord[vAppleIndex] = 0;
		} else {
			for (int i = 0; i < 9; i++) {
				m_AppleRecord[i] = 0;
			}
		}
	}

	public int getRecordByIndex(int vAppleIndex) {
			return m_AppleRecord[vAppleIndex];
	}
}
