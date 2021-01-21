package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class TestParkAndUnpark {
	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("child thread begin park");
				//����park�����������Լ�
				LockSupport.park();
				System.out.println("child thread end park");
				System.out.println("������ѧ��һ����ݼ���sysout + alt +/ �ǿ���̨" + 
				"�����һ����ݼ�");
			}
		});
		
		// �������߳�
		thread.start();
		Thread.sleep(1000); // ���߳�����һ���ӣ�Ŀ�����ܹ������̼߳�ʱʹ��
		System.out.println("main thread begin unpark");
		LockSupport.unpark(thread); // ����unpark�������ܹ������߳�thread�������֤��
		// Ȼ��park��������
	}
}
