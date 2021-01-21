package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class Test2 {
	public static void main(String[] args) {
		System.out.println("李天阳");
		// 使当前线程获取到许可证
		LockSupport.unpark(Thread.currentThread());
		// 再次调用park方法
		LockSupport.park();
		System.out.println("end park");
		int name = 5;
		switch(name) {
			case 1:
				System.out.println("litianyang");
				break;
			case 5:
				System.out.println("goutianyang");
				break;
			default:
				System.out.println("没见过");
				
		}
	}
}
