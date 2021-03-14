package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ReentrantLock {

	Sync sync;
	
	public ReentrantLock() {
		sync = new NonfairLock();
	}
	
	public ReentrantLock(boolean fair) {
		sync = fair? new FairLock():new NonfairLock();
	}
	
	public void lock() {
		sync.lock();
	}
	
	
}
class NonfairLock extends Sync{
	final void lock() {
		// CAS设置状态值
		if(compareAndSetState(0,1)) {
			setExclusiveOwnerThread(Thread.currentThread())
		}else {
			acquire(1);
		}
	}
	
	public final void acquire(int arg) {
		// 调用ReentrantLock重写的tryAcquire方法
		if(!tryAcquire(arg) && acquireQueued(addWaiter(Node.EXCLUSIVE) ,arg)) {
			selfInterrupt();
		}
	}
	
	protected final boolean tryAcuqire(int acquires) {
		return nonfairTryAcquire(acquires);
	}
	
	final boolean nonfairTryAcquire(int acquires) {
		final Thread current = Thread.currentThread();
		int c = getState();
		
		if(c == 0) {
			// 当前AQS状态值为0，也就是没有线程获取到了该锁，那么我们让当前线程占用该锁
			if(compareAndSetState(0,acquires)) {
				setExlusiveOwnerThread(current);
				return true;
			}else if(current == getExlusiveThread()){
				// 该锁已经被当前线程占用，那么也就是重入的情况
				int nextc = c + acquires;
				if(nextc <0) { // 这种情况就是重入的次数太多了，超过了int正值的范畴
					throw new Error("Maxium lock count exceeded");
				}
				setState(nextc);
				return true;
			}else {
				return false;
			}
		}
	}
}

abstract class Sync extends AbstractQueuedSynchronizer{
	
}