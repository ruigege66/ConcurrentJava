package com.ruigege.OtherFoundationOfConcurrent2;

import jdk.internal.misc.Unsafe;

public class TestUnsafe {

	static final Unsafe unsafe = Unsafe.getUnsafe();
	
	static final long state = 0;
	
	static final long stateOffset=0;
	//unsafeʵ���ڲ�����state��ƫ����
	
	static {
		try {
			stateOffset = unsafe.objectFieldOffset(Unsafe.class.getDeclaredField("state"));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		TestUnsafe testUnsafe = new TestUnsafe();
		Boolean success = unsafe.compareAndSwapInt(testUnsafe,stateOffset,0,1);
		System.out.println(success);
	}
}
