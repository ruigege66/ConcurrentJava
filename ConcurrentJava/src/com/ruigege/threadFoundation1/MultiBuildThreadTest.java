package com.ruigege.threadFoundation1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class MultiBuildThreadTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException{
		//使用继承Thread的类的方式来进行多线程创建
		MyThreadExtendsType thread1 = new MyThreadExtendsType();
		thread1.start();
		//使用实现Runnable接口的方式进行多线程创建
		Thread thread2 = new Thread(new MyThreadImplementsRunnable());
		thread2.start();
		//使用Callable和FutureTask来创建多线程
		FutureTask<String> futurnTask = new FutureTask<>(new MyThreadImplementsCallable());
		Thread thread3 = new Thread(futurnTask);
		
		thread3.start();
		System.out.println(futurnTask.get());
		
		
		
	}
}
