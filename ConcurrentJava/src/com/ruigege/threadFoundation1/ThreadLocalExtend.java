package com.ruigege.threadFoundation1;

public class ThreadLocalExtend {

	public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.set0("我是子线程的");
				System.out.println(threadLocal.get());
			}
		});
		
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(threadLocal.get());
		
	}	
}
