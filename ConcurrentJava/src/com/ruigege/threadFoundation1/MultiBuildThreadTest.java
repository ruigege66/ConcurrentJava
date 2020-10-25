package com.ruigege.threadFoundation1;


public class MultiBuildThreadTest {
	public static void main(String[] args) {
		
		MyThreadExtendsType thread1 = new MyThreadExtendsType();
		thread1.start();
		Thread thread2 = new Thread(new MyThreadImplementsRunnable());
		thread2.start();
		
		
	}
}
