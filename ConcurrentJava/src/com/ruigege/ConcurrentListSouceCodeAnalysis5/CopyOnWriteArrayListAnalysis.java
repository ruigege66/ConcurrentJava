package com.ruigege.ConcurrentListSouceCodeAnalysis5;

import java.util.Arrays;
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
    	//���ߺ���Arrays.copyOf��ʾ����һ���ڶ������ĳ��ȣ�����Ϊ��һ�����������飬���ҷ��ص����������ǵ���������
    	setArray(Arrays.copyOf(toCopyIn, toCopyIn.length,Object[].class));
    }
}
