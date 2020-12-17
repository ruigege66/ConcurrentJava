package com.ruigege.AtomicOperationClass4;

import java.util.concurrent.atomic.AtomicLong;

import jdk.internal.misc.Unsafe;

import java.lang.Number;

public class AtomicLongTest extends Number implements java.io.Serializable{
	private static final long serialVersionUID = 1927816293512124184L;
	
	private static final Unsafe unsafe = Unsafe.getUnsafe();
	
	private volatile long value;//用于放实际值的变量
	
	private final long valueOffset;//value的偏移量
	
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
	
	//下面的语句是用于判断JVM是否支持Long类型的CAS
	static final boolean VM_SUPPORTS_LONG_CAS = VMSupportsCS8();
	private static native boolean VMSupportCS8();
	
	/**
	 * 该方法使用CAS算法用于累加
	 * @return
	 */
	public final long incrementAndGet() {
		return unsafe.getAndAddLong(this, valueOffset, 1L) + 1L;
	}
	/**
	 * 我们来一起回顾一下Unsafe类中的成员方法getAndAddLong方法
	 * public final long getAndAddLong(Object obj,long offset,long addValue){
	 *     long l;
	 *     do{
	 *         l = getLongvolatie(obj,offset);//获取obj对象偏移量为offset的值
	 *     }(!compareAndSwapLong(obj,offset,l,l+addValue));如与预期不相同，那么我们不断地获取l的值，如果
	 *     //相同了，说明这个时候可以+addValue将得到的值赋值给l就可以了
	 *     return l;最后返回的是+addValue之前的值
	 *     //我理解的CAS算法，其实它的本质就是不断循环获取一致的值，再进行更新。
	 * }
	 */
	
	/**
	 * 该方法使用CAS用于累减
	 * @return
	 */
	public final long decrementAndGet() {
		return unsafe.getAndAddLong(this,valueOffset,-1L) -1L;
	}
	
	
	/**
	 * 下面这两个方法，可以和上面的两个方法作对比，都是累加，但是上面返回是累加之后的值，下面是返回累加之前的值
	 * @return
	 */
	public final long getAndIncrement() {
		return unsafe.getAndAddLong(this, valueOffset, 1L);
	}
	
	public final long getAndDecrement() {
		return unsafe.getAndAddLong(this,valueOffset,-1L);
	}
	
	/**
	 * 下面这个方法类似于Unsafe中的compareAndSwapLong方法
	 * @param expect
	 * @param update
	 * @return
	 */
	public final boolean compareAndSet(long expect,long update) {
		return unsafe.compareAndSwapLong(this,valueOffset,expect,update);
	}
	
}
