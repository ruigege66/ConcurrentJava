package com.ruigege.threadFoundation1;

public class DeadLockExample {
	//创建资源
	private static Object object1 = new Object();
	private static Object object2 = new Object();

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized(object1) {
					System.out.println("线程1获得了资源1");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized(object2) {
						System.out.println("线程1获得了资源2");
					}
					
				}

			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized(object1) {
					System.out.println("线程2获得了资源2");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized(object2) {
						System.out.println("线程2获得了资源1");
					}					
				}

			}
		});
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		
		
	}
}
