package com.ruigege.threadFoundation1;

public class ThreadLocalMap {
	
	private Entry[] table;
	
	private ThreadLocalMap(ThreadLocalMap parentMap) {
		Entry[] parentTable = parentMap.table;
		int len = parentTable.length;
		setThreshole(len);
		table = new Entry[len];
		
		
		for(int j=0;j<len;j++) {
			Entry e = parentTable[j];
			if(e != null) {
				//下面这几行就是为了获取Entry实力的key和value并且生成一个新的Entry实例
				@SuppressWarnings("unchecked")
				ThreadLocal<Object> key = (ThreadLocal<Object>)e.get();
				if(key != null) {
					Object value = key.chirld(e.value);//返回e.value
					Entry c = new Entry(key,value);
					int h = key.threadLocalHashCode & (len-1);
					while(table[h] != null) {
						h = nextIndex(h,len);
					}
					table[h] = c;
					size++;
				}
			}
		}
	}
	
}
