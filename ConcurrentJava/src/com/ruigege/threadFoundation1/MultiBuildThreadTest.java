package com.ruigege.threadFoundation1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiBuildThreadTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		//ʹ�ü̳�Thread����ķ�ʽ�����ж��̴߳���
		MyThreadExtendsType thread1 = new MyThreadExtendsType();
		thread1.start();
		//ʹ��ʵ��Runnable�ӿڵķ�ʽ���ж��̴߳���
		Thread thread2 = new Thread(new MyThreadImplementsRunnable());
		thread2.start();
		//ʹ��Callable��FutureTask���������߳�
		FutureTask<String> futurnTask = new FutureTask<>(new MyThreadImplementsCallable());
		Thread thread3 = new Thread(futurnTask);
		
		thread3.start();
		System.out.println(futurnTask.get());
		
		
		
	}
}
