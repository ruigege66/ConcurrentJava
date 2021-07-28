package com.ruigege.LockSourceAnalysis6;

import java.util.ArrayList;

public static class ReentrantLockList {

	//�̲߳���ȫ��List
	private ArrayList<String> array = new ArrayList<String>();
	//��ռ��
	private volatile ReentrantLock lock = new ReentrantLock();
	
	//���Ԫ��
	public void add(String e) {
		lock.lock();
		try {
			array.add(e);
		}finally {
			lcok.unlock();
		}
	}
	//ɾ��Ԫ��
	public void remove(String e) {
		lock.lock();
		try {
			array.remove(e);
		}finally {
			lock.unlock();
		}
	}
	
	//��ȡ����
	public String get(int index) {
		lock.lock();
		try {
			return array.get(index);
		}finally {
			lock.unlock();
		}
	}
}
