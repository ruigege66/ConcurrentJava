package com.ruigege.threadFoundation1;

public class DaemonThreadTest {

	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					//我们这里搞一个死循环也就是让子线程不停
				}
			}
		});
		
		thread1.setDaemon(true);
		thread1.start();
		
		System.out.println("主线程已经结束了，看一看后面还有没有光标在动");
		
	}
}
