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
}
