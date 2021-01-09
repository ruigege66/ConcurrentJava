package com.ruigege.ConcurrentListSouceCodeAnalysis5;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class CopyOnWriteArrayListAnalysis<E> implements List<E>,RandomAccess, Cloneable, java.io.Serializable {
	
	CopyWriteArrayList a = new CopyOnWriteArrayList();
	
    private transient volatile Object[] array;

	public CopyOnWriteArrayListAnalysis() {
		setArray(new Object[0]);
	}
	
    final void setArray(Object[] a) {
        array = a;
    }
    
    public CopyOnWriteArrayListAnalysis(E[] toCopyIn) {
    	//���ߺ���Arrays.copyOf��ʾ����һ���ڶ������ĳ��ȣ�����Ϊ��һ�����������飬���ҷ��ص����������ǵ���������
    	setArray(Arrays.copyOf(toCopyIn, toCopyIn.length,Object[].class));
    }
    
    public CopyOnWriteArrayListAnalysis(Collection<? extends E> c) {
    	Object[] elements;
    	if(c.getClass() == CopyOnWriteArrayListAnalysis.class) {
    		elements = ((CopyOnWriteArrayListAnanlysis)c).getArray();
    	}else {
    		elements = c.toArray();
    		//������������������ж����û�з���һ��Object[].class�����
    		if(elements.getClass() != Object[].class) {
    			elements = Arrays.copyOf(elements,elements.length,Object[].class);
    		}
    	}
    }
    
    final Object[] getArray() {
        return array;
    }
    
    public boolean add(E element) {
    	final ReentrantLock lock = this.lock; // ��ȡ��ʵ���Ķ�ռ��
    	lock.lock();
    	try {
    		Object[] elements = getArray(); // ��ȡ�ڲ��洢�����飬ע����ʱ����ԭ�������飬
    		// ֻ������ʹ��һ���µ�������ָ�����ĵ�ַ
    		int len = elements.length; // ��ȡ����ĳ���
    		Object[] newElements = Arrays.copyOf(elements,len+1);
    		// ʹ��Arrays�����࣬����һ���µ����飬��ǰ���Ԫ��ȫ�����ƽ�ȥ����������һ��λ��
    		newElements[len] = element;
    		// ʹ�����������������ԭ��������
    		setArray(newElements);
    	}finally {
    		lock.unlock();
    	}
    }
    
    public E get(int index) {
    	return get(getArray(),index);
    }
    
    public E get(Object[] a,int index) {
    	return (E)a[index];
    }
    
    public E set(int index ,E element) {
    	final ReentrantLock lock = this.lock;
    	lock.lock();
    	try {
    		Object[] a = getArray();
    		E oldValue = a[index];
    		if(oldValue != element) {
    			int len = a.length;
    			Object[] newElements = Arrays.copyOf(a, len);
    			newElements[index] = element;
    			setArray(newElements);
    		}else {
    			setArray(a);
    		}
    		
    	}finally{
    		lock.unlock();
    	}
    }
    
}
