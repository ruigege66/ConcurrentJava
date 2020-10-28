package com.ruigege.threadFoundation1;

public class UserYieldMethod implements Runnable{
	
	public UserYieldMethod() {
		Thread thread = new Thread(this);
		thread.start();
	}
		
	@Override
	public void run() {
		System.out.println("开始一个线程"+Thread.currentThread());
		Thread.yield();
		System.out.println("结束一个线程"+Thread.currentThread());
		
	}
	
	public static void main(String[] args) {
		new UserYieldMethod();
		new UserYieldMethod();
		new UserYieldMethod();
	}
}
