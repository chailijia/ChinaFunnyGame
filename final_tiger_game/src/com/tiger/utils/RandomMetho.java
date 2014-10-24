package com.tiger.utils;

/**
 *老虎机算法
 *原理，一个随机数的算法
 *
 *		r普通概率：  0.056
 *		a 苹果：10   0.015
 *		b 芒果：15   0.04
 *		c 橘子：20   0.03
 *		d 铜铃：25   0.012
 *		e 西瓜：30   0.02 
 *		f 双星：35   0.017
 *		g 77   ：40  0.015
 *		h 小bar：40  0.015
 *		i 中bar：60   0.01
 *		j 大bar：120  0.005
 *		命运： 0.1
 *		幸运： 0.13
 *		
 *		特殊大奖：  0.004
 *		两套概率
 *		特殊的玩法：
 *		0.0005  1   大三元    ：   中一个大元，有一定的几率同时中三个大元
 *		0.001   2   小三元    ：   中一个小元，有一定的几率同事中三个小元
 *		0.0005  3   六六大顺  ：   中一个大元或一个小元，有一定的几率，同时中三个大元和三个小元
 *		0.0005  4   对对碰    ：   中任意大元或小元，有一定的几率顺势很随机中一个灯，同时逆时针随机中一个灯
 *		0.0005  5   小兵立大功：   中一个小元有一定几率同事中一个10倍以上的灯
 *		0.0005  6   大满贯    ：   中一个bar（无论大，中，小），有一个几率三个bar全中
 *		0.0005  7   开火车    ：   中任意一个灯，有一定几率一整条边全中，不包含bar的边。
 *
 *
 ***/
import java.util.*;

public class RandomMetho {

	public static Map getRank(int type) {
		// 顺序表
		double oArr[] = { 1, 6, 8, 11, 14, 16, 18, 20, 22, 23 };
		double sArr[] = { 0, 2, 3, 4, 5, 7, 10, 12, 13, 15, 17, 19 };

		// 总共有24个概率，然后24个概率，包括大灯，小灯，命运，幸运
//		double rateArr[] = { 0.012, 0.054, 0.015, 0.005, 0.010, 0.030, 0.054,
//				0.020, 0.054, 0.110, 0.030, 0.054, 0.030, 0.012, 0.054, 0.015,
//				0.054, 0.040, 0.054, 0.017, 0.054, 0.110, 0.054, 0.054, 0.0001,
//				0.0020, 0.0001, 0.0001, 0.0015, 0.0001, 0.0001 };
		
		double rateArr[] = { 0.041, 0.041, 0.041, 0.041, 0.041, 0.041, 0.041,
							 0.041, 0.041, 0.041, 0.041, 0.041, 0.041, 0.041, 0.041, 0.041,
							 0.041, 0.041, 0.041, 0.041, 0.054, 0.041, 0.041, 0.041, 0.041,
							 0.500, 0.500, 0.500, 0.500, 0.500, 0.500};
		// 通杀部分
		int death = 9;
		double deathR = 0.03;
		// 返还率
		double retRate = 0.96;

		// 随机数的最大值
		int total = 1000;

		Random random1 = new Random();
		int rand = Math.abs(random1.nextInt(1000)) % total;
		Map result = new HashMap();

		if (type == 1) {
			for (int i = 0; i < 24; i++) {
				rateArr[i] = 0.008;
			}
			
			for (int i = 24; i < rateArr.length; i++) {
				rateArr[i] = 0.1;
			}
		}
		
		double level = 0;
		for (int i = 0; i < rateArr.length; i++) {

			double cur = rateArr[i];
			double start = total * level;
			double end = total * (level + cur);
			level += cur;

			if (rand >= start && rand < end && i < 24) {

				result.put("type", 0);
				result.put("target", i);
				result.put("ext", -1);
				return result;

			} else if (rand >= start && rand < end && 24 == i) { // 大三元, 220

				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 1);
				result.put("target", 0);
				result.put("ext", "{2,4,5}");
				return result;

			} else if (rand >= start && rand < end && 25 == i) { // 小三元, 12
				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 2);
				result.put("target", 1);
				result.put("ext", "{1,6,8}");
				return result;
			} else if (rand >= start && rand < end && 26 == i) { // 六六大顺 120
				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 3);
				result.put("target", rand2 > 5 ? 0 : 1);
				result.put("ext", "{11 , 14 , 16 , 4 , 5 , 7}");
				return result;
			} else if (rand >= start && rand < end && 27 == i) { // 对对碰 66
				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 4);
				result.put("target", 1);
				result.put("ext", "{ 22, 2}");
				return result;
			} else if (rand >= start && rand < end && 28 == i) { // 小兵立大功： 13
				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 5);
				result.put("target", 1);
				result.put("ext", "{10}");
				return result;
			} else if (rand >= start && rand < end && 29 == i) { // 大满贯 22
				int rand2 = Math.abs(random1.nextInt(10));
				result.put("type", 6);
				result.put("target", 2);
				if (1 == rand2) {
					result.put("ext", "{ 3, 4 ,5 }");
				} else {
					result.put("ext", -1);
				}
				return result;
			} else if (rand >= start && rand < end && 30 == i) { // 开火车 65
				int rand2 = Math.abs(random1.nextInt(10)) % 4;
				result.put("type", 7);
				result.put("target", 1);
				if (0 == rand2) {
					result.put("ext", "{ 0 , 1 , 2 , 3 , 4 , 5 , 6 }");
				} else if (1 == rand2) {
					result.put("ext", "{ 6 , 7 , 8 , 9 , 10 , 11 , 12}");
				} else if (2 == rand2) {
					result.put("ext", "{12 , 13 , 14 , 15 , 16 , 17 , 18}");
				} else if (3 == rand2) {
					result.put("ext", "{18 , 19 , 20 , 21 , 22 , 23 , 0}");
				}

				return result;
			}
		}

		result.put("type", 10);
		result.put("target", death);
		result.put("ext", 0);
		return result;
	}
}
