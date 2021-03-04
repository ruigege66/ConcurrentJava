package com.ruigege.LockSourceAnalysis6;

import java.util.Queue;
import java.util.concurrent.locks.Condition;

public class NonReentrantLockTest {
	
	final static NonReentrantLock lock = new NonReentrantLock();
	final static Condition notFull = lock.newCondition();
	final static Condition notEmpty = lock.newCondition();
	
	final static Queue<String> queue = new LinkedBlockingQueue<String>();
	final static int queueSize = 10;
	
	public static void main(String[] args) {
		Thread producer = new Thread(new Runnable() {
			public void run() {
				// ��ȡ��ռ��
				lock.lock();
				try {
					// (1)����������ˣ���ȴ�
					while(queue.size() == queueSize) {
						notEmpty.await();
					}
					// ��2�����Ԫ�ص�����
					queue.add("ele");
					// (3)���������߳�
					notFull.signalAll();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					// �ͷ���
					lock.unlock();
				}
			}
		});
		
		Thread consumer = new Thread(new Runnable() {
			public void run() {
				// ��ȡ��ռ��
				lock.lock();
				try {
					// ���п�
					while(0 == queue.size()) {
						notFull.await();
					}
					// ����һ��Ԫ��
					String ele = queue.poll();
					// ���������߳�
					notEmpty.signalAll();
				}catch(Exception e) {
					e.printStackTrace();
				}finally {
					// �ͷ���
					lock.unlock();
				}
			}
		});
		
		// �����߳�
		producer.start();
		consumer.start();
	}

}
