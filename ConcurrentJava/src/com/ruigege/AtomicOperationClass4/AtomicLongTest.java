package com.ruigege.AtomicOperationClass4;

import java.util.concurrent.atomic.AtomicLong;

import jdk.internal.misc.Unsafe;

import java.lang.Number;

public class AtomicLongTest extends Number implements java.io.Serializable{
	private static final long serialVersionUID = 1927816293512124184L;
	
	private static final Unsafe unsafe = Unsafe.getUnsafe();
	
	private volatile long value;//���ڷ�ʵ��ֵ�ı���
	
	private final long valueOffset;//value��ƫ����
	
	static {
		try {
			valueOffset = unsafe.objectFieldOffset(AtomicLong.class.getDeclaredField(value));
			
		}catch(Exception e) {
			throw new Error(e);
		}
	}
	
	public AtomicLongTest(long initialValue) {
		value = initialValue;
	}
	
	//���������������ж�JVM�Ƿ�֧��Long���͵�CAS
	static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
	private static native boolean VMSupportCS8();
	
	/**
	 * �÷���ʹ��CAS�㷨�����ۼ�
	 * @return
	 */
	public final long incrementAndGet() {
		return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
	}
	/**
	 * ������һ��ع�һ��Unsafe���еĳ�Ա����getAndAddLong����
	 * public final long getAndAddLong(Object obj,long offset,long addValue){
	 *     long l;
	 *     do{
	 *         l = getLongvolatie(obj,offset);//��ȡobj����ƫ����Ϊoffset��ֵ
	 *     }(!compareAndSwapLong(obj,offset,l,l+addValue));����Ԥ�ڲ���ͬ����ô���ǲ��ϵػ�ȡl��ֵ�����
	 *     //��ͬ�ˣ�˵�����ʱ�����+addValue���õ���ֵ��ֵ��l�Ϳ�����
	 *     return l;��󷵻ص���+addValue֮ǰ��ֵ
	 *     //������CAS�㷨����ʵ���ı��ʾ��ǲ���ѭ����ȡһ�µ�ֵ���ٽ��и��¡�
	 * }
	 */
	
	/**
	 * �÷���ʹ��CAS�����ۼ�
	 * @return
	 */
	public final long decrementAndGet() {
		return unsafe.getAndAddLong(this,valueOffset,-1L) -1L;
	}
	
	
	/**
	 * �������������������Ժ�����������������Աȣ������ۼӣ��������淵�����ۼ�֮���ֵ�������Ƿ����ۼ�֮ǰ��ֵ
	 * @return
	 */
	public final long getAndIncrement() {
		return unsafe.getAndAddLong(this, valueOffset, 1L);
	}
	
	public final long getAndDecrement() {
		return unsafe.getAndAddLong(this,valueOffset,-1L);
	}
	
	/**
	 * �����������������Unsafe�е�compareAndSwapLong����
	 * @param expect
	 * @param update
	 * @return
	 */
	public final boolean compareAndSet(long expect,long update) {
		return unsafe.compareAndSwapLong(this,valueOffset,expect,update);
	}
	
}
