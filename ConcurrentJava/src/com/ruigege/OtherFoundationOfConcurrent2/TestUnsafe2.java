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
			//ʹ�÷����ȡ��Ա����theState��ֵ
			Field field = Unsafe.class.getDeclaredField("unsafe");
			//������ֵΪ�ɴ�ȡ
			field.setAccessible(true);
			//��ȡ�ñ���field��ֵ
			Unsafe unsafe = (Unsafe)field.get(null);
			//��õ����unsafe�࣬����ʹ�����ķ�������ȡ����������state������ƫ����
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
