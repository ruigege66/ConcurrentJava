package com.ruigege.OtherFoundationOfConcurrent2;

public class UpdateEntry2 {

	public int updateEntry(long id) {
		//使用乐观锁获取指定记录
		EntryObject entry = query("select * from table1 where id=#{id}",id);
		
		//
		String name = generatorName(entry);
		entry.setName(name);
		
		//update操作
		int count = update("update table1 set name=#{name},age=#{age},version=${version}+1 where id=#{id} and version =#{version}",entry);
		return count;
	}
}
