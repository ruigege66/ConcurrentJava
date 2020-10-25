package com.ruigege.threadFoundation1;

public class MyThreadImplementsRunnable implements Runnable{
	@Override
	public void run() {
		System.out.println("这是一个实现Runable接口的多线程");
	}
	
}
