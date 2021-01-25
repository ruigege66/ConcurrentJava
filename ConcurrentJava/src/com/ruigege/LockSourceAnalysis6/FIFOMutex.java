package com.ruigege.LockSourceAnalysis6;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {
	private final AtomicBoolean locked = new AtomicBoolean(false); // 一个boolean类的锁
	private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>(); // 一个高并发队列
	
	public void lock() {
		boolean wasInterrupted = false; // 中断的标志
		Thread current = Thread.currentThread();
		waiters.add(current); // 队列中添加这个线程
		// (1)
		while(waiters.peek() != current || !locked.compareAndSet(false,true)) {
			// 复习compareAndSet方法，第一个参数是期盼的值，第二个就是如果就是期盼的值，那么就
			// 设置为第二个参数，然后返回true
			LockeSupport.park(this);
			if(Thread.interrupted()) { // （2）
				wasInterrupted = true;
			}	
		}
		waiters.remove();
		if(wasInterrupted) { // （3）
			current.interrupt();
		}
	}
	public void unlock() {
		locked.set(false);
		LockSupport.unpark(waiters.peek());
		
		
		
	}
}
