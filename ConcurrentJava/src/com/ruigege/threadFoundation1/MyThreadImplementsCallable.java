package com.ruigege.threadFoundation1;

import java.util.concurrent.Callable;

public class MyThreadImplementsCallable implements Callable<String> {
	@Override
	public String call() throws Exception{
		System.out.println("ʹ��FutureTask�ķ�ʽ���д������߳�");
		return "��������";
	}
	

}
