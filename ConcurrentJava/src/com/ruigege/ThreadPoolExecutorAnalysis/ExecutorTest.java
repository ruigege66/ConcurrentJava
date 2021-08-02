package com.ruigege.ThreadPoolExecutorAnalysis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorTest {
	public static ExecutorService newFixeThreadPool (int nThreads) {
		return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	}
	
	public static ExecutorService newFixeThreadPool (int nThreads, ThreadFactory threadFactory) {
		return new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
	}
	

	
	public static ExecutorService newSingleThreadExecutor() {
		return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));
	}
	
	// 使用自己的工厂
	public static ExecutorService newSingleThreadExecutor(ThreadFactory threadFactory) {
		return new FinalizableDelegatedExecutorService(new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()), threadFactory);
	}
	
	public static ExecutorService newCachedThreadPool() {
		return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
	}
	
	// 使用自定义的线程工厂
	public static ExecutorService newCachedThreadPool(ThreadFactory threadFactory) {
		return new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L,TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
	}

}
