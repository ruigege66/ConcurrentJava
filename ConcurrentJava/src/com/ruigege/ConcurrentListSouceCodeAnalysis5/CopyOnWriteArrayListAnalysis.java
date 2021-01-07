package com.ruigege.ConcurrentListSouceCodeAnalysis5;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;

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
}
