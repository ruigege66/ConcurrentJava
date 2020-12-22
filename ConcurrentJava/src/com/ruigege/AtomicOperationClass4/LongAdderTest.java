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
			//如果as是一个空数组，也就是第一个是false，那么会去判断第二个，第二个函数，其实就是一个CAS操作，
			//空数组就意味着没有初始化数组，这个LongAdder实例，里面就一个base变量，也就是说并发量太小了，不足以
			//初始化数组，这时这个实例就是一个AtomicLong实例，没有区别，第二个判断就是给base赋值为最新的增长量
			//如果cas失败了，那么就进入到下面的判断语句中
			boolean uncontended = true;
			if(as==null || (m = as.length-1)<0 || (a = as[getProbe() & m]) == null || !(uncontended=a.cas(v=a.value,v+x))){
				/**
				 * 四个判断条件，逐步深入
				 * （1）如果数组为空，继续执行判断体内；不为空，看第二个条件
				 * （2）如果数组中元素为0，继续执行判断体内；不少于1个，看第三个条件
				 * （3）
				 */
				longAccumulate(x,null,uncontended);
			}
		}
	}
	
	final boolean casBase(long cmp,long val) {
		return UNSAFE.compareAndSwapLong(this,BASE,cmp,val);
	}
	
	
}
