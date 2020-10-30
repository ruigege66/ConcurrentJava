package com.ruigege.threadFoundation1;

public class InterruptThreadWhenSleeping {
	public static void main(String[] args) throws InterruptedException {
		Thread threadOne = new Thread(new Runnable() {
			@Override
			public void run(){
				try {
					System.out.println("���߳̿�ʼ�ˣ�����ʼ˯�߼���");
					Thread.sleep(3000);
					System.out.println("˯�߽���");
				}catch(InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		
		threadOne.start();
		Thread.sleep(1000);
		threadOne.interrupt();
		threadOne.join();
		
	}
}
