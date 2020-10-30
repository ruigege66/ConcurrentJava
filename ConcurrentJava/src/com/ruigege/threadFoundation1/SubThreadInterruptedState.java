package com.ruigege.threadFoundation1;

public class SubThreadInterruptedState {
	public static void main(String[] args) throws InterruptedException{
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					System.out.println("子线程没有中断");
				}
				System.out.println("子线程虽然中断了，但是并没有结束");
			}
		});
		
		thread1.start();
		Thread.sleep(5);//保证子线程能够执行起来
		thread1.interrupt();//中断子线程，可以理解为时间片这里不给它，一会再给它因此子线程会持续运行结束，这函数就是一个表姐而已
		System.out.println(thread1.isInterrupted());
		thread1.join();
		System.out.println("主线程结束");
		
	}
}
