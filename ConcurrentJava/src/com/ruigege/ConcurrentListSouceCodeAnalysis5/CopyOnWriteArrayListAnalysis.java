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
    	//工具函数Arrays.copyOf表示复制一个第二参数的长度，内容为第一个参数的数组，并且返回的数组类型是第三个参数
    	setArray(Arrays.copyOf(toCopyIn, toCopyIn.length,Object[].class));
    }
    
    public CopyOnWriteArrayListAnalysis(Collection<? extends E> c) {
    	Object[] elements;
    	if(c.getClass() == CopyOnWriteArrayListAnalysis.class) {
    		elements = ((CopyOnWriteArrayListAnanlysis)c).getArray();
    	}else {
    		elements = c.toArray();
    		//下面这条语句是用来判断如果没有返回一个Object[].class的情况
    		if(elements.getClass() != Object[].class) {
    			elements = Arrays.copyOf(elements,elements.length,Object[].class);
    		}
    	}
    }
    
    final Object[] getArray() {
        return array;
    }
    
    public boolean add(E element) {
    	final ReentrantLock lock = this.lock; // 获取该实例的独占锁
    	lock.lock();
    	try {
    		Object[] elements = getArray(); // 获取内部存储的数组，注意这时候还是原来的数组，
    		// 只不过是使用一个新的引用来指向它的地址
    		int len = elements.length; // 获取数组的长度
    		Object[] newElements = Arrays.copyOf(elements,len+1);
    		// 使用Arrays工具类，创建一个新的数组，将前面的元素全都复制进去，并且留出一个位置
    		newElements[len] = element;
    		// 使用这个新数组来代替原来的数组
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
    		int removeRemain = len - (index + 1); // 这个整数代表要迁移的剩余元素个数
    		if(removeRemain == 0) {
    			setArray(Arrays.copyOf(elements, len-1));//除了最后一个全部都copy过去
    		}else {
    			Object[] newElements = new Object[len-1];
    			System.arraycopy(elements,0,newElements,0,index);
    			System.arraycopy(elements,index+1,newElements,index,removeRemain);
    			// 这里我们学习一个函数System.arraycopy
    			// 第一个参数代表从哪里复制，第二个参数代表从第几个索引开始
    			// 第三个参数代表复制到哪个数组中，从第几个开始，复制几个到新数组
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
    	// array的快照版本
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
