package com.ruigege.threadFoundation1;

public class MyThreadExtendsType extends Thread {
	
	@Override
	public void run() {
		System.out.println("这是一个继承Thread类的多线程表示方法");
	}

}
