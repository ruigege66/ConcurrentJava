package com.ruigege.PricipleAnalyzingOfThreadLocalRandom3;

import java.util.Random;

public class RandomTest {

	public static void main(String[] args) {
		Random random = new Random();
		for(int i=0;i<5;i++) {
			System.out.println(random.nextInt(5));//输出五个5以内的随机数
		}
		
	}
}
