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
				//�����⼸�о���Ϊ�˻�ȡEntryʵ����key��value��������һ���µ�Entryʵ��
				@SuppressWarnings("unchecked")
				ThreadLocal<Object> key = (ThreadLocal<Object>)e.get();
				if(key != null) {
					Object value = key.chirld(e.value);//����e.value
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
