package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class TestPark {
	public void testPark() {
		LockSupport.park();
		LockSupport.park(this);
	}
	public static void main(String[] args) {
		System.out.println("��ʼpark����");
		TestPark testPark = new TestPark();
		testPark.testPark();
	}
	public static void park2(Object blocker) {
		// ��ȡ��ǰ�߳�
		Thread t = Thread.currentThread();
		// Thread��������һ��volatile Object blocker
		// �������setter�����������blocker��¼�����̵߳�blocker������
		setBlocker(t,blocker);
		// ����park�����Ը��߳̽�������
		UNSAFE.park(false,0L); // UNSAFE��ʵ�Ǹ��̵߳�Unsafe����������
		// ����ʡ��ǰ��Ķ��壬ֱ����������
		setBlocker(t,null);
		// ��������ְ�blocker������Ϊ�գ�������Ϊ�Ѿ�ֹͣ������
		// ���blocker����������߳�������ʱ����������ԭ���õ�
	}
	public static void parkUtile(Object blocker,long deadline) {
		Thread t = Thread.currentThread();
		setBlocker(t,blocker);
		UNSAFE.park(false,deadline);
		setBlocker(t,null);
	}

}
