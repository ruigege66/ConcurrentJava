package com.ruigege.AtomicOperationClass4;
import sun.misc.Unsafe;

public class LongAdderTest {
	
	volatile long value;
	
	public LongAdderTest(long value) {
		this.value = value;
	}

	private static final Unsafe unsafe;
	private static final long valueOffset;
	static {
		try {
			unsafe = Unsafe.getUnsafe();
			Class<?>
		}
	}
}
