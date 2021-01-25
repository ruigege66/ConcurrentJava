package com.ruigege.LockSourceAnalysis6;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

public class FIFOMutex {
	private final AtomicBoolean locked = new AtomicBoolean(false); // һ��boolean�����
	private final Queue<Thread> waiters = new ConcurrentLinkedQueue<Thread>(); // һ���߲�������
	
	public void lock() {
		boolean wasInterrupted = false; // �жϵı�־
		Thread current = Thread.currentThread();
		waiters.add(current); // �������������߳�
		// (1)
		while(waiters.peek() != current || !locked.compareAndSet(false,true)) {
			// ��ϰcompareAndSet��������һ�����������ε�ֵ���ڶ�����������������ε�ֵ����ô��
			// ����Ϊ�ڶ���������Ȼ�󷵻�true
			LockeSupport.park(this);
			if(Thread.interrupted()) { // ��2��
				wasInterrupted = true;
			}	
		}
		waiters.remove();
		if(wasInterrupted) { // ��3��
			current.interrupt();
		}
	}
	public void unlock() {
		locked.set(false);
		LockSupport.unpark(waiters.peek());
		
		
		
	}
}
