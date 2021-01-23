package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class TestPark {
	public void testPark() {
		LockSupport.park();
		LockSupport.park(this);
	}
	public static void main(String[] args) {
		System.out.println("开始park方法");
		TestPark testPark = new TestPark();
		testPark.testPark();
	}
	public static void park2(Object blocker) {
		// 获取当前线程
		Thread t = Thread.currentThread();
		// Thread对象中有一个volatile Object blocker
		// 这里调用setter方法，把这个blocker记录到该线程的blocker对象中
		setBlocker(t,blocker);
		// 调用park方法对该线程进行阻塞
		UNSAFE.park(false,0L); // UNSAFE其实是该线程的Unsafe变量，我们
		// 这里省略前面的定义，直接拿来解释
		setBlocker(t,null);
		// 最后我们又把blocker对象置为空，这是因为已经停止阻塞了
		// 这个blocker对象多用于线程阻塞的时候用来分析原因用的
	}
	public static void parkUtile(Object blocker,long deadline) {
		Thread t = Thread.currentThread();
		setBlocker(t,blocker);
		UNSAFE.park(false,deadline);
		setBlocker(t,null);
	}

}
