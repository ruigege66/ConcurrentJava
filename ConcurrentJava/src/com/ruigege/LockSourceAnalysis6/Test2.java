package com.ruigege.LockSourceAnalysis6;

import java.util.concurrent.locks.LockSupport;

public class Test2 {
	public static void main(String[] args) {
		System.out.println("������");
		// ʹ��ǰ�̻߳�ȡ�����֤
		LockSupport.unpark(Thread.currentThread());
		// �ٴε���park����
		LockSupport.park();
		System.out.println("end park");
		int name = 5;
		switch(name) {
			case 1:
				System.out.println("litianyang");
				break;
			case 5:
				System.out.println("goutianyang");
				break;
			default:
				System.out.println("û����");
				
		}
	}
}
