package com.ruigege.PricipleAnalyzingOfThreadLocalRandom3;

public class ThreadLocalRandomSource {
	private static final sun.misc.Unsafe UNSAFE;
	private static final long SEED;
	private static final long PROBE;
	private static final long SECONDARY;
	
	static {
		try {
			//ʹ��Unsafe���ƣ���ȡһ��ʵ������ʹ��������ȡ��Ա������ƫ����
			UNSAFE = sun.misc.Unsafe.getUnsafe();
			Class<?> thread = Thread.class;
			//��ȡthread�ڲ���Ա������ƫ����
			SEED = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomSeed"));
			PROBE = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomProbe"));
			SECONDARY = UNSAFE.objectFieldOffset(thread.getDeclaredField("threadLocalRandomSecondary"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	static final ThreadLocalRandom instance = new ThreadLcoalRandom();
	public static ThreadLocalRandom current() {
		//������һ���жϣ�Ҳ���ǵ�ǰ�߳��е�PROBE�����Ա������ֵΪ0��
		//���Ϊ0��˵�����ǵ�һ�ε��ã�������Ҫ���г�ʼ����ʵ�����ٷ��ء�
		if(UNSAFE.getInt(Thread.currentThread(),PROBE)==0) {
			localInit();
		}
		return instance;
	}
	
	static final void localInit() {
		int p = probeGenerator.addAndGet(PROBE_INCREMENT);
		int probe = (p==0)?1:p;//����0
		int seed = mix64(seeder.getAndAdd(SEEDER_INCREMENT));
		Thread t = Thread.currentThread();
		UNSAFE.putLong(t, SEED,seed);
		UNSAFE.putInt(t, PROBE,probe);
	}
	public int nextInt(int bound) {
		//����У��
		if(bound<0) {
			throw new IllegalArgumentException(BadBound);
		}
		//���ݵ�ǰ�߳��е�������������һ������
		int r = mix32(nextSeed());
		//�����һ������bound������һ����������㷨��
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
		//��r��ֵ���ŵ���ǰ�߳���SEED������
		UNSAFE.putLong(t, SEED,r);
		return r;
	}

} 
