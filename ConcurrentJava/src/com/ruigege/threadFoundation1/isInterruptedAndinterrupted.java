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
		
		System.out.println("���߳�isInterrupted:"+threadOne.isInterrupted());
		System.out.println("���߳�Interrupted:"+threadOne.interrupted());
		System.out.println("���߳�Interrupted:"+Thread.interrupted());
		System.out.println("���߳�isInterrupted:"+threadOne.isInterrupted());
		
		threadOne.join();
	}
}
