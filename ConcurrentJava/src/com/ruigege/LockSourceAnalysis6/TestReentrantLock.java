package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		// Condition类是锁对应的一个条件变量，一个lock对象是可以创建多个条件变量的
		
		lock.lock(); // 首先获取独占锁
		try {
			System.out.println("begin wait");
			condition.await(); // 阻塞当前线程，当其他线程调用条件变量的signal方法的时候，被阻塞的线程就会这
			// 这里放回，需要注意的是和调用Object的wait方法一样，如果在没有获取锁之前调用了
			// 条件变量的await方法，就会抛出java.lang.IllegalMonitorStateException
			System.out.println("end wait");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock(); // 释放了锁
		}
		
		lock.lock();
		
		try {
			System.out.println("begin signal");
			condition.signal();
			System.out.println("end signal");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public final void await() throws InterruptedException {
		if(Thread.interrupted()) {
			throw new InterruptedException();
		}
		// 创建新的Node节点，并且插入到条件队列末尾
		Node node = addContionWaiter();
		// 释放当前线程获取到的锁
		int savedState = fullyRelease(node);
		
		int interruptMode = 0;
		// 调用park方法阻塞挂起当前线程
		while(!isOnSyncQueue(node)) {
			LockSupport.park(this);
			if((interruprMode = checkInterruptWhileWaiting(node)) != 0) {
				break;
			}
		}
	}
	
	public void unlock() {
		sync.release();
	}
	
	public final boolean tryRelease( int releases) {
		// 如果不是锁持有者，则调用 unlock则抛出异常
		int c = getState() - releases;
		if(Thread.currentThread() != getExclusiveOwnerThread()) {
			throw new IllegalMonitorStateException();
		}
		boolean free = false;
		// 如果当前可重入的次数为0，则清空锁持有线程
		if(c == 0) {
			free = true;
			setExclusiveOwnerThread(null);
		}
		// 设置可重入次数为原始值-1
		setState(c);
		return free;
	}
}
