package com.ruigege.OtherFoundationOfConcurrent2;

public class UpdateEntry2 {

	public int updateEntry(long id) {
		//ʹ���ֹ�����ȡָ����¼
		EntryObject entry = query("select * from table1 where id=#{id}",id);
		
		//
		String name = generatorName(entry);
		entry.setName(name);
		
		//update����
		int count = update("update table1 set name=#{name},age=#{age},version=${version}+1 where id=#{id} and version =#{version}",entry);
		return count;
	}
}
