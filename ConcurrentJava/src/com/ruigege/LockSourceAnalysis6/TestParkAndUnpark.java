package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class TestParkAndUnpark {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("child thread begin park");
				//调用park方法，挂起自己
				LockSupport.park();
				System.out.println("child thread end park");
				System.out.println("今天又学了一个快捷键，sysout + alt +/ 是控制台" + 
				"输出的一个快捷键");
			}
		});
		
		// 启动子线程
		thread.start();
		Thread.sleep(1000); // 主线程休眠一秒钟，目的是能够让子线程及时使用
		System.out.println("main thread begin unpark");
		LockSupport.unpark(thread); // 调用unpark方法，能够让子线程thread持有许可证，
		// 然后park方法返回
	}
}
