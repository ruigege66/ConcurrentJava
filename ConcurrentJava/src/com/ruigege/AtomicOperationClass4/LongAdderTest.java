package com.ruigege.AtomicOperationClass4;
import sun.misc.Unsafe;

@sun.misc.Contended public class LongAdderTest {
	
	volatile long value;
	
	public LongAdderTest(long value) {
		this.value = value;
	}

	private static final Unsafe unsafe;
	private static final long valueOffset;
	static {
		try {
			unsafe = Unsafe.getUnsafe();
			Class<?> ak = Cell.class;
			valueOffset = unsafe.objectFieldOffset(ak.getDeclaredField("value"));
		}catch(Exception e) {
			throw new Error(e);
		}
	}
	
	final long cas(long cmp,long val) {
		return unsafe.compareAndSwapLong(this,valueOffset,cmp,val);
	}
	
	public long sum() {
		Cell[] as = cells;
		long sum = base;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(Cell[i] != null) {
					sum += Cell[i].value;
				}
			}
		}
	}
	
	public void reset() {
		Cell[] as = cells;
		base = 0L;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(as[i] != null) {
					as[i].value = 0L;
				}
			}
		}
	}
	
	public long sunThenReset() {
		Cell[] as = cells;
		int sum = base;
		if(as != null) {
			for (int i=0;i<as.length;i++) {
				if(as[i] != null) {
					sum += as[i];
					as[i] = 0L;
				}
			}
		}
	}
	
	public void add(long x) {
		Cell[] as;
		long b,v;
		int m;
		Cell a;
		if((as = cells) != null) || !caseBase(b=base,b+x)){
			//���as��һ�������飬Ҳ���ǵ�һ����false����ô��ȥ�жϵڶ������ڶ�����������ʵ����һ��CAS������
			//���������ζ��û�г�ʼ�����飬���LongAdderʵ���������һ��base������Ҳ����˵������̫С�ˣ�������
			//��ʼ�����飬��ʱ���ʵ������һ��AtomicLongʵ����û�����𣬵ڶ����жϾ��Ǹ�base��ֵΪ���µ�������
			//���casʧ���ˣ���ô�ͽ��뵽������ж������
			boolean uncontended = true;
			if(as==null || (m = as.length-1)<0 || (a = as[getProbe() & m]) == null || !(uncontended=a.cas(v=a.value,v+x))){
				/**
				 * �ĸ��ж�������������
				 * ��1���������Ϊ�գ�����ִ���ж����ڣ���Ϊ�գ����ڶ�������
				 * ��2�����������Ԫ��Ϊ0������ִ���ж����ڣ�������1����������������
				 * ��3��
				 */
				longAccumulate(x,null,uncontended);
			}
		}
	}
	
	final boolean casBase(long cmp,long val) {
		return UNSAFE.compareAndSwapLong(this,BASE,cmp,val);
	}
	
	
}
