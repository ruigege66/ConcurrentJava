package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		// Condition��������Ӧ��һ������������һ��lock�����ǿ��Դ����������������
		
		lock.lock(); // ���Ȼ�ȡ��ռ��
		try {
			System.out.println("begin wait");
			condition.await(); // ������ǰ�̣߳��������̵߳�������������signal������ʱ�򣬱��������߳̾ͻ���
			// ����Żأ���Ҫע����Ǻ͵���Object��wait����һ���������û�л�ȡ��֮ǰ������
			// ����������await�������ͻ��׳�java.lang.IllegalMonitorStateException
			System.out.println("end wait");
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			lock.unlock(); // �ͷ�����
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
		// �����µ�Node�ڵ㣬���Ҳ��뵽��������ĩβ
		Node node = addContionWaiter();
		// �ͷŵ�ǰ�̻߳�ȡ������
		int savedState = fullyRelease(node);
		
		int interruptMode = 0;
		// ����park������������ǰ�߳�
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
		// ��������������ߣ������ unlock���׳��쳣
		int c = getState() - releases;
		if(Thread.currentThread() != getExclusiveOwnerThread()) {
			throw new IllegalMonitorStateException();
		}
		boolean free = false;
		// �����ǰ������Ĵ���Ϊ0��������������߳�
		if(c == 0) {
			free = true;
			setExclusiveOwnerThread(null);
		}
		// ���ÿ��������Ϊԭʼֵ-1
		setState(c);
		return free;
	}
}
