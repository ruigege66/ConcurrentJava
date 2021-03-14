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
		// CAS����״ֵ̬
		if(compareAndSetState(0,1)) {
			setExclusiveOwnerThread(Thread.currentThread())
		}else {
			acquire(1);
		}
	}
	
	public final void acquire(int arg) {
		// ����ReentrantLock��д��tryAcquire����
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
			// ��ǰAQS״ֵ̬Ϊ0��Ҳ����û���̻߳�ȡ���˸�������ô�����õ�ǰ�߳�ռ�ø���
			if(compareAndSetState(0,acquires)) {
				setExlusiveOwnerThread(current);
				return true;
			}else if(current == getExlusiveThread()){
				// �����Ѿ�����ǰ�߳�ռ�ã���ôҲ������������
				int nextc = c + acquires;
				if(nextc <0) { // ���������������Ĵ���̫���ˣ�������int��ֵ�ķ���
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