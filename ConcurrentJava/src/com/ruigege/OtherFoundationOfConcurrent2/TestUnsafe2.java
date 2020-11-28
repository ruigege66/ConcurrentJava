package com.ruigege.OtherFoundationOfConcurrent2;

import java.lang.reflect.Field;

import sun.misc.Unsafe;
//import jdk.internal.misc.Unsafe;

public class TestUnsafe2 {
	static final Unsafe unsafe;
	static final long offset;
	private volatile long state=0;
	static {
		try {
			//使用反射获取成员变量theState的值
			Field field = Unsafe.class.getDeclaredField("unsafe");
			//设置域值为可存取
			field.setAccessible(true);
			//获取该变量field的值
			Unsafe unsafe = (Unsafe)field.get(null);
			//获得的这个unsafe类，我们使用它的方法来获取这个测试类的state变量的偏移量
			offset = unsafe.objectFieldOffset(TestUnsafe2.class.getDeclaredField("state"));
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new Error(e);
		}
	}
	
	
	public static void main(String[] args) {
		TestUnsafe2 testUnsafe2 = new TestUnsafe2();
		boolean success = unsafe.compareAndSwapInt(testUnsafe2,offset,0,1);
		System.out.println(success);
	}
}
