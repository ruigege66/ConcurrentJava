package com.ruigege.threadFoundation1;

public class TestThreadLocal {
	ThreadLocalMap threadLocals;
	ThreadLocalMap inheritableThreadLocals;
		
	public void set(T value) {
		Thread t = Thread.currentThread();//���Ȼ�ȡ���̱���
		//���Ȼ�ȡ��Ա����
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
		//key��this,���˵����ֻ�������ThreadLocalʵ����ΪΨһkey����Ҳ��֤�ˣ������̵߳ı���
		//����threadLocals��key��һ�£���֤������һ�£������Threadʵ����key���ǾͿ϶���һ���ˣ�
		//��Ϊÿ��Thread��ʵ����һ����
		threadLocals = new ThreadLocalMap(this,value);
	}
	
	public T get() {
		Thread t = Thread.currentThread();//��set����һ���Ȼ�ȡ���߳� 
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
		
		//����Ĵ����setһ����ֻ��������ֻ����ǰ������nullΪvalue
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
