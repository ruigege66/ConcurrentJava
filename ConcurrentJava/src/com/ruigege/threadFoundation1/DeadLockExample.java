package com.ruigege.threadFoundation1;

public class DeadLockExample {
	//������Դ
	private static Object object1 = new Object();
	private static Object object2 = new Object();

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized(object1) {
					System.out.println("�߳�1�������Դ1");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized(object2) {
						System.out.println("�߳�1�������Դ2");
					}
					
				}

			}
		});
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized(object1) {
					System.out.println("�߳�2�������Դ2");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					synchronized(object2) {
						System.out.println("�߳�2�������Դ1");
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
