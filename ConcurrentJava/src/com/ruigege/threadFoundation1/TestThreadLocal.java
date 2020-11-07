package com.ruigege.threadFoundation1;

public class TestThreadLocal {
	ThreadLocalMap threadLocals;
	ThreadLocalMap inheritableThreadLocals;
		
	public void set(T value) {
		Thread t = Thread.currentThread();//首先获取进程本身
		//首先获取成员变量
		ThreadLocalMap map = getMap(t);
		if(map != null) {
			map.set(this,value);
		}else {
			createMap(t,value);
		}
		
		
	}
	
	ThreadLocalMap getMap(Thread t) {
		return t.threadLocals;
	}
	
	void createMap(Thread t,T value) {
		//key是this,这就说明，只能是这个ThreadLocal实例作为唯一key，这也保证了，所有线程的本地
		//变量threadLocals的key是一致，保证了数据一致，如果用Thread实例做key，那就肯定不一样了，
		//因为每个Thread的实例不一样。
		threadLocals = new ThreadLocalMap(this,value);
	}
	
	public T get() {
		Thread t = Thread.currentThread();//和set方法一样先获取本线程 
		ThreadLocalMap map = getMap(t);
		if(map != null) {
			ThreadLocalMap.Entry e = map.getEntry(this);
			if(e!=null) {
				
				T result = (T)e.value();
				return result;
			}
		}
		return setInitialValue();
	}
	
	private T setInitialValue() {		
		T value = initialValue();
		
		//下面的代码和set一样，只不过我们只是提前设置了null为value
		Thread t = Thread.currentThread();
		ThreadLocalMap  map = getMap(t);
		if(map != null) {
			map.set(this,value);
		}else {
			createMap(t,value);
			
		}
		return value;
	}
	
	protected T initialValue() {
		return null;
	}
}
