package com.tiger.utils;

/**
 *老虎机算法
 *原理，一个随机数的算法
 ***/
import java.util.*;

public class Tegr {
	public static int getRank(int type) {
//		double rate = 0.9;
//		double[] rateArr1 = { 3.0 / 40, 1.0 / 20, 3.0 / 100, 3.0 / 160,
//				3.0 / 200, 3.0 / 1000 };
//		double[] rateArr2 = { 1.0 / 12, 1.0 / 18, 1.0 / 30, 1.0 / 40, 1.0 / 60,
//				1.0 / 300 };
//		double[] target;
//		int total1 = 4000;
//		int total2 = 1800;
//		int total = 0;
//		double edge = 0; // 分割线，分割线之上的直接返回没有中奖
//		double tem = 0;
//
//		if (1 == type) {
//			target = rateArr2;
//			total = total2;
//		} else {
//			target = rateArr1;
//			total = total1;
//		}
		Random random1 = new Random(System.currentTimeMillis());
		return (Math.abs(random1.nextInt()) % 22);
//		int rand = Math.abs(random1.nextInt()) % total;
//		for (int i = 0; i < target.length; i++) {
//			tem += target[i];
//		}
//		edge = tem * total;
//		if (rand > edge || 0 >= rand) {
//
//			return 0;
//
//		} else {
//			double level = 0;
//			for (int i = 0; i < target.length; i++) {
//				double cur = target[i];
//				double start = total * level;
//				double end = total * (level + cur);
//				level += cur;
//
//				if (rand >= start && rand < end) {
//					return i + 1;
//				}
//			}
//			return 0;
//		}

	};

	/*
	 * public static void main(String arg[]){
	 * 
	 * for( int i = 0 ; i < 100;i ++){
	 * 
	 * int result = getRank( 0 ); System.out.println(result); }
	 * 
	 * }
	 */
}
