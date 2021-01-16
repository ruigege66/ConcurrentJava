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
    
    public E remove(int index) {
    	final ReentrantLock lock = this.lock;
    	lock.lock();
    	try {
    		Object[] elements = getArray();
    		int len = elements.length;
    		int removeRemain = len - (index + 1); // �����������ҪǨ�Ƶ�ʣ��Ԫ�ظ���
    		if(removeRemain == 0) {
    			setArray(Arrays.copyOf(elements, len-1));//�������һ��ȫ����copy��ȥ
    		}else {
    			Object[] newElements = new Object[len-1];
    			System.arraycopy(elements,0,newElements,0,index);
    			System.arraycopy(elements,index+1,newElements,index,removeRemain);
    			// ��������ѧϰһ������System.arraycopy
    			// ��һ��������������︴�ƣ��ڶ�����������ӵڼ���������ʼ
    			// ���������������Ƶ��ĸ������У��ӵڼ�����ʼ�����Ƽ�����������
    			setArray(newElements);
    		}
    	}finally {
    		lock.unlock();
    	}
    }
    
    public Iterator<E> iterator(){
    	return new COWIteratro<E>(getArray(),0);
    }
    
    static final class COWIterator<E> implements ListIterator<E> {
    	// array�Ŀ��հ汾
    	private final Object[] snapshot;
    	
    	private int cursor;
    	
    	private COWIterator(Object[] elements,int initialCursor) {
    		cursor = initialCursor;
    		snapshot = elements;
    	}
    	
    	
    	public boolean hasNext() {
    		return cursor < snapshot.length;
    	}
    	
    	public E next() {
    		if(!hasNext()) {
    			throw new NoSuchElementException();
    		}
    		return <E>snapshot[cursor++];
    	}	
    }    
}
