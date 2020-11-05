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
				threadLocal.set("共享变量1");
				System.out.println("线程1的共享变量是：");
				printContentOfThreadLocal(threadLocal);
				System.out.println("线程1去除共享变量后的值为:" + threadLocal.get());
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				threadLocal.set("共享变量2");
				System.out.println("线程2的共享变量是：");
				printContentOfThreadLocal(threadLocal);
				System.out.println("线程2去除共享变量后的值为:" + threadLocal.get());
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
