package com.ruigege.threadFoundation1;

public class ThreadLocalTest {
	
	public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	public static void printContentOfThreadLocal(ThreadLocal threadLocal) {
		System.out.println(threadLocal.get());
		threadLocal.remove();
		
	}
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.set("�������1");
				System.out.println("�߳�1�Ĺ�������ǣ�");
				printContentOfThreadLocal(threadLocal);
				System.out.println("�߳�1ȥ������������ֵΪ:" + threadLocal.get());
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.set("�������2");
				System.out.println("�߳�2�Ĺ�������ǣ�");
				printContentOfThreadLocal(threadLocal);
				System.out.println("�߳�2ȥ������������ֵΪ:" + threadLocal.get());
			}
		});
		
		thread1.start();
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		
		thread2.start();
		
	}
}
