package com.ruigege.threadFoundation1;

public class DaemonThreadTest {

	public static void main(String[] args) {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					//���������һ����ѭ��Ҳ���������̲߳�ͣ
				}
			}
		});
		
		thread1.setDaemon(true);
		thread1.start();
		
		System.out.println("���߳��Ѿ������ˣ���һ�����滹��û�й���ڶ�");
		
	}
}
