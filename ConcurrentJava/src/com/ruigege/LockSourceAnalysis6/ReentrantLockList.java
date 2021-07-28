package com.ruigege.LockSourceAnalysis6;

import java.util.ArrayList;

public static class ReentrantLockList {


	private ArrayList<String> array = new ArrayList<String>();
	//独占锁
	private volatile ReentrantLock lock = new ReentrantLock();
	
	//添加元素

	private ArrayList<String> array = new ArrayList<String>();
	//鐙崰閿�
	private volatile ReentrantLock lock = new ReentrantLock();
	
	//娣诲姞鍏冪礌

	public void add(String e) {
		lock.lock();
		try {
			array.add(e);
		}finally {
			lcok.unlock();
		}
	}

	public void remove(String e) {
		lock.lock();
		try {
			array.remove(e);
		}finally {
			lock.unlock();
		}
	}
	
	//获取数据
	public String get(int index) {
		lock.lock();
		try {
			return array.get(index);
		}finally {
			lock.unlock();
		}
	}
}
