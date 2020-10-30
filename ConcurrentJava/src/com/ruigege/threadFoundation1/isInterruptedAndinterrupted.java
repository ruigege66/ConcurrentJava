package com.ruigege.threadFoundation1;

public class isInterruptedAndinterrupted {
	public static void main(String[] args) throws InterruptedException {
		Thread threadOne = new Thread(new Runnable() {
			@Override
			public void run() {
				for(;;) {
					
				}
			}
		});
		
		threadOne.start();
		threadOne.interrupt();
		
		System.out.println("子线程isInterrupted:"+threadOne.isInterrupted());
		System.out.println("子线程Interrupted:"+threadOne.interrupted());
		System.out.println("主线程Interrupted:"+Thread.interrupted());
		System.out.println("子线程isInterrupted:"+threadOne.isInterrupted());
		
		threadOne.join();
	}
}
