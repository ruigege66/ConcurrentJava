package com.ruigege.threadFoundation1;

public class FunctionWait {
	public static void main(String[] args) throws InterruptedException{
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("����һ�����߳�");
			}
		});
		
		thread.start();
		thread.wait(1000);
		thread.join();
		
	}
}
