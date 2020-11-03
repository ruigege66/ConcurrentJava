package com.ruigege.threadFoundation1;

public class TestViolate {
	
	public volatile int inc =1;
	
	
	public static void main(String[] args) {
		TestViolate test = new TestViolate();
	
		for(int i=0;i<10;i++) {
			//����ʮ���̣߳���inc������������
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
			Thread.yield();//������ڻ�û�н������̣߳�����Ҫ�����ó�CPU����������
		}
		System.out.println(test.inc);
	}
	
}
