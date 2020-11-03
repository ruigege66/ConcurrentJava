package com.ruigege.threadFoundation1;

public class TestViolate {
	
	public volatile int inc =1;
	
	
	public static void main(String[] args) {
		TestViolate test = new TestViolate();
	
		for(int i=0;i<10;i++) {
			//创建十个线程，对inc进行自增操作
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					for(int j=0;j<10000;j++) {
						test.inc++;
					}
				}
			});
			thread.start();
		}
		
		while(Thread.activeCount()>1) {
			Thread.yield();//如果存在还没有结束的线程，就需要尽量让出CPU供它们运行
		}
		System.out.println(test.inc);
	}
	
}
