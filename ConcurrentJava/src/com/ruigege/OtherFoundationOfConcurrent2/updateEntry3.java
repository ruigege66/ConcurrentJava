package com.ruigege.OtherFoundationOfConcurrent2;

public class updateEntry3 {

	boolean result = false;
	int retryNum = 5;
	while(retryNum>0) {
		//ʹ���ֹ�����ȡ��¼
		EntryObject entry = query("select * from table1 where id=#{id}",id);
		String name = generatorName(entry);
		entry.setName(name);
		
		//update����
		int count = update("update table1 set name=#{name},age=#{age},version=${version}+1 where id=#{id} and version =#{version}",entry);
		//���ص����������0�Ļ�˵�����³ɹ��ˣ���ô��������ѭ��
		if(count == 1) {
			result = true;
			break;
		}
		retryNum--;
		
	}
	return result;
	
	
}
