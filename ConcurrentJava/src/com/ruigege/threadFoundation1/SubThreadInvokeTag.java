package com.ruigege.threadFoundation1;

public class SubThreadInvokeTag {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					
				}
				System.out.println("子线程isInterruped()状态："+Thread.currentThread().isInterrupted());
				System.out.println("子线程interruped()状态："+Thread.currentThread().interrupted());
				System.out.println("子线程isInterruped()状态："+Thread.currentThread().isInterrupted());
			}
		});
		thread.start();
		thread.interrupt();
		System.out.println("主线程isInterrupted状态"+Thread.currentThread().isInterrupted());
		thread.join();
		
	}
}
