package com.ruigege.threadFoundation1;

public class FunctionWait {
	public static void main(String[] args) throws InterruptedException{
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("����һ�����߳�");
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("����һ����һ�����߳�");
			}
		});
		thread1.start();
		thread2.start();
		thread1.wait(1000);
		thread1.join();
		thread2.join();
		
		
	}
}
