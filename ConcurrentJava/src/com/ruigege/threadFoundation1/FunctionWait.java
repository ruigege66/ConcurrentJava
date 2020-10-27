package com.ruigege.threadFoundation1;

public class FunctionWait {
	public static void main(String[] args) throws InterruptedException{
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("这是一个子线程");
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("这是一个另一个子线程");
			}
		});
		thread1.start();
		thread2.start();
		thread1.wait(1000);
		thread1.join();
		thread2.join();
		
		
	}
}
