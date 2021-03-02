package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class NonReentrantLockME implements Lock,java.io.Serializable{
	// �ڲ�������
	private static class Sync extends AbstractQueueSynchronizer {
		// �Ƿ����Ѿ�������
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}
		
		// ���stateΪ0�����Ի�ȡ��
		public boolean tryAcquire(int acquires) {
			assert acquires == 1;
			if(compareAndSetState(0,1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}
		
		// �����ͷ���������stateΪ0
		protected boolean tryRelease(int release) {
			assert releases == 1;
			if(getState() == 0) {
				throw new IllegalMonitorStateException();
			}
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}
		
		// �ṩ���������ӿ�
		Condition newConditon() {
			return new ConditionObject();
		}
	}
	
	// ����һ��Sync��������Ĺ���
	private final Sync sync = new Sync();
	
	public void lock() {
		sync.acquire(1);
	}
	
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}
	
	public void unlock() {
		sync.release(1);
		
	}
	public Condition newCondition() {
		return sync.newConditon();
	}
	
	
	public boolean isLocked() {
		return sync.isHeldExclusively();
	}
	
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	public boolean tryLock(long timeout,TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1,unit.toNanos(timeout));
	}
}
