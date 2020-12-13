package com.ruigege.PricipleAnalyzingOfThreadLocalRandom3;

public class ThreadLocalRandomSource {
	private static final sun.misc.Unsafe UNSAFE;
	private static final long SEED;
	private static final long PROBE;
	private static final long SECONDARY;
	
	static {
		try {
			//使用Unsafe机制，获取一个实例，并使用它来获取成员变量的偏移量
			UNSAFE = sun.misc.Unsafe.getUnsafe();
			Class<?> thread = Thread.class;
			//获取thread内部成员变量的偏移量
			SEED = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomSeed"));
			PROBE = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomProbe"));
			SECONDARY = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomSecondary"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	static final ThreadLocalRandom instance = new ThreadLcoalRandom();
	public static ThreadLocalRandom current() {
		//这里做一个判断，也就是当前线程中的PROBE这个成员变量的值为0吗？
		//如果为0，说面这是第一次调用，我们先要进行初始化该实例，再返回。
		if(UNSAFE.getInt(Thread.currentThread(),PROBE)==0) {
			localInit();
		}
		return instance;
	}
	
	static final void localInit() {
		int p = probeGenerator.addAndGet(PROBE_INCREMENT);
		int probe = (p==0)?1:p;//跳过0
		int seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
		Thread t = Thread.currentThread();
		UNSAFE.putLong(t, SEED,seed);
		UNSAFE.putInt(t, PROBE,probe);
	}
	public int nextInt(int bound) {
		//参数校验
		if(bound<0) {
			throw new IllegalArgumentException(BadBound);
		}
		//根据当前线程中的种子来计算下一个种子
		int r = mix32(nextSeed());
		//下面就一个根据bound来返回一个随机数的算法了
		int m = bound-1;
		if((bound & m)==0) {
			r&= m;
		}else {
			for (int u=r>>>1;u+m-(r=u%bound)<0;u = mix32(nextSeed())>>>1);
		}
		return r;
	}
	final long nextSeed() {
		Thread t = Thread.currentThread();
		long r = UNSAFE.getLong(t,SEED)+GAMMA;
		//将r的值，放到当前线程中SEED变量中
		UNSAFE.putLong(t, SEED,r);
		return r;
	}

} 
