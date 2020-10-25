package com.ruigege.threadFoundation1;

import java.util.concurrent.Callable;

public class MyThreadImplementsCallable implements Callable<String> {
	@Override
	public String call() throws Exception{
		System.out.println("使用FutureTask的方式来行创建多线程");
		return "创建好了";
	}
	

}
