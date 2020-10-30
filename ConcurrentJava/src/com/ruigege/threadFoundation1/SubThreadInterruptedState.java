package com.ruigege.threadFoundation1;

public class SubThreadInterruptedState {
	public static void main(String[] args) throws InterruptedException{
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while(!Thread.currentThread().isInterrupted()) {
					System.out.println("���߳�û���ж�");
				}
				System.out.println("���߳���Ȼ�ж��ˣ����ǲ�û�н���");
			}
		});
		
		thread1.start();
		Thread.sleep(5);//��֤���߳��ܹ�ִ������
		thread1.interrupt();//�ж����̣߳��������Ϊʱ��Ƭ���ﲻ������һ���ٸ���������̻߳�������н������⺯������һ��������
		System.out.println(thread1.isInterrupted());
		thread1.join();
		System.out.println("���߳̽���");
		
	}
}
