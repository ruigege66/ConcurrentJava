package com.ruigege.threadFoundation1;

public class SubThreadInvokeTag {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					
				}
				System.out.println("���߳�isInterruped()״̬��"+Thread.currentThread().isInterrupted());
				System.out.println("���߳�interruped()״̬��"+Thread.currentThread().interrupted());
				System.out.println("���߳�isInterruped()״̬��"+Thread.currentThread().isInterrupted());
			}
		});
		thread.start();
		thread.interrupt();
		System.out.println("���߳�isInterrupted״̬"+Thread.currentThread().isInterrupted());
		thread.join();
		
	}
}
